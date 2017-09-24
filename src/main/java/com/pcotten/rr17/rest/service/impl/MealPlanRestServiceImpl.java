package com.pcotten.rr17.rest.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.rest.service.MealPlanRestService;
import com.pcotten.rr17.service.MealPlanService;
import com.pcotten.rr17.service.MealService;

@RestController
public class MealPlanRestServiceImpl implements MealPlanRestService {

	@Inject
	MealPlanService service;
	@Inject
	MealService mealService;
	
//	@Override
//	public ResponseEntity<Void> updateMealPlan(
//			@PathVariable Integer mealPlanId, 
//			@RequestBody MealPlan mealPlan) {
//		
//		ResponseEntity<Void> response = null;
//		mealPlan.setId(mealPlanId);
//		try {
//			boolean success = service.updateMealPlan(mealPlan);
//			
//			if (success) {
//				response = ResponseEntity.ok().build();
//			}
//			else {
//				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return response;
//	}

//	@Override
//	public ResponseEntity<Void> deleteMealPlan(
//			@PathVariable Integer mealPlanId) {
//
//		ResponseEntity<Void> response = null;
//		try {
//			boolean success = service.deleteMealPlan(mealPlanId);
//			
//			if (success) {
//				response = ResponseEntity.ok().build();
//			}
//			else {
//				response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return response;
//	}

	@Override
	public ResponseEntity<List<Meal>> getMealPlanMeals(
			@PathVariable Integer mealPlanId) {
		
		ResponseEntity<List<Meal>> response = null;
		
		List<Meal> meals = service.getMealPlanMeals(mealPlanId);
		
		if (meals != null && !meals.isEmpty()) {
			response = ResponseEntity.ok(meals);
		}
		else {
			response = new ResponseEntity<List<Meal>>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

//	@Override
//	public ResponseEntity<Meal> getMealPlanMeal(
//			@PathVariable Integer mealPlanId, 
//			@PathVariable Integer mealId) {
//
//		ResponseEntity<Meal> response = null;
//		Meal meal = mealService.getMeal(mealId);
//		
//		if (meal != null) {
//			response = ResponseEntity.ok(meal);
//		}
//		else {
//			response = new ResponseEntity<Meal>(HttpStatus.NOT_FOUND);
//		}
//		return response;
//	}
	
	@Override
	public ResponseEntity<Void> addMealToMealPlan(
			@PathVariable ("mealPlanId") Integer mealPlanId,
			@PathVariable ("mealId") Integer mealId) {
		
		ResponseEntity<Void> response = null;
		
		boolean success = service.addMealToMealPlan(mealPlanId, mealId);
		
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
		
	}

	@Override
	public ResponseEntity<Void> removeMealFromMealPlan(
			@PathVariable Integer mealPlanId, 
			@PathVariable Integer mealId) {
		
		ResponseEntity<Void> response = null;
		
		boolean success = service.removeMealFromMealPlan(mealPlanId, mealId);
		
		if (success) {
			response = ResponseEntity.ok().build();
		}
		else {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

}
