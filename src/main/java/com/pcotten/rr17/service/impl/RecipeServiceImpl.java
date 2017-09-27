package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.dao.RecipeDAO;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Image;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Instruction;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.CategoryService;
import com.pcotten.rr17.service.ImageService;
import com.pcotten.rr17.service.IngredientService;
import com.pcotten.rr17.service.InstructionService;
import com.pcotten.rr17.service.RecipeService;

@Component
public class RecipeServiceImpl implements RecipeService{

	@Inject
	DatabaseManager manager;
	@Inject
	IngredientService ingredientService;
	@Inject
	InstructionService instructionService;
	@Inject
	ImageService imageService;
	@Inject
	CategoryService categoryService;
	@Inject
	RecipeDAO recipeDAO;
	
	
	public RecipeServiceImpl(){
		
	}
	
	
	public Recipe getRecipe(Integer recipeId) {
		
		Recipe recipe = recipeDAO.getRecipe(recipeId);
		recipe.setIngredients(ingredientService.getRecipeIngredients(recipeId));
		recipe.setInstructions(instructionService.getInstructions(recipeId));
		recipe.setImages(imageService.getRecipeImages(recipeId));
		recipe.setCategories(categoryService.getRecipeCategories(recipeId));
		
		return recipe;
	}
	
	
	public Recipe createRecipe(Recipe recipe, Integer userId) throws SQLException{
		
		Map<Integer, List<Ingredient>> ingredients = recipe.getIngredients();
		List<Instruction> instructions = recipe.getInstructions();
		List<Image> images = recipe.getImages();
		List<Category> categories = recipe.getCategories();
	
		recipe = recipeDAO.createRecipe(recipe, userId);
		if (recipe.getId() != null){
			
			//Insert and add ingredients
			for (Integer i : ingredients.keySet()) {
				for (Ingredient ingredient : ingredients.get(i)) {
					ingredientService.addIngredientToRecipe(ingredient, recipe.getId());
				}
			}
			//Link recipe to User
			
			//Insert and add instructions
			for (Instruction instruction : instructions) {
				instruction.setRecipeId(recipe.getId());
				instructionService.createInstruction(instruction);
			}
			//Insert and link images
			for (Image image : images) {
				imageService.createImage(image);
			}
			//Insert and link categories
			for (Category category : categories) {
				categoryService.addCategoryToRecipe(category.getId(), recipe.getId());
			}
			
			
		}
		else {
			System.out.println();
		}
		return recipe;
	}
		
//		r = linkRecipeToCookbook(recipe, cookbookId);
//		if (r != 0){
//			System.out.println("Recipe  " + recipe.getTitle() + " successfully linked to cookbook " + cookbookId);
//		}
//		else {
//			System.out.println("Unable to complete recipe insert - failed to link recipe to user");
//			throw new SQLException();
//		}
//						
//		r = insertRecipeIngredients(recipe);
//		if (r != 0){
//			System.out.println("Recipe ingredients successfully inserted into database");
//		}
//		else {
//			System.out.println("Unable to complete recipe insert - failed to insert ingredients");
//			throw new SQLException();
//		}
//						
//		r = linkIngredientsToRecipe(recipe);
//		if (r != 0){
//			System.out.println("Recipe ingredients successfully linked in database");
//		}
//		else {
//			System.out.println("Unable to complete recipe insert - failed to link ingredients");
//			throw new SQLException();
//		}
//		
//		r = insertRecipeInstructions(recipe);
//		if (r != 0){
//			System.out.println("Recipe instructions successfully inserted in database");
//		}
//		else {
//			System.out.println("Unable to complete recipe insert - failed to insert instructions");
//			throw new SQLException();
//		}
//		
//		// insert categories
//		// link categories to recipe
//		// insert images
//				
//		return recipe;
//	}
//
//
//	

	public boolean updateRecipe(Recipe recipe) throws SQLException{
		
		int result = recipeDAO.updateRecipe(recipe);
		
		boolean success = false;
		
		if (result < 0) {
			success = true;
			System.out.println("Successfully updated recipe " + recipe.getId());
		}
		else {
			System.out.println("Failed to update recipe " + recipe.getId());
		}
		
		return success;
	}
	
	public boolean deleteRecipe(Integer id) throws SQLException{
		
		boolean success = false;
		
		int result = recipeDAO.deleteRecipe(id);
		
		if (result != -1){
			System.out.println("Successfully removed recipe with recipeId " + id);
			success = true;
		}
		else {
			System.out.println("Unable to remove recipe entity with recipeId " + id);
		}
		
		return success;
	}


	@Override
	public boolean recipeExists(Recipe recipe, Integer userId) throws SQLException {

		Connection conn = manager.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM recipes_by_userId WHERE title = ? AND owner = ?");
		pstmt.setString(1, recipe.getTitle());
		pstmt.setInt(2, userId);
		
		return manager.isExists(pstmt);
	}
	
	@Override
	public List<Recipe> getRecipes(String category, String title, String username) throws SQLException {
		//Move this to RecipeDAOImpl
		
		Connection conn = manager.getConnection();
		String sql = "SELECT * FROM recipe WHERE ";
		int paramCount = 0;
		Map<Integer, String> params = new HashMap<Integer, String>();
		if (category != null) {
			sql += "category LIKE ? ";
			params.put(++paramCount, category);
		}
		if (title != null) {
			if (category != null) {
				sql += "AND ";
			}
			sql += "title LIKE ? ";
			params.put(++paramCount, title);
		}
		if (username != null) {
			if (category != null || title != null) {
				sql += "AND ";
			}
			sql += "owner LIKE ? || attributedTo LIKE ?";
			params.put(++paramCount, "%" + username + "%");
			params.put(++paramCount, "%" + username + "%");
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for (Integer i : params.keySet()) {
			pstmt.setString(i, params.get(i));
		}
		
		ResultSet result = pstmt.executeQuery();
		
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		// TODO complete search code
		
		return null;
	}

	@Override
	public Map<Integer, List<Ingredient>> getRecipeIngredients(Integer recipeId) {

		Map<Integer, List<Ingredient>> ingredients = ingredientService.getRecipeIngredients(recipeId);
		
		return ingredients;
	}


	@Override
	public boolean createRecipeIngredient(Integer recipeId, Ingredient ingredient) throws SQLException {

		boolean success = ingredientService.addIngredientToRecipe(ingredient, recipeId);
		
		return success;
	}


	@Override
	public boolean updateRecipeIngredient(Integer recipeId, Ingredient ingredient) {
		
		boolean success = ingredientService.updateRecipeIngredient(ingredient, recipeId);
		
		return success;
	}


	@Override
	public boolean deleteRecipeIngredient(Integer recipeId, Integer ingredientId) {
		
		boolean success = ingredientService.removeIngredientFromRecipe(ingredientId, recipeId);

		return success;
	}


	@Override
	public List<Recipe> getMealRecipes(Integer mealId) {

		List<Recipe> recipes = recipeDAO.getMealRecipes(mealId);
		
		return recipes;
	}


	@Override
	public List<Recipe> getCookbookRecipes(Integer cookbookId) {
		
		List<Recipe> recipes = recipeDAO.getCookbookRecipes(cookbookId);
		
		return recipes;
	}


	@Override
	public List<Recipe> getUserRecipes(Integer userId) {

		List<Recipe> recipes = recipeDAO.getUserRecipes(userId);
		// populate ingredients, instructions, images, and categories
		for (Recipe recipe : recipes) {
			int id = recipe.getId();
			recipe.setInstructions(instructionService.getInstructions(id));
			recipe.setIngredients(ingredientService.getRecipeIngredients(id));
			recipe.setImages(imageService.getRecipeImages(id));
			recipe.setCategories(categoryService.getRecipeCategories(id));
		}
		
		return recipes;
	}
	
}
