package com.pcotten.rr17.rest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcotten.rr17.util.AllowedValue;

public interface AllowedValuesRestService {

	@RequestMapping(value="/av/{avTable}", method=RequestMethod.GET)
	public ResponseEntity<List<AllowedValue>> getAllowedValues(
			@PathVariable ("avTable") String avTable,
			@RequestParam (value="qualifier", required=false) String qualifier );
		
}
