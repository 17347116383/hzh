
package com.itdy.hqsm.smtmode.service.Impl;

import java.util.List;

import com.itdy.hqsm.smtmode.mapper.TestDataMapper;
import com.itdy.hqsm.smtmode.service.TestDataService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 *
 * @author
 * @version
 */
@Service
@Transactional(readOnly = true)
public class TestDataServiceImpl   implements TestDataService {


	 @Autowired
	 private TestDataMapper testDataDao;

	@Override
	public T get(String id) {
		return null;
	}

	@Override
	public T get(T entity) {
		return null;
	}

	@Override
	public List<T> findList(T entity) {
		return null;
	}

	@Override
	public void save(T entity) {

	}

	@Override
	public void delete(T entity) {

	}
}