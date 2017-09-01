package com.pcotten.rr17.storage.service;

import com.pcotten.rr17.model.*;


public class SQLBuilder {
	
	public static String toSQLString(String s){
		s = "\"" + s + "\"";
		return s;
	}
	
//	public static String insertModel(Object entry){
//		String sql;
//		if (entry instanceof Comment)
//			sql = insertCategoryModel((Comment)entry);
//		else if (entry instanceof Cookbook)
//			sql = insertCookbookModel((Cookbook)entry);
//		else if (entry instanceof Image)
//			sql = insertImageModel((Image)entry);
//		else if (entry instanceof Ingredient)
//			sql = insertIngredientModel((Ingredient)entry);
//		else if (entry instanceof Instruction)
//			sql = insertInstructionModel((Instruction)entry);
//		else if (entry instanceof Meal)
//			sql = insertMealModel((Meal)entry);
//		else if (entry instanceof MealPlan)
//			sql = insertMealPlanModel((MealPlan)entry);
//		else if (entry instanceof Pantry)
//			sql = insertPantryModel((Pantry)entry);
//		else if (entry instanceof Recipe)
//			sql = insertRecipeModel((Recipe)entry);
//		else if (entry instanceof User)
//			sql = insertUserModel((User)entry);
//		else
//			sql = null;
//		
//		return sql;
//	}
//	
//	private static String insertCookbookModel(Cookbook cookbook){
//		String sql = "INSERT INTO cookbook VALUES (" +
//			IdService.generateId("cookbook") + ",";
//			sql += nullCheck(cookbook.getName()) + ");";
//		return sql;
//	}
//	
//	private static String insertImageModel(Image image){
//		String sql = "INSERT INTO image VALUES (" +
//			IdService.generateId("image") + ",";
//			sql += nullCheck(image.getImagePath()) + ",";
//			sql	+= nullCheck(image.getRecipeId()) + ",";
//			sql += nullCheck(image.getImageText()) + ");";
//		return sql;
//	}
//	
//	private static String insertIngredientModel(Ingredient ingredient){
//		String sql = "INSERT INTO ingredient VALUES (" +
//			IdService.generateId("ingredient") + ",";
//			sql += nullCheck(ingredient.getName()) + ",";
//			sql += nullCheck(ingredient.getQuantity()) + ",";
//			sql += nullCheck(ingredient.getUnitOfMeasure()) + ");";
//		return sql;
//	}
//	
//	private static String insertInstructionModel(Instruction instruction){
//		String sql = "INSERT INTO instruction VALUES (" +
//			IdService.generateId("instruction") + ",";
//			sql += nullCheck(instruction.getInstructionOrderIndex()) +",";
//			sql += nullCheck(instruction.getInstructionText()) + ",";
//			sql += nullCheck(instruction.getRecipeId()) + ");";
//		return sql;
//	}
//	
//	private static String insertMealModel(Meal meal){
//		String sql = "INSERT INTO meal VALUES (" +
//			IdService.generateId("meal") + ",";
//			sql += nullCheck(meal.getName()) + ");";
//		return sql;
//	}
//	
//	private static String insertMealPlanModel(MealPlan mealPlan){
//		String sql = "INSERT INTO mealPlan VALUES (" +
//			IdService.generateId("mealPlan") + ",";
//			sql += nullCheck(mealPlan.getName()) + ");";
//		return sql;
//	}
//	
//	private static String insertPantryModel(Pantry pantry){
//		String sql = "INSERT INTO pantry VALUES (" +
//			IdService.generateId("pantry") + ",";
//			sql += nullCheck(pantry.getUserId()) + ");";
//		return sql;
//	}
//	
//	private static String insertRecipeModel(Recipe recipe){
//		String sql = "INSERT INTO recipe VALUES (" +
//			IdService.generateId("recipe") + ",";
//			sql += nullCheck(recipe.getName()) +  ",";
//			sql += nullCheck(recipe.getDescription()) +  ",";
//			sql += nullCheck(recipe.getServings()) + ",";
//			sql += nullCheck(recipe.getOvenTemp()) + ",";
//			if (recipe.getAttributor() != null){
//				sql += recipe.getAttributor().getId() + ");";
//			}
//			else {
//				sql += "null);";
//			}
//		return sql;
//	}
//
//	private static String insertUserModel(User user){
//		String sql = "INSERT INTO user VALUES (" +
//			IdService.generateId("user") + ",";
//			sql += nullCheck(user.getFirstName()) + ",";
//			sql += nullCheck(user.getLastName()) + ",";
//			sql += nullCheck(user.getEmail()) + ",";
//			sql += nullCheck(user.getUsername()) + ",";
//			sql += nullCheck(user.getPassword()) + ");";
//		return sql;
//	}
//	
//	private static String nullCheck(Object value){
//		if (value == null){
//			return "null";
//		}
//		if (value instanceof Number){
//			return value.toString();
//		}
//		else return ("'" + value.toString() + "'");
//	}
	

}
