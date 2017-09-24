package com.pcotten.rr17.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.dao.AllowedValueDAO;
import com.pcotten.rr17.service.AllowedValuesService;
import com.pcotten.rr17.util.AllowedValue;

@Component
public class AllowedValuesServiceImpl implements AllowedValuesService {

	@Inject
	AllowedValueDAO allowedValueDAO;
	
	@Override
	public List<AllowedValue> getAllowedValues(String avTable, String qualifier) {

		List<AllowedValue> av = allowedValueDAO.getAllowedValues(avTable, qualifier);
		
		return av;
	}

	
}
