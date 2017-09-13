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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.User;
import com.pcotten.rr17.rest.service.UserRestService;
import com.pcotten.rr17.service.CookbookService;
import com.pcotten.rr17.service.IngredientService;
import com.pcotten.rr17.service.MealPlanService;
import com.pcotten.rr17.service.MealService;
import com.pcotten.rr17.service.PantryService;
import com.pcotten.rr17.service.UserService;

@RestController
public class UserRestServiceImpl implements UserRestService {
	
	@Inject
	private UserService service;
	@Inject
	private IngredientService ingredientService;
	@Inject
	private PantryService pantryService;
	@Inject
	private CookbookService cookbookService;
	@Inject
	private MealService mealService;
	@Inject
	private MealPlanService mealPlanService;

	@Override
	public ResponseEntity<Void> createUser(
			@RequestBody User user, 
			UriComponentsBuilder uriBuilder) {
		
		ResponseEntity<Void> response = null;
		try {
			if (service.userExists(user)) {
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			else {
				user = service.createUser(user);
				HttpHeaders headers = new HttpHeaders();
				headers.setLocation(uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
				response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<User> getUser(
			@PathVariable Integer userId) {
		
		ResponseEntity<User> response = null;
		User user = service.getUserById(userId);
		if (user != null) {
			response = ResponseEntity.ok(user);
		}
		else {
			response = new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
//	@Override
//	public ResponseEntity<User> getUser(String username) {
//		ResponseEntity<User> response = null;
//		response = ResponseEntity.ok(service.getUserByUsername(username));
//		return response;
//	}

	@Override
	public ResponseEntity<Void> updateUser(
			@PathVariable Integer userId, 
			@RequestBody User user) {
		
		ResponseEntity<Void> response = null;
		try {
			boolean success = service.updateUser(user);
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

	@Override
	public ResponseEntity<Void> deleteUser(
			@PathVariable Integer userId) {
		
		ResponseEntity<Void> response = null;
		boolean success;
		try {
			success = service.deleteUser(userId);
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

	@Override
	public ResponseEntity<List<Cookbook>> getCookbooks(
			@PathVariable Integer userId) {
		
		ResponseEntity<List<Cookbook>> response = null;
		try {
			response = null;
			List<Cookbook> cookbooks = service.getCookbooks(userId);
			if (cookbooks != null && !cookbooks.isEmpty()) {
				response = ResponseEntity.ok(cookbooks);
			}
			else {
				response = new ResponseEntity<List<Cookbook>>(HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> createCookbook(
			@PathVariable Integer userId, 
			@RequestBody Cookbook cookbook, 
			UriComponentsBuilder uriBuilder) {
		
		ResponseEntity<Void> response = null;
		
		try {
			if (cookbookService.cookbookExists(userId, cookbook)) {
				response = new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			else {
				cookbook = service.createCookbook(userId, cookbook);
				if (cookbook != null && cookbook.getId() != null) {
					HttpHeaders headers = new HttpHeaders();
					Map<String, Integer> uriVariables = new HashMap<String, Integer>();
					uriVariables.put("userId", userId);
					uriVariables.put("cookbookId", cookbook.getId());
					headers.setLocation(uriBuilder.path("users/{userId}/cookbook/{cookbookId}").buildAndExpand(uriVariables).toUri());
					response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
				}
				else {
					response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseEntity<List<Ingredient>> getPantryIngredients(
			@PathVariable Integer userId) {
		
		ResponseEntity<List<Ingredient>> response = null;
		try {
			List<Ingredient> ingredients = service.getPantryIngredients(userId);
			if (ingredients != null && !ingredients.isEmpty()) {
				response = ResponseEntity.ok(ingredients);
			}
			else {
				response = new ResponseEntity<List<Ingredient>>(HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> createPantryIngredient(
			@PathVariable Integer userId, 
			@RequestBody Ingredient ingredient, 
			UriComponentsBuilder uriBuilder) {
		
		ResponseEntity<Void> response = null;
		
		try {
			if (pantryService.pantryIngredientExists(userId, ingredient)) {
				Integer ingredientId = ingredientService.getIngredientId(ingredient.getName());
				boolean success = service.updatePantryIngredient(userId, ingredientId, ingredient);
				if (success) {
					response = ResponseEntity.ok().build();
				}
				else {
					response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
			}
			else {
				boolean success = service.createPantryIngredient(userId, ingredient);
				if (success) {
					response = new ResponseEntity<Void>(HttpStatus.CREATED);
				}
				else {
					response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> updatePantryIngredient(
			@PathVariable Integer userId, 
			@PathVariable Integer ingredientId, 
			@RequestBody Ingredient ingredient) {
		
		ResponseEntity<Void> response = null;
		
		try {
			boolean success = service.updatePantryIngredient(userId, ingredientId, ingredient);
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

	@Override
	public ResponseEntity<Void> deletePantryIngredient(
			@PathVariable Integer userId, 
			@PathVariable Integer ingredientId) {
		
		ResponseEntity<Void> response = null;
		
		try {
			boolean success;
			success = service.deletePantryIngredient(userId, ingredientId);
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

	@Override
	public ResponseEntity<List<Meal>> getMeals(
			@PathVariable Integer userId) {
		
		ResponseEntity<List<Meal>> response = null;
		try {
			List<Meal> meals = service.getMeals(userId);;
			if (meals != null && !meals.isEmpty()) {
				response = ResponseEntity.ok(meals);
			}
			else {
				response = new ResponseEntity<List<Meal>>(HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> createMeal(
			@PathVariable Integer userId, 
			@RequestBody Meal meal, 
			UriComponentsBuilder uriBuilder) {
		
		ResponseEntity<Void> response = null;
		try {
			if (mealService.mealExists(userId, meal)) {
				response = new ResponseEntity<Void>(HttpStatus.CONFLICT);
			} else {
				meal = service.createMeal(userId, meal);
				if (meal != null && meal.getId() != null) {
					HttpHeaders headers = new HttpHeaders();
					Map<String, Integer> uriVariables = new HashMap<String, Integer>();
					uriVariables.put("userId", userId);
					uriVariables.put("mealId", meal.getId());
					headers.setLocation(uriBuilder.path("users/{userId}/meals/{mealId}").buildAndExpand(uriVariables).toUri());
					response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
				}
				else {
					response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return response;
	}

	@Override
	public ResponseEntity<Void> updateMeal(
			@PathVariable Integer userId, 
			@PathVariable Integer mealId, 
			@RequestBody Meal meal) {
		
		ResponseEntity<Void> response = null;
		boolean success;
		try {
			success = service.updateMeal(userId, mealId, meal);
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

	@Override
	public ResponseEntity<Void> deleteMeal(
			@PathVariable Integer userId, 
			@PathVariable Integer mealId) {
		
		ResponseEntity<Void> response = null;
		boolean success;
		try {
			success = service.deleteMeal(userId, mealId);
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

	@Override
	public ResponseEntity<List<MealPlan>> getMealPlans(
			@PathVariable Integer userId) {
		
		ResponseEntity<List<MealPlan>> response = null;
		try {
			List<MealPlan> meals = service.getMealPlans(userId);
			if (meals != null && !meals.isEmpty()) {
				response = ResponseEntity.ok(meals);
			}
			else {
				response = new ResponseEntity<List<MealPlan>>(HttpStatus.NOT_FOUND);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseEntity<Void> createMealPlan(
			@PathVariable Integer userId, 
			@RequestBody MealPlan mealPlan, 
			UriComponentsBuilder uriBuilder) {
		
		ResponseEntity<Void> response = null;
		try {
			if (mealPlanService.mealPlanExists(userId, mealPlan)) {
				response = new ResponseEntity<Void>(HttpStatus.CONFLICT);
			} else {
				mealPlan = service.createMealPlan(userId, mealPlan);
				if (mealPlan != null && mealPlan.getId() != null) {
					HttpHeaders headers = new HttpHeaders();
					Map<String, Integer> uriVariables = new HashMap<String, Integer>();
					uriVariables.put("userId", userId);
					uriVariables.put("mealId", mealPlan.getId());
					headers.setLocation(uriBuilder.path("users/{userId}/meals/{mealId}").buildAndExpand(uriVariables).toUri());
					response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
				}
				else {
					response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return response;
	}

	@Override
	public ResponseEntity<Void> updateMealPlan(
			@PathVariable Integer userId, 
			@PathVariable Integer mealPlanId, 
			@RequestBody MealPlan mealplan) {
		
		ResponseEntity<Void> response = null;
		boolean success;
		try {
			success = service.updateMealPlan(userId, mealPlanId, mealplan);
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

	@Override
	public ResponseEntity<Void> deleteMealPlan(
			@PathVariable Integer userId, 
			@PathVariable Integer mealPlanId) {
		
		ResponseEntity<Void> response = null;
		boolean success;
		try {
			success = service.deleteMealPlan(userId, mealPlanId);
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
