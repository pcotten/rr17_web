package com.pcotten.rr17.rest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.model.User;

@RequestMapping("/")
public interface UserRestService {
	
	@RequestMapping(value="/users", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<Void> createUser(
			@RequestBody User user, 
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/users/{userId}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<User> getUser(
			@PathVariable ("userId") Integer userId);
	
	@RequestMapping(value="/users/{userId}", method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<Void> updateUser(
			@PathVariable ("userId") Integer userId,
			@RequestBody User user);
	
	@RequestMapping(value="/users/{userId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(
			@PathVariable ("userId") Integer userId);
	
	@RequestMapping(value="/users/{userId}/cookbooks", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Cookbook>> getCookbooks(
			@PathVariable ("userId") Integer userId);
	
	@RequestMapping(value="/users/{userId}/cookbooks", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<Void> createCookbook(
			@PathVariable ("userId") Integer userId,
			@RequestBody Cookbook cookbook,
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/users/{userId}/cookbooks/{cookbookId}", method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<Void> updateCookbook(
			@PathVariable ("userId") Integer userId,
			@PathVariable ("cookbookId") Integer cookbookId,
			@RequestBody Cookbook cookbook);

	@RequestMapping(value="/users/{userId}/cookbooks/{cookbookId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCookbook(
			@PathVariable ("userId") Integer userId,
			@PathVariable ("cookbookId") Integer cookbookId);
		
	@RequestMapping(value="/users/{userId}/recipes", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Recipe>> getRecipes(
			@PathVariable ("userId") Integer userId);
	
	@RequestMapping(value="/users/{userId}/recipes", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<Void> createRecipe(
			@PathVariable ("userId") Integer userId,
			@RequestBody Recipe recipe,
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/users/{userId}/recipes/{recipeId}", method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<Void> updateRecipe(
			@PathVariable ("userId") Integer userId,
			@PathVariable ("recipeId") Integer recipeId,
			@RequestBody Recipe recipe);

	@RequestMapping(value="/users/{userId}/recipes/{recipeId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteRecipe(
			@PathVariable ("userId") Integer userId,
			@PathVariable ("recipeId") Integer recipeId);
	
	@RequestMapping(value="/users/{userId}/pantry/ingredients", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Ingredient>> getPantryIngredients(
			@PathVariable ("userId") Integer userId);
	
	@RequestMapping(value="/users/{userId}/pantry/ingredients", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<Void> createPantryIngredient(
			@PathVariable ("userId") Integer userId,
			@RequestBody Ingredient ingredient,
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/users/{userId}/pantry/ingredients/{ingredientId}", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	public ResponseEntity<Void> updatePantryIngredient(
			@PathVariable ("userId") Integer userId, 
			@PathVariable ("ingredientId") Integer ingredientId,
			@RequestBody Ingredient ingredient);
	
	@RequestMapping(value="/users/{userId}/pantry/ingredients/{ingredientId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deletePantryIngredient(
			@PathVariable ("userId") Integer userId,
			@PathVariable ("ingredientId") Integer ingredientId);
	
	@RequestMapping(value="/users/{userId}/meals", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Meal>> getMeals(
			@PathVariable ("userId") Integer userId);
	
	@RequestMapping(value="/users/{userId}/meals", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<Void> createMeal(
			@PathVariable ("userId") Integer userId,
			@RequestBody Meal meal,
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/users/{userId}/meals/{mealId}", method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<Void> updateMeal(
			@PathVariable ("userId") Integer userId,
			@PathVariable ("mealId") Integer mealId,
			@RequestBody Meal meal);

	@RequestMapping(value="/users/{userId}/meals/{mealId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteMeal(
			@PathVariable ("userId") Integer userId,
			@PathVariable ("mealId") Integer mealId);
	
	@RequestMapping(value="/users/{userId}/mealplans", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<MealPlan>> getMealPlans(
			@PathVariable ("userId") Integer userId);
	
	@RequestMapping(value="/users/{userId}/mealplans", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<Void> createMealPlan(
			@PathVariable ("userId") Integer userId,
			@RequestBody MealPlan mealPlan,
			UriComponentsBuilder uriBuilder);
	
	@RequestMapping(value="/users/{userId}/mealplans/{mealPlanId}", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	public ResponseEntity<Void> updateMealPlan(
			@PathVariable ("userId") Integer userId,
			@PathVariable ("mealPlanId") Integer mealPlanId,
			@RequestBody MealPlan mealplan);

	@RequestMapping(value="/users/{userId}/mealplans/{mealPlanId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteMealPlan(
			@PathVariable ("userId") Integer userId,
			@PathVariable ("mealPlanId") Integer mealPlanId);
}
