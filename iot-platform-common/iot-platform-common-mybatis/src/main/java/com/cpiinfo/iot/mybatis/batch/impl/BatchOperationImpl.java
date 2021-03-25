package com.cpiinfo.iot.mybatis.batch.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpiinfo.iot.mybatis.batch.BatchOperation;


@Service
public class BatchOperationImpl implements BatchOperation {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public List<Integer> batchInsert(Class<?> mapperClass, String method, List<? extends Object> entities, int batchSize){
		return doBatch(mapperClass, method, entities, batchSize, true);
	}
	
	@Override
	public List<Integer> batchUpdate(Class<?> mapperClass, String method, List<? extends Object> entities, int batchSize){
		return doBatch(mapperClass, method, entities, batchSize, false);
	}
	
	private List<Integer> doBatch(Class<?> mapperClass, String method, List<? extends Object> entities, int batchSize, boolean forInsert){
		List<Integer> result = null;
		if(entities != null && entities.size() > 0) {
			result = new ArrayList<Integer>();
			SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
			String stmt = mapperClass.getName() + "." + method;
			for(int i = 0; i < entities.size(); i++) {
				if(forInsert)
					sqlSession.insert(stmt, entities.get(i));
				else
					sqlSession.update(stmt, entities.get(i));
				if((i + 1) % batchSize == 0) {
					flushBatch(result, sqlSession);
				}
			}
			if(entities.size() % batchSize != 0) {
				flushBatch(result, sqlSession);
			}
			sqlSession.close();
		}
		return result;
	}

	private void flushBatch(List<Integer> result, SqlSession sqlSession) {
		int[] updateCount = sqlSession.flushStatements().get(0).getUpdateCounts();
		for(int cnt : updateCount)
			result.add(cnt);
	}
}
