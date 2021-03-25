package com.cpiinfo.authnz.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
* @Title: JacksonUtil.java 
* @Package com.cpiinfo.iisp.utils
* @Description: TODO(json字符与对像转换) 
* @author 孙德
* @date 2016年9月20日 上午1:45:29 
* @version V1.0
 */
public final class JacksonUtil {

	public static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);
	
	public static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	/**
	 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
	 * (1)转换为普通JavaBean：readValue(json,Student.class)
	 * (2)转换为List,如List<Student>,将第二个参数传递为Student
	 * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
	 * 
	 * @param jsonStr
	 * @param valueType
	 * @return
	 */
	public static <T> T readValue(String jsonStr, Class<T> valueType) {
		try {
			return objectMapper.readValue(jsonStr, valueType);
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}
	
	/**
	 * json数组转List
	 * @param jsonStr
	 * @param valueTypeRef
	 * @return
	 */
	public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef){
		try {
			return objectMapper.readValue(jsonStr, valueTypeRef);
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	/**
	 * 把JavaBean转换为json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}
	/** 
     * 将map转为json 
     * @param map 
     * @param sb 
     * @return 
     */  
    public static StringBuilder mapToJson(Map<?, ?> map, StringBuilder sb) {  
        if (sb == null) {
        	sb = new StringBuilder();  
        }
        sb.append("{");  
        Iterator<?> iter = map.entrySet().iterator();  
        while (iter.hasNext()) {   
            Entry<?, ?> entry = (Entry<?, ?>) iter.next();  
            String key = entry.getKey() != null ? entry.getKey().toString() : "";  
            sb.append("\"" + stringToJson(key) + "\":");  
            Object o = entry.getValue();  
            if (o instanceof List<?>) {  
                List<?> l = (List<?>) o;  
                listToJson(l, sb);  
            } else if (o instanceof Map<?, ?>) {  
                Map<?, ?> m = (Map<?, ?>) o;  
                mapToJson(m, sb);  
            } else {  
                String val = entry.getValue() != null ? entry.getValue().toString() : "";  
                sb.append("\"" + stringToJson(val) + "\"");  
            }  
            if (iter.hasNext()) {
            	sb.append(",");  
            }
        }  
        sb.append("}");  
        return sb;  
    }  
    
    
    
	/** 
     * 将map转为json 
     * @param map 
     * @param sb 
     * @return 
     * 针对null数据
     */  
    public static StringBuilder mapToJsonNew(Map<?, ?> map, StringBuilder sb) {  
        if (sb == null) {
        	sb = new StringBuilder();  
        }
        sb.append("{");  
        Iterator<?> iter = map.entrySet().iterator();  
        while (iter.hasNext()) {   
            Entry<?, ?> entry = (Entry<?, ?>) iter.next();  
            String key = entry.getKey() != null ? entry.getKey().toString() : "";  
            sb.append("\"" + stringToJson(key) + "\":");  
            Object o = entry.getValue();  
            if (o instanceof List<?>) {  
                List<?> l = (List<?>) o;  
                listToJson(l, sb);  
            } else if (o instanceof Map<?, ?>) {  
                Map<?, ?> m = (Map<?, ?>) o;  
                mapToJson(m, sb);  
            } else {  
                Object val = entry.getValue() != null ? entry.getValue().toString() : entry.getValue();  
                sb.append( val);  
            }  
            if (iter.hasNext()) {
            	sb.append(",");  
            }
        }  
        sb.append("}");  
        return sb;  
    } 
    /** 
     * 将list转为json 
     * @param lists 
     * @param sb 
     * @return 
     */  
    public static StringBuilder listToJson(List<?> lists, StringBuilder sb) {  
        if (sb == null) {
        	sb = new StringBuilder();  
        }
        sb.append("[");  
        for (int i = 0; i < lists.size(); i++) {  
            Object o = lists.get(i);  
            if (o instanceof Map<?, ?>) {  
                Map<?, ?> map = (Map<?, ?>) o;  
                mapToJson(map, sb);  
            } else if (o instanceof List<?>) {  
                List<?> l = (List<?>) o;  
                listToJson(l, sb);  
            } else {  
                sb.append("\"" + o + "\"");  
            }  
            if (i != lists.size() - 1) {
            	sb.append(",");  
            }
        }  
        sb.append("]");  
        return sb;  
    }  
    /** 
     * 将字符串转为json数据 
     * @param str 数据字符串 
     * @return json字符串 
     */  
    public static String stringToJson(String str) {    
        StringBuffer sb = new StringBuffer();    
        for (int i = 0; i < str.length(); i++) {    
            char c = str.charAt(i);    
            switch (c) {    
                case '\"':    
                    sb.append("\\\"");    
                    break;    
                case '\\':    
                    sb.append("\\\\");    
                    break;    
                case '/':    
                    sb.append("\\/");    
                    break;    
                case '\b':    
                    sb.append("\\b");    
                    break;    
                case '\f':    
                    sb.append("\\f");    
                    break;    
                case '\n':    
                    sb.append("\\n");    
                    break;    
                case '\r':    
                    sb.append("\\r");    
                    break;    
                case '\t':    
                    sb.append("\\t");    
                    break;    
                default:    
                    sb.append(c);    
            }    
        }    
        return sb.toString();    
    }  
    
    /**
     * 从Json对象串中取得指定属性的值
     * @param jsonCfgStr
     * @param paramName
     * @return
     */
    public static String getConfigValue(String jsonCfgStr, String paramName) {
		JsonNode json = null;
		try {
			json = objectMapper.readTree(jsonCfgStr);
		} catch (IOException e) {
			if(logger.isDebugEnabled()) {
				logger.error("", e);
			}
		}
		return json == null || json.get(paramName) == null ? null : json.get(paramName).textValue();
	}
}