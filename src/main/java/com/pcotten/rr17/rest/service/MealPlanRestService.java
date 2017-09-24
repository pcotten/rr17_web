package com.pcotten.rr17.rest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;

@Controller
public interface MealPlanRestService {
	
	// covered by UserRestService /users/{userid}/mealPlans/{mealPlanId}
//	@RequestMapping(value="/mealplans/{mealPlanId}", method=RequestMethod.PUT)
//	public ResponseEntity<Void> updateMealPlan(
//			@PathVariable ("mealPlanId") Integer mealPlanId,
//			@RequestBody MealPlan mealPlan);
	
//	@RequestMapping(value="/mealplans/{mealPlanId}", method=RequestMethod.DELETE)
//	public ResponseEntity<Void> deleteMealPlan(
//			@PathVariable ("mealPlanId") Integer mealPlanId);
	
	@RequestMapping(value="/mealplans/{mealPlanId}/meals", method=RequestMethod.GET)
	public ResponseEntity<List<Meal>> getMealPlanMeals(
			@PathVariable ("mealPlanId") Integer mealPlanId);

	// covered by mealRestService /meals/{mealId}  -- mealPlanId unnecessary
//	@RequestMapping(value="/mealplans/{mealPlanId}/meals/{mealId}", method=RequestMethod.GET)
//	public ResponseEntity<Meal> getMealPlanMeal(
//			@PathVariable ("mealPlanId") Integer mealPlanId,
//			@PathVariable ("mealId") Integer mealId);
	
	@RequestMapping(value="/mealplans/{mealPlanId}/meals/{mealId}", method=RequestMethod.POST)
	public ResponseEntity<Void> addMealToMealPlan(
			@PathVariable ("mealPlanId") Integer mealPlanId,
			@PathVariable ("mealId") Integer mealId);
			
	@RequestMapping(value="/mealplans/{mealPlanId}/meals/{mealId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> removeMealFromMealPlan(
			@PathVariable ("mealPlanId") Integer mealPlanId,
			@PathVariable ("mealId") Integer mealId);

}
