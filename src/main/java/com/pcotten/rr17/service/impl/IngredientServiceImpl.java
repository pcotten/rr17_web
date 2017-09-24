package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.dao.IngredientDAO;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.service.CategoryService;
import com.pcotten.rr17.service.IngredientService;

@Component
public class IngredientServiceImpl implements IngredientService {
	
	@Inject
	DatabaseManager manager;
	@Inject
	IngredientDAO ingredientDAO;
	@Inject
	CategoryService categoryService;
	
	public IngredientServiceImpl(){
		
	}
	
	public Ingredient getIngredient(Integer id) {
		
		return ingredientDAO.getIngredient(id);
		
	}
	
	public Ingredient createIngredient(Ingredient ingredient) throws SQLException{
		
		ingredient = ingredientDAO.createIngredient(ingredient);
		
		if (ingredient.getId() != null){
			System.out.println("Ingredient " + ingredient.getName() + " successfully inserted into database with id " + ingredient.getId());
		}
		else {
			System.out.println("Ingredient " + ingredient.getName() + " not created");
		}

		return ingredient;
	}
	

	public boolean updateIngredient(Ingredient ingredient) throws SQLException {

		boolean success = false;
		int result = ingredientDAO.updateIngredient(ingredient);
		
		if (result != 0){
			System.out.println("Ingredient " + ingredient.getName() + " successfully updated in database");
			success = true;
		}
		else {
			System.out.println("Ingredient " + ingredient.getName() + " not updated");
		}
		return success;
	}


	public boolean deleteIngredient(Integer id) throws SQLException {
		
		boolean success = false;
		int result = ingredientDAO.deleteIngredient(id);

		if (result != -1){
			System.out.println("Successfully removed ingredient with id " + id);
			success = true;
		}
		else {
			System.out.println("Unable to remove ingredient entity with id " + id);
		}
		
		return success;
	}

	
	@Override
	public List<Category> getIngredientCategories(Integer id) {
		
		List<Category> categories = categoryService.getIngredientCategories(id);
		
		return categories;
	}
	
	
	@Override
	public List<Ingredient> getRecipeIngredients(Integer recipeId) {

		List<Ingredient> ingredients = ingredientDAO.getRecipeIngredients(recipeId);
		
		return ingredients;
	}
	
	
	@Override
	public boolean addIngredientToRecipe(Ingredient ingredient, Integer recipeId) throws SQLException {

		boolean success = false;
		int result = 0;
		
		if (!ingredientExists(ingredient)) {
			ingredient = ingredientDAO.createIngredient(ingredient);
		}
		else {
			ingredient.setId(getIngredientId(ingredient.getName()));
		}
		if (ingredient.getId() != null) {
			result = ingredientDAO.createRecipeIngredient(ingredient, recipeId);
			if (result > 0) {
				System.out.println("Successfully added ingredient to recipe");
				success = true;
			}
			else {
				System.out.println("Failed to add ingredient to recipe");
			}
		}

		return success;
	}
	
	
	@Override
	public boolean updateRecipeIngredient(Ingredient ingredient, Integer recipeId) {

		boolean success = false;
		int result = ingredientDAO.updateRecipeIngredient(ingredient, recipeId);
		
		if (result > 0) {
			System.out.println("Successfully updated ingredient");
			success = true;
		}
		else {
			System.out.println("Failed to update ingredient");
		}
		
		return success;
	}

	
	@Override
	public boolean removeIngredientFromRecipe(Integer ingredientId, Integer recipeId) {
		
		boolean success = false;
		int result = -1;
		
		result = ingredientDAO.removeIngredientFromRecipe(ingredientId, recipeId);
		if (result != -1) {
			System.out.println("Successfully removed ingredient from recipe");
			success = true;
		}
		else {
			System.out.println("Failed to remove ingredient from recipe");
		}
		
		return success;
	}
	
	
	@Override
	public List<Ingredient> getPantryIngredients(Integer pantryId) {

		List<Ingredient> ingredients = ingredientDAO.getPantryIngredients(pantryId);
		
		return ingredients;
	}
	
	@Override
	public boolean addIngredientToPantry(Ingredient ingredient, Integer pantryId) throws SQLException {

		boolean success = false;
		int result = 0;
		
		if (!ingredientExists(ingredient)) {
			ingredient = ingredientDAO.createIngredient(ingredient);
		}
		else {
			ingredient.setId(getIngredientId(ingredient.getName()));
		}
		if (ingredient.getId() != null) {
			result = ingredientDAO.createPantryIngredient(ingredient, pantryId);
			if (result > 0) {
				System.out.println("Successfully added ingredient to pantry");
				success = true;
			}
			else {
				System.out.println("Failed to add ingredient to pantry");
			}
		}	

		return success;	
		
	}

	
	@Override
	public boolean updatePantryIngredient(Ingredient ingredient, Integer pantryId) {
		
		boolean success = false;
		int result = ingredientDAO.updatePantryIngredient(pantryId, ingredient);
		
		if (result > 0) {
			System.out.println("Successfully updated ingredient to pantry");
			success = true;
		}
		else {
			System.out.println("Failed to update ingredient to pantry");
		}
		return success;
	}

	
	@Override
	public boolean removeIngredientFromPantry(Integer ingredientId, Integer pantryId) {

		
		boolean success = false;
		int result = -1;
		
		result = ingredientDAO.removeIngredientFromRecipe(ingredientId, pantryId);
		if (result != -1) {
			System.out.println("Successfully removed ingredient from pantry");
			success = true;
		}
		else {
			System.out.println("Failed to remove ingredient from pantry");
		}
		
		return success;
	}


	@Override
	public Integer getIngredientId(String name) {
		
		Ingredient ingredient = ingredientDAO.getIngredientByName(name);
		
		return ingredient.getId();
	}
	
	
	public boolean ingredientExists(Ingredient ingredient) throws SQLException {
		
		Connection conn = manager.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT count(*) FROM ingredient WHERE name = ?");
		pstmt.setString(1, ingredient.getName());
		
		return manager.isExists(pstmt);
	}




	



}
