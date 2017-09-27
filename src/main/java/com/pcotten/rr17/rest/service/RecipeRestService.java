package com.pcotten.rr17.rest.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Image;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;

@Controller
public interface RecipeRestService {
	
	@RequestMapping(value="/recipes", method=RequestMethod.GET)
	public ResponseEntity<List<Recipe>> getRecipes(
			@RequestParam ("category") String category,
			@RequestParam ("title") String title,
			@RequestParam ("username") String username);
	
	@RequestMapping(value="/recipes/{recipeId}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateRecipe(
			@PathVariable ("recipeId") Integer recipeId,
			@RequestBody Recipe recipe);
	
	@RequestMapping(value="/recipes/{recipeId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteRecipe(
			@PathVariable ("recipeId") Integer recipeId);
	
	@RequestMapping(value="/recipes/{recipeId}/ingredients", method=RequestMethod.GET)
	public ResponseEntity<Map<Integer, List<Ingredient>>> getRecipeIngredients(
			@PathVariable ("recipeId") Integer recipeId);

	@RequestMapping(value="/recipes/{recipeId}/ingredients/{ingredientId}", method=RequestMethod.POST)
	public ResponseEntity<Void> addIngredientToRecipe(
			@PathVariable ("recipeId") Integer recipeId,
			@PathVariable ("ingredientId") Integer ingredientId,
			@RequestBody Ingredient ingredient);
	
	@RequestMapping(value="/recipes/{recipeId}/ingredients/{ingredientId}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateRecipeIngredient(
			@PathVariable ("recipeId") Integer recipeId, 
			@PathVariable ("ingredientId") Integer ingredientId,
			@RequestBody Ingredient ingredient);
	
	@RequestMapping(value="/recipes/{recipeId}/ingredients/{ingredientId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> removeIngredientFromRecipe(
			@PathVariable ("recipeId") Integer recipeId, 
			@PathVariable ("ingredientId") Integer ingredientId);
	
	@RequestMapping(value="/recipes/{recipeId}/images", method=RequestMethod.GET)
	public ResponseEntity<List<Image>> getRecipeImages(
			@PathVariable ("recipeId") Integer recipeId);
	
	@RequestMapping(value="/recipes/{recipeId}/images/{imageId}", method=RequestMethod.POST)
	public ResponseEntity<Void> addImageToRecipe(
			@PathVariable ("recipeId") Integer recipeId,
			@PathVariable ("imageId") Integer imageId);
	
	@RequestMapping(value="/recipes/{recipeId}/images/{imageId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> removeImageFromRecipe(
			@PathVariable ("recipeId") Integer recipeId,
			@PathVariable ("imageId") Integer imageId);
	
	@RequestMapping(value="/recipes/{recipeId}/categories", method=RequestMethod.GET)
	public ResponseEntity<List<Category>> getRecipeCategories(
			@PathVariable ("recipeId") Integer recipeId);
	
	@RequestMapping(value="/recipes/{recipeId}/categories/{categoryId}", method=RequestMethod.POST)
	public ResponseEntity<Void> addCategoryToRecipe(
			@PathVariable ("recipeId") Integer recipeId,
			@PathVariable ("categoryId") Integer imageId);
	
	@RequestMapping(value="/recipes/{recipeId}/categories/{categoryId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> removeCategoryFromRecipe(
			@PathVariable ("recipeId") Integer recipeId,
			@PathVariable ("categoryId") Integer imageId);
	
	
}
