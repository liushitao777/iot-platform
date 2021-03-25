package com.cpiinfo.iot.commons.influxdb;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

/**
 * @ClassName InfluxDBUtils
 * @Deacription InfluxDB操作对象，支持多租户时按租户指定不同 database
 * @Author yangbo
 * @Date 2020年10月02日 20:08
 * @Version 1.0
 **/
@Slf4j
@ConditionalOnBean(InfluxDbConfigProperties.class)
@Component
public class InfluxDBUtils implements DisposableBean{
    
    @Autowired
    private InfluxDbConfigProperties properties;
    
    @Autowired
    private InfluxdbDatabaseProvider dbProvider;
    
    @Autowired
    private ConversionService conversion;
    
    private Map<Class<?>, Map<String, Field>> mapFields = new ConcurrentHashMap<>();

    private volatile InfluxDB _influxDB;
    private OkHttpClient.Builder client;

    public InfluxDBUtils() {
    }

    /**
     * 创建数据库
     *
     * @param dbName
     */
    @SuppressWarnings("deprecation")
    public void createDB(String dbName) {
        getInfluxDb().createDatabase(dbName);
    }

    /**
     * 删除数据库
     *
     * @param dbName
     */
    @SuppressWarnings("deprecation")
    public void deleteDB(String dbName) {
        getInfluxDb().deleteDatabase(dbName);
    }

    /**
     * 测试连接是否正常
     *
     * @return true 正常
     */
    public boolean ping() {
        boolean isConnected = false;
        Pong pong;
        try {
            pong = getInfluxDb().ping();
            if (pong != null) {
                isConnected = true;
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return isConnected;
    }

    /**
     * 连接时序数据库 ，若不存在则创建
     *
     * @return
     */
    public InfluxDB getInfluxDb() {
        if (_influxDB == null) {
            synchronized (this) {
                if (_influxDB == null) {
                    client = new OkHttpClient.Builder()
                        .connectTimeout(properties.getConnectTimeout(), TimeUnit.SECONDS)
                        .writeTimeout(properties.getWriteTimeout(), TimeUnit.SECONDS)
                        .readTimeout(properties.getReadTimeout(), TimeUnit.SECONDS);
                    _influxDB = InfluxDBFactory.connect(properties.getUrl(), 
                            properties.getUsername(), properties.getPassword(), client);
                    _influxDB.setRetentionPolicy(properties.getRetentionPolicy());
                    if(properties.isGzip()) {
                        _influxDB.enableGzip();
                    }
                    if(properties.isBatchEnabled()) {
                        _influxDB.enableBatch();
                    }
                    _influxDB.setLogLevel(InfluxDB.LogLevel.parseLogLevel(properties.getLogLevel()));
                }
            }
        }
        return _influxDB;
    }
    
    protected String determinDatabase() {
        return dbProvider.database();
    }

    /**
     * 创建自定义保留策略
     *
     * @param policyName  策略名
     * @param duration    保存天数
     * @param replication 保存副本数量
     * @param isDefault   是否设为默认保留策略
     */
    public void createRetentionPolicy(String policyName, String duration, int replication, Boolean isDefault) {
        String sql = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s ", policyName,
                determinDatabase(), duration, replication);
        if (isDefault) {
            sql = sql + " DEFAULT";
        }
        this.query(sql);
    }

    /**
     * 创建默认的保留策略
     *
     * @param 策略名：default，保存天数：30天，保存副本数量：1 设为默认保留策略
     */
    public void createDefaultRetentionPolicy() {
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "default", determinDatabase(), "60d", 1);
        this.query(command);
    }

    /**
     * 查询
     *
     * @param command 查询语句
     * @return
     */
    public QueryResult query(String command) {
        return getInfluxDb().query(new Query(command, determinDatabase()));
    }
    
    /**
     * 查询数据并返回指定类型对象列表
     * @param <T>
     * @param command
     * @param clazz
     * @return
     */
    public <T> List<T> queryList(String command, Class<T> clazz){
        return queryList(command, clazz, null);
    }
    public <T> List<T> queryList(String command, Class<T> clazz, DataRowMapper<T> rowMapper){
        List<T> list = new ArrayList<>();
        QueryResult results = query(command);
        if(!results.hasError()) {
            if(results.getResults() != null) {
                list = new ArrayList<>();
                Map<String, Field> fields = getClassFields(clazz);
                for (Result result : results.getResults()) {
                    List<Series> series= result.getSeries();
                    if(null == series){
                        return list;
                    }
                    for (Series serie : series) {
                        List<List<Object>>  values = serie.getValues();
                        List<String> columns = serie.getColumns();
                        for(List<Object> row : values) {
                            T obj = null;
                            try {
                                obj = clazz.newInstance();
                            } catch (Exception e) {
                                throw new RuntimeException("创建对象出错.", e);
                            }
                            for(int i = 0; i < columns.size(); i++) {
                                String col = columns.get(i);
                                Object val = row.get(i);
                                Field field = fields.get(col);
                                if(field != null) {
                                    val = convertFieldValue(val, field.getType());
                                    ReflectionUtils.makeAccessible(field);
                                    ReflectionUtils.setField(field, obj, val);
                                }
                            }
                            if(rowMapper != null) {
                                rowMapper.mappingDataRow(obj, columns, row);
                            }
                            list.add(obj);
                        }
                    }
                }
            }
        }
        else {
            throw new RuntimeException("查询时序数据InfluxDB出错. " + results.getError());
        }
        return list;
    }

    private <T> Map<String, Field> getClassFields(Class<T> clazz) {
        Map<String, Field> fields = mapFields.get(clazz);
        if(fields == null) {
            fields = new ConcurrentHashMap<String, Field>();
            mapFields.put(clazz, fields);
            for(Field field : FieldUtils.getAllFieldsList(clazz)) {
                fields.put(field.getName(), field);
            }
        }
        return fields;
    }

    private Object convertFieldValue(Object val, Class<?> type) {
        if(val != null && !type.isAssignableFrom(val.getClass())) {
            val = conversion.convert(val, type);
        }
        return val;
    }

    /**
     * 插入
     *
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields, long time,
            TimeUnit timeUnit) {
        Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);
        if (0 != time) {
            builder.time(time, timeUnit);
        }
        getInfluxDb().write(determinDatabase(), properties.getRetentionPolicy(), builder.build());
    }

    /**
     * 插入
     *
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     */
    public void insert(String measurement, String[] tags, String[] tagValues, 
            String[] fields, Object[] fieldValues, long time, TimeUnit timeUnit) {
        Point point = buildPoint(measurement, tags, tagValues, fields, fieldValues, time, timeUnit);
        getInfluxDb().write(determinDatabase(), properties.getRetentionPolicy(), point);
    }

    /**
     * 构建一个测点
     * @param measurement
     * @param tags
     * @param tagValues
     * @param fields
     * @param fieldValues
     * @param time
     * @param timeUnit
     * @return
     */
    private Point buildPoint(String measurement, String[] tags, String[] tagValues, String[] fields,
            Object[] fieldValues, long time, TimeUnit timeUnit) {
        Builder builder = Point.measurement(measurement);
        for(int i = 0; i < tags.length; i++) {
            builder.tag(tags[i], tagValues[i]);
        }
        for(int i = 0; i < fields.length; i++) {
            builder.field(fields[i], fieldValues[i]);
        }
        if (0 != time) {
            builder.time(time, timeUnit);
        }
        Point point = builder.build();
        return point;
    }
    
    /**
     * 批量写入测点
     *
     * @param batchPoints
     */
    public void batchInsert(List<String> measurements, List<String[]> tags, List<String[]> tagValues, 
            List<String[]> fields, List<Object[]> fieldValues, long time, TimeUnit timeUnit) {
        List<Point> points = new ArrayList<>(measurements.size());
        for(int k = 0; k < measurements.size(); k++) {
            String measurement = measurements.get(k);
            String[] tagArr = tags.get(k);
            String[] tagValArr = tagValues.get(k);
            String[] fieldArr = fields.get(k);
            Object[] fieldValArr = fieldValues.get(k);
            points.add(buildPoint(measurement, tagArr, tagValArr, 
                    fieldArr, fieldValArr, time, timeUnit));
        }
        BatchPoints batchPoints = BatchPoints.database(determinDatabase())
                .retentionPolicy(properties.getRetentionPolicy()).points(points)
                .build();
        getInfluxDb().write(batchPoints);
    }
    
    /**
     * 批量写入测点
     *
     * @param batchPoints
     */
    public void batchInsert(BatchPoints batchPoints) {
        getInfluxDb().write(batchPoints);
    }

    /**
     * 批量写入数据
     *
     * @param database        数据库
     * @param retentionPolicy 保存策略
     * @param consistency     一致性
     * @param records         要保存的数据（调用BatchPoints.lineProtocol()可得到一条record）
     */
    public void batchInsert(final String database, final String retentionPolicy, final ConsistencyLevel consistency,
            final List<String> records) {
        getInfluxDb().write(database, retentionPolicy, consistency, records);
    }

    /**
     * 删除
     *
     * @param command 删除语句
     * @return 返回错误信息
     */
    public String deleteMeasurementData(String command) {
        QueryResult result = getInfluxDb().query(new Query(command, determinDatabase()));
        return result.getError();
    }

    /**
     * 关闭数据库
     */
    public void close() {
        getInfluxDb().close();
    }

    /**
     * 构建Point
     *
     * @param measurement
     * @param time
     * @param fields
     * @return
     */
    public Point pointBuilder(String measurement, long time, Map<String, String> tags, Map<String, Object> fields) {
        Point point = Point.measurement(measurement).time(time, TimeUnit.MILLISECONDS).tag(tags).fields(fields).build();
        return point;
    }

    @Override
    public void destroy() throws Exception {
        if(_influxDB != null) {
            _influxDB.close();
            _influxDB = null;
        }
    }

    /**
     * 时序库中一行数据映射到Bean对象
     * @param <T>
     */
    public static interface DataRowMapper<T> {
        /**
         * 将时序库中一行数据映射到一个Bean对象属性中
         * @param bean - 要映射的Bean对象
         * @param columns - 时序库中的列名
         * @param row - 时序库对应列表的一行数据
         * @return
         */
        boolean mappingDataRow(T bean, List<String> columns, List<Object> row);
    }
}
