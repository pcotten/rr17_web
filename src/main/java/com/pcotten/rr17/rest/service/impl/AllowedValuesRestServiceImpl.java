package com.pcotten.rr17.rest.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pcotten.rr17.rest.service.AllowedValuesRestService;
import com.pcotten.rr17.service.AllowedValuesService;
import com.pcotten.rr17.util.AllowedValue;

@RestController
public class AllowedValuesRestServiceImpl implements AllowedValuesRestService{

	@Inject
	AllowedValuesService avService;
	
	@Override
	public ResponseEntity<List<AllowedValue>> getAllowedValues(
			@PathVariable String avTable, 
			@RequestParam (required=false) String qualifier) {

		ResponseEntity<List<AllowedValue>> response = null;
		List<AllowedValue> av = avService.getAllowedValues(avTable, qualifier);
		if (av != null) {
			response = ResponseEntity.ok(av);
		}
		else {
			response = new ResponseEntity<List<AllowedValue>>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

}
