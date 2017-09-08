package com.pcotten.rr17.rest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Recipe;

@Controller
public interface CookbookRestService {
	
	@RequestMapping(value="/cookbooks/{cookbookId}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateCookbook(
			@PathVariable ("cookbookId") Integer cookbookId,
			@RequestBody String payload);
	
	@RequestMapping(value="/cookbooks/{cookbookId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCookbook(
			@PathVariable ("cookbookId") Integer cookbookId);
	
	@RequestMapping(value="/cookbooks/{cookbookId}/recipes", method=RequestMethod.GET)
	public ResponseEntity<List<Recipe>> getCookbookRecipes(
			@PathVariable ("cookbookId") Integer cookbookId);
	
	@RequestMapping(value="/cookbooks/{cookbookId}/recipes", method=RequestMethod.POST)
	public ResponseEntity<Void> createCookbookRecipe(
			@PathVariable ("cookbookId") Integer cookbookId,
			@RequestBody String payload,
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/cookbooks/{cookbookId}/recipes/{recipeId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCookbookRecipe(
			@PathVariable ("cookbookId") Integer cookbookId,
			@PathVariable ("recipeId") Integer recipeId);
}
