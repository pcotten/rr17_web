package com.pcotten.rr17.rest.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.rest.service.CookbookRestService;
import com.pcotten.rr17.service.CookbookService;
import com.pcotten.rr17.service.RecipeService;

@RestController
public class CookbookRestServiceImpl implements CookbookRestService {

	@Inject 
	CookbookService service;
	@Inject
	RecipeService recipeService;
	@Inject
	CookbookService cookbookService;
	
	
//	@Override
//	public ResponseEntity<Void> updateCookbook(
//			@PathVariable Integer cookbookId, 
//			@RequestBody Cookbook cookbook) {
//		
//		ResponseEntity<Void> response = null;
//		
//		try {
//			boolean success = service.updateCookbook(cookbook);
//			if (success) {
//				response = ResponseEntity.ok().build();
//			}
//			else {
//				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//			}
//				
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return response;
//	}

//	@Override
//	public ResponseEntity<Void> deleteCookbook(
//			@PathVariable Integer cookbookId) {
//		
//		ResponseEntity<Void> response = null;
//		
//		try {
//			boolean success = service.deleteCookbook(cookbookId);
//			if (success) {
//				response = ResponseEntity.ok().build();
//			}
//			else {
//				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//			}
//				
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return response;
//	}

	@Override
	public ResponseEntity<List<Recipe>> getCookbookRecipes(
			@PathVariable Integer cookbookId) {

		ResponseEntity<List<Recipe>> response = null;
		
		List<Recipe> recipes;
		try {
			recipes = service.getCookbookRecipes(cookbookId);
			if (recipes != null && !recipes.isEmpty()) {
				response = ResponseEntity.ok(recipes);
			}
			else
				response = new ResponseEntity<List<Recipe>>(HttpStatus.NOT_FOUND);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public ResponseEntity<Void> addRecipeToCookbook(
			@PathVariable Integer cookbookId,
			@PathVariable Integer recipeId) {
		
		ResponseEntity<Void> response = null;
		
		boolean success = service.addRecipeToCookbook(cookbookId, recipeId);
		if(success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	@Override
	public ResponseEntity<Void> removeRecipeFromCookbook(
			@PathVariable Integer cookbookId, 
			@PathVariable Integer recipeId) {

		ResponseEntity<Void> response = null;
		
		boolean success;
		try {
			success = service.removeRecipeFromCookbook(cookbookId, recipeId);
			if (success) {
				response = ResponseEntity.ok().build();
			}
			else {
				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return response;
	}

}
