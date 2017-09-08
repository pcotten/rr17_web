package com.pcotten.rr17.rest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;

@Controller
public interface RecipeRestService {
	
	@RequestMapping(value="/recipes", method=RequestMethod.GET)
	public ResponseEntity<List<Recipe>> getRecipes(
			@RequestParam ("category") String category,
			@RequestParam ("name") String name,
			@RequestParam ("username") String username);
	
	@RequestMapping(value="/recipes/{recipeId}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateRecipe(
			@PathVariable ("recipeId") Integer recipeId,
			@RequestBody String payload);
	
	@RequestMapping(value="/recipes/{recipeId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteRecipe(
			@PathVariable ("recipeId") Integer recipeId);
	
	@RequestMapping(value="/recipes/{recipeId}/ingredients", method=RequestMethod.GET)
	public ResponseEntity<List<Ingredient>> getRecipeIngredients(
			@PathVariable ("recipeId") Integer recipeId);

	
	@RequestMapping(value="/recipes/{recipeId}/ingredients", method=RequestMethod.POST)
	public ResponseEntity<Void> createRecipeIngredient(
			@PathVariable ("recipeId") Integer recipeId,
			@RequestBody String payload,
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/recipes/{recipeId}/ingredients/{ingredientID}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateRecipeIngredient(
			@PathVariable ("recipeId") Integer recipeId, 
			@PathVariable ("ingredientId") Integer ingredientId,
			@RequestBody String payload);
	
	@RequestMapping(value="/recipes/{recipeId}/ingredients/{ingredientID}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteRecipeIngredient(
			@PathVariable ("recipeId") Integer recipeId, 
			@PathVariable ("ingredientId") Integer ingredientId);
}
