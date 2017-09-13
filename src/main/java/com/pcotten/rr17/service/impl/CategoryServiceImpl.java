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
	
	public Integer updateCategory(Category category) throws SQLException{
		
		int result = categoryDAO.updateCategory(category);
		
		if (result > 0){
			System.out.println("Category '" + category.getName() + "' successfully updated in database");
		}
		else {
			System.out.println("Failed to update category");
		}

		return result;
	}
	
	public Integer deleteCategory(Integer id) throws SQLException{
		
		int result = -1;

		result = categoryDAO.deleteCategory(id);
		
		if (result != -1){
			System.out.println("Successfully removed category with id " + id);
		}
		else {
			System.out.println("Unable to remove image category with id " + id);
		}
		
		return result;
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
	public Integer linkCategoryToRecipe(Integer categoryId, Integer recipeId) {
		
		int result = categoryDAO.linkCategoryToRecipe(categoryId, recipeId);
		
		return result;
	}

	@Override
	public Integer linkCategoryToIngredient(Integer categoryId, Integer ingredientId) {
		
		int result = categoryDAO.linkCategoryToIngredient(categoryId, ingredientId);
		
		return result;
	}

	@Override
	public Integer linkCategoryToCookbook(Integer categoryId, Integer cookbookId) {
		
		int result = categoryDAO.linkCategoryToCookbook(categoryId, cookbookId);
		
		return result;
	}


}
