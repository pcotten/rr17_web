package com.pcotten.rr17.rest.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.rest.service.CookbookRestService;

@RestController
public class CookbookRestServiceImpl implements CookbookRestService {

	@Override
	public ResponseEntity<Void> updateCookbook(
			@PathVariable Integer cookbookId, 
			@RequestBody String payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteCookbook(
			@PathVariable Integer cookbookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<Recipe>> getCookbookRecipes(
			@PathVariable Integer cookbookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> createCookbookRecipe(
			@PathVariable Integer cookbookId,
			@RequestBody String payload, 
			UriComponentsBuilder uriBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deleteCookbookRecipe(
			@PathVariable Integer cookbookId, 
			@PathVariable Integer recipeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
