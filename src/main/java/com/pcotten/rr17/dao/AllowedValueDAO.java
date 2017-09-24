package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.util.AllowedValue;

public interface AllowedValueDAO {

	public List<AllowedValue> getAllowedValues(String avTable, String qualifier);
	
}
