package com.pcotten.rr17.service.impl;

import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.dao.CategoryDAO;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.service.CategoryService;

@Component
public class CategoryServiceImpl implements CategoryService {
	
	@Inject
	DatabaseManager manager;
	@Inject
	CategoryDAO categoryDAO;
	
	public CategoryServiceImpl(){
		
	}
	
	public Category getCategory(Integer id){
		
		Category category = categoryDAO.getCategory(id);
		
		return category;

	}

	public Category createCategory(Category category) throws SQLException{
		
		category = categoryDAO.createCategory(category);
		
		if (category.getId() != null){
			System.out.println("Category '" + category.getName() 
			+ "' successfully inserted into database with id " + category.getId());
		}
		else {
			System.out.println("Failed to create category '" + category.getName() + "'");
		}

		return category;
	}
	
	public boolean updateCategory(Category category) throws SQLException{
		
		boolean success = false;
		int result = categoryDAO.updateCategory(category);
		
		if (result > 0){
			System.out.println("Category '" + category.getName() + "' successfully updated in database");
			success = true;
		}
		else {
			System.out.println("Failed to update category");
		}

		return success;
	}
	
	public boolean deleteCategory(Integer id) throws SQLException{
		
		boolean success = false;
		int result = -1;

		result = categoryDAO.deleteCategory(id);
		
		if (result != -1){
			System.out.println("Successfully removed category with id " + id);
			success = true;
		}
		else {
			System.out.println("Unable to remove image category with id " + id);
		}
		
		return success;
	}

	@Override
	public List<Category> getRecipeCategories(Integer recipeId) {

		List<Category> categories = categoryDAO.getRecipeCategories(recipeId);
		
		return categories;
	}

	@Override
	public List<Category> getIngredientCategories(Integer ingredientId) {
		
		List<Category> categories = categoryDAO.getIngredientCategories(ingredientId);
		
		return categories;
	}

	@Override
	public List<Category> getCookbookCategories(Integer cookbookId) {
		
		List<Category> categories = categoryDAO.getCookbookCategories(cookbookId);
		
		return categories;
	}

	@Override
	public boolean addCategoryToRecipe(Integer categoryId, Integer recipeId) {
		
		boolean success = false;
		
		Integer result = categoryDAO.addCategoryToRecipe(categoryId, recipeId);
		if (result > 0) {
			System.out.println("Successfully added category to recipe");
			success = true;
		}
		else {
			System.out.println("Failed to add category to recipe");
		}
		return success;
	}

	@Override
	public boolean addCategoryToIngredient(Integer categoryId, Integer ingredientId) {
		
		boolean success = false;
		
		Integer result = categoryDAO.addCategoryToIngredient(categoryId, ingredientId);
		if (result > 0) {
			System.out.println("Successfully added category to ingredient");
			success = true;
		}
		else {
			System.out.println("Failed to add category to ingredient");
		}
		return success;
	}

	@Override
	public boolean addCategoryToCookbook(Integer categoryId, Integer cookbookId) {
		
		boolean success = false;
		
		Integer result = categoryDAO.addCategoryToCookbook(categoryId, cookbookId);
		if (result > 0) {
			System.out.println("Successfully added category to cookbook");
			success = true;
		}
		else {
			System.out.println("Failed to add category to cookbook");
		}
		return success;
	}

	@Override
	public boolean removeCategoryFromRecipe(Integer categoryId, Integer recipeId) {

		boolean success = false;
		
		Integer result = categoryDAO.removeCategoryFromRecipe(categoryId, recipeId);
		if (result > 0) {
			System.out.println("Successfully removed category from recipe");
			success = true;
		}
		else {
			System.out.println("Failed to remove category from recipe");
		}
		return success;
	}

	@Override
	public boolean removeCategoryFromIngredient(Integer categoryId, Integer ingredientId) {

		boolean success = false;
		
		Integer result = categoryDAO.removeCategoryFromIngredient(categoryId, ingredientId);
		if (result > 0) {
			System.out.println("Successfully removed category from ingredient");
			success = true;
		}
		else {
			System.out.println("Failed to remove category from ingredient");
		}
		return success;
	}

	@Override
	public boolean removeCategoryFromCookbook(Integer categoryId, Integer cookbookId) {

		boolean success = false;
		
		Integer result = categoryDAO.removeCategoryFromCookbook(categoryId, cookbookId);
		if (result > 0) {
			System.out.println("Successfully removed category from cookbook");
			success = true;
		}
		else {
			System.out.println("Failed to remove category from cookbook");
		}
		return success;
	}


}
