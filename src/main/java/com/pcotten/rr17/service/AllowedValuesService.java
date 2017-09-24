package com.pcotten.rr17.service;

import java.util.List;

import com.pcotten.rr17.util.AllowedValue;

public interface AllowedValuesService {

	public List<AllowedValue> getAllowedValues(String avTable, String qualifier);
}
