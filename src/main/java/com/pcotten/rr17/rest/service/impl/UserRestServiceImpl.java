package com.pcotten.rr17.rest.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.User;
import com.pcotten.rr17.rest.service.UserRestService;
import com.pcotten.rr17.service.UserService;

@RestController
public class UserRestServiceImpl implements UserRestService {
	
	@Inject
	private UserService service;

	@Override
	public ResponseEntity<User> createUser(@RequestBody User user) {
		ResponseEntity<User> response = null;
		try {
			response = ResponseEntity.ok(service.createUser(user));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseEntity<User> getUser(@PathVariable Integer userId) {
		ResponseEntity<User> response = null;
		response = ResponseEntity.ok(service.getUserById(userId));
		return response;
	}
	
//	@Override
//	public ResponseEntity<User> getUser(String username) {
//		ResponseEntity<User> response = null;
//		response = ResponseEntity.ok(service.getUserByUsername(username));
//		return response;
//	}

	@Override
	public ResponseEntity<Void> updateUser(@PathVariable Integer userId, @RequestBody User user) {
		ResponseEntity<Void> response = null;
		try {
			response = service.updateUser(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
		ResponseEntity<Void> response = null;
		try {
			response = service.deleteUser(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseEntity<List<Cookbook>> getCookbooks(@PathVariable String userId) {
		ResponseEntity<List<Cookbook>> response = null;
		response = service.getCookbooks(Integer.valueOf(userId));
		return response;
	}

	@Override
	public ResponseEntity<Cookbook> createCookbook(@PathVariable Integer userId, @RequestBody Cookbook cookbook) {
		ResponseEntity<Cookbook> response = null;
		response = service.createCookbook(userId, cookbook);
		return response;
	}

	@Override
	public ResponseEntity<List<Ingredient>> getPantryIngredients(@PathVariable Integer userId) {
		ResponseEntity<List<Ingredient>> response = null;
		response = service.getPantryIngredients(userId);
		return response;
	}

	@Override
	public ResponseEntity<Ingredient> createPantryIngredient(@PathVariable Integer userId, @RequestBody Ingredient ingredient) {
		ResponseEntity<Ingredient> response = null;
		response = service.createPantryIngredient(userId, ingredient);
		return response;
	}

	@Override
	public ResponseEntity<Void> updatePantryIngredient(@PathVariable Integer userId, @PathVariable Integer ingredientId, @RequestBody Ingredient ingredient) {
		ResponseEntity<Void> response = null;
		response = service.updatePantryIngredient(userId, ingredientId, ingredient);
		return response;
	}

	@Override
	public ResponseEntity<Void> deletePantryIngredient(@PathVariable Integer userId, @PathVariable Integer ingredientId) {
		ResponseEntity<Void> response = null;
		response = service.deletePantryIngredient(userId, ingredientId);
		return response;
	}

	@Override
	public ResponseEntity<List<Meal>> getMeals(@PathVariable Integer userId) {
		ResponseEntity<List<Meal>> response = null;
		response = service.getMeals(userId);
		return response;
	}

	@Override
	public ResponseEntity<Meal> createMeal(@PathVariable Integer userId, @RequestBody Meal meal) {
		ResponseEntity<Meal> response = null;
		response = service.createMeal(userId, meal);
		return response;
	}

	@Override
	public ResponseEntity<Void> updateMeal(@PathVariable Integer userId, @PathVariable Integer mealId, @RequestBody Meal meal) {
		ResponseEntity<Void> response = null;
		response = service.updateMeal(userId, mealId, meal);
		return response;
	}

	@Override
	public ResponseEntity<Meal> deleteMeal(@PathVariable Integer userId, @PathVariable Integer mealId) {
		ResponseEntity<Meal> response = null;
		response = service.deleteMeal(userId, mealId);
		return response;
	}

	@Override
	public ResponseEntity<List<MealPlan>> getMealPlans(@PathVariable Integer userId) {
		ResponseEntity<List<MealPlan>> response = null;
		response = service.getMealPlans(userId);
		return response;
	}

	@Override
	public ResponseEntity<MealPlan> createMealPlan(@PathVariable Integer userId, @RequestBody MealPlan mealPlan) {
		ResponseEntity<MealPlan> response = null;
		response = service.createMealPlan(userId, mealPlan);
		return response;
	}

	@Override
	public ResponseEntity<MealPlan> updateMealPlan(@PathVariable Integer userId, @PathVariable Integer mealPlanId, @RequestBody MealPlan mealplan) {
		ResponseEntity<MealPlan> response = null;
		response = service.updateMealPlan(userId, mealPlanId, mealplan);
		return response;
	}

	@Override
	public ResponseEntity<MealPlan> deleteMealPlan(@PathVariable Integer userId, @PathVariable Integer mealPlanId) {
		ResponseEntity<MealPlan> response = null;
		response = service.deleteMealPlan(userId, mealPlanId);
		return response;
	}




}
