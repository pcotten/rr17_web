package com.pcotten.rr17.rest.service;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;

@Controller
public interface RecipeRestService {

	@RequestMapping(value="/recipes/{recipeId}", method=RequestMethod.PUT)
	public Recipe updateRecipe(
			@PathVariable ("recipeId") Integer recipeId,
			@RequestBody String payload);
	
	@RequestMapping(value="/recipes/{recipeId}", method=RequestMethod.DELETE)
	public boolean deleteRecipe(
			@PathVariable ("recipeId") Integer recipeId);
	
	@RequestMapping(value="/recipes/{recipeId}/ingredients", method=RequestMethod.GET)
	public List<Ingredient> getRecipeIngredients(
			@PathVariable ("recipeId") Integer recipeId);

	// Is this necessary?
//	@RequestMapping(value="/recipes/{recipeId}/ingredients", method=RequestMethod.POST)
//	public Ingredient createRecipeIngredient(
//			@PathVariable ("recipeId") Integer recipeId,
//			@RequestBody String payload);
	
	@RequestMapping(value="/recipes/{recipeId}/ingredients/{ingredientID}", method=RequestMethod.PUT)
	public Ingredient updateRecipeIngredient(
			@PathVariable ("recipeId") Integer recipeId, 
			@PathVariable ("ingredientId") Integer ingredientId,
			@RequestBody String payload);
	
	@RequestMapping(value="/recipes/{recipeId}/ingredients/{ingredientID}", method=RequestMethod.DELETE)
	public Ingredient deleteRecipeIngredient(
			@PathVariable ("recipeId") Integer recipeId, 
			@PathVariable ("ingredientId") Integer ingredientId);
}
