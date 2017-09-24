package com.pcotten.rr17.rest.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Image;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.rest.service.RecipeRestService;
import com.pcotten.rr17.service.CategoryService;
import com.pcotten.rr17.service.ImageService;
import com.pcotten.rr17.service.IngredientService;
import com.pcotten.rr17.service.RecipeService;

@RestController
public class RecipeRestServiceImpl implements RecipeRestService {
	
	@Inject
	RecipeService service;
	@Inject
	IngredientService ingredientService;
	@Inject
	ImageService imageService;
	@Inject
	CategoryService categoryService;

	@Override
	public ResponseEntity<List<Recipe>> getRecipes(
			@RequestParam String category,
			@RequestParam String title,
			@RequestParam String username) {

		ResponseEntity<List<Recipe>> response = null;
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		try {
			recipes = service.getRecipes(category, title, username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (recipes != null && !recipes.isEmpty()) {
			response = ResponseEntity.ok(recipes);
		}
		else {
			response = new ResponseEntity<List<Recipe>>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	@Override
	public ResponseEntity<Void> updateRecipe(
			@PathVariable Integer recipeId, 
			@RequestBody Recipe recipe) {

		ResponseEntity<Void> response = null;
		
		try {
			boolean success = service.updateRecipe(recipe);
			if (success) {
				response = ResponseEntity.ok().build();
			}
			else {
				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> deleteRecipe(
			@PathVariable Integer recipeId) {
		
		ResponseEntity<Void> response = null;
		
		try {
			boolean success = service.deleteRecipe(recipeId);
			if (success) {
				response = ResponseEntity.ok().build();
			}
			else {
				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<List<Ingredient>> getRecipeIngredients(
			@PathVariable Integer recipeId) {

		ResponseEntity<List<Ingredient>> response = null;
		
		List<Ingredient> ingredients = ingredientService.getRecipeIngredients(recipeId);
		
		if (ingredients != null && !ingredients.isEmpty()) {
			response = ResponseEntity.ok(ingredients);
		}
		else {
			response = new ResponseEntity<List<Ingredient>>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> addIngredientToRecipe(
			@PathVariable Integer recipeId,
			@PathVariable Integer ingredientId,
			@RequestBody Ingredient ingredient) {
		
		ResponseEntity<Void> response = null;
		
		try {
			boolean success = ingredientService.addIngredientToRecipe(ingredient, recipeId);
			if (success) {
				response = ResponseEntity.ok().build();
			}
			else {
				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	@Override
	public ResponseEntity<Void> updateRecipeIngredient(
			@PathVariable Integer recipeId, 
			@PathVariable Integer ingredientId, 
			@RequestBody Ingredient ingredient) {
		
		ResponseEntity<Void> response = null;
	
		boolean success = ingredientService.updateRecipeIngredient(ingredient, recipeId);
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> removeIngredientFromRecipe(
			@PathVariable Integer recipeId, 
			@PathVariable Integer ingredientId) {
		
		ResponseEntity<Void> response = null;
		
		boolean success = ingredientService.removeIngredientFromRecipe(ingredientId, recipeId);
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return response;

	}

	@Override
	public ResponseEntity<List<Image>> getRecipeImages(Integer recipeId) {
		
		ResponseEntity<List<Image>> response = null;
		
		List<Image> images = imageService.getRecipeImages(recipeId);
		
		if (images != null && !images.isEmpty()) {
			response = ResponseEntity.ok(images);
		}
		else {
			response = new ResponseEntity<List<Image>>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> addImageToRecipe(Integer recipeId, Integer imageId) {
		
		ResponseEntity<Void> response = null;
		
		boolean success = imageService.addImageToRecipe(imageId, recipeId);
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@Override
	public ResponseEntity<Void> removeImageFromRecipe(Integer recipeId, Integer imageId) {

		ResponseEntity<Void> response = null;
		
		boolean success = imageService.removeImageFromRecipe(imageId, recipeId);
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

	@Override
	public ResponseEntity<List<Category>> getRecipeCategories(Integer recipeId) {
		
		ResponseEntity<List<Category>> response = null;
		
		List<Category> categories = categoryService.getRecipeCategories(recipeId);
		
		if (categories != null && !categories.isEmpty()) {
			response = ResponseEntity.ok(categories);
		}
		else {
			response = new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> addCategoryToRecipe(Integer recipeId, Integer imageId) {

		ResponseEntity<Void> response = null;
		
		boolean success = categoryService.addCategoryToRecipe(imageId, recipeId);
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> removeCategoryFromRecipe(Integer recipeId, Integer imageId) {
		
		ResponseEntity<Void> response = null;
		
		boolean success = categoryService.removeCategoryFromRecipe(imageId, recipeId);
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

}
