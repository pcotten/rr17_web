package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.dao.CookbookDAO;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.CategoryService;
import com.pcotten.rr17.service.CookbookService;
import com.pcotten.rr17.service.RecipeService;

@Component
public class CookbookServiceImpl implements CookbookService {
	
	@Inject
	DatabaseManager manager;
	@Inject
	RecipeService recipeService;
	@Inject
	CategoryService categoryService;
	@Inject
	CookbookDAO cookbookDAO;

	public CookbookServiceImpl(){
		
	}
	
	
	public Cookbook getCookbook(Integer id) {
		
		return cookbookDAO.getCookbook(id);

	}

	
	public Cookbook createCookbook(Cookbook cookbook, Integer userId) throws SQLException{

		cookbook = cookbookDAO.createCookbook(cookbook, userId);
		
		if (cookbook.getId() != null){
			System.out.println("Cookbook entity " + cookbook.getTitle() 
			+ " successfully inserted into database with id " + cookbook.getId());
		}
		else {
			System.out.println("Failed to create cookbook " + cookbook.getTitle());
		}
		
		cookbookDAO.linkCookbookToUser(cookbook.getId(), userId);
		
		for (Category category : cookbook.getCategories()) {
			cookbookDAO.linkCategoryToCookbook(cookbook.getId(), category.getId());
		}
		
		return cookbook;
	}


	public boolean updateCookbook(Cookbook cookbook) throws SQLException {
		
		int r = 0;
		boolean success = false;
		
		r = cookbookDAO.updateCookbook(cookbook);

		if (r != 0 && cookbook.getId() != null){
			System.out.println("Cookbook entity successfully updated in database");
			success =  true;
		}
		else {
			System.out.println("Cookbook update failed");
			success = false;
		}
		return success;
	}


	public boolean deleteCookbook(Integer id) throws SQLException {
		
		int r = -1;
		boolean success = false;
		
		r = cookbookDAO.deleteCookbook(id);

		if (r != -1){
			System.out.println("Successfully removed cookbook with id " + id);
			success = true;
		}
		else {
			System.out.println("Unable to remove cookbook entity with id " + id);
			success = false;
		}
		
		return success;
	}


	@Override
	public List<Recipe> getCookbookRecipes(Integer cookbookId) throws SQLException {
		List<Recipe> recipes = recipeService.getCookbookRecipes(cookbookId);
		
		return recipes;
	}
	
//	@Override
//	public Recipe createCookbookRecipe(Integer cookbookId, Recipe recipe) throws SQLException {
//	
//		if (!recipeService.recipeExists(recipe)) {
//			recipe = recipeService.createRecipe(recipe, getCookbookOwner(cookbookId));
//			cookbookDAO.addRecipeToCookbook(cookbookId, recipe.getId());
//		}
//		else {
//			if (recipe != null && recipe.getId() != null) {
//				cookbookDAO.addRecipeToCookbook(cookbookId, recipe.getId());
//			}
//			recipe = null;
//			// will generate conflict - UI should fetch conflicting recipe 
//			// and give the user the chance to insert it into the cookbook
//		}
//
//		return recipe;
//	}

	@Override
	public boolean addRecipeToCookbook(Integer cookbookId, Integer recipeId) {
		int result = cookbookDAO.deleteCookbookRecipe(cookbookId, recipeId);
		
		boolean success = false;
		
		if (result >= 0) {
			success = true;
			System.out.println("Successfully added recipe to cookbook");
		}
		else {
			success = false;
			System.out.println("Failed to remove recipe from cookbook");
		}
		
		return success;
	}

	@Override
	public boolean removeRecipeFromCookbook(Integer cookbookId, Integer recipeId) throws SQLException {
		
		int result = cookbookDAO.deleteCookbookRecipe(cookbookId, recipeId);
	
		boolean success = false;
		
		if (result >= 0) {
			success = true;
			System.out.println("Removed recipe " + recipeId + " from cookbook " + cookbookId);
		}
		else {
			success = false;
			System.out.println("Failed to remove recipe " + recipeId + " from cookbook " + cookbookId);
		}
		
		return success;
	}


	@Override
	public List<Category> getCookbookCategories(Integer id) throws SQLException {
		
		List<Category> categories = categoryService.getCookbookCategories(id);

		return categories;
	}
	
	/*
	 *  Cookbook is considered pre-existing if its title and owner match
	 */
	
	@Override
	public boolean cookbookExists(Integer userId, Cookbook cookbook) throws SQLException {
		
		Connection conn = manager.getConnection();

		PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM cookbooks_by_userid "
				+ "WHERE title = ? AND owner = ?");
		ps.setString(1, cookbook.getTitle());
		ps.setInt(2, userId);

		return manager.isExists(ps);
	}

	@Override
	public Integer getCookbookOwner(Integer cookbookId) {
		Cookbook cookbook = getCookbook(cookbookId);
		return cookbook.getOwner();
	}


	@Override
	public List<Cookbook> getCookbooks(Integer userId) {

		List<Cookbook> cookbooks = cookbookDAO.getUserCookbooks(userId);
		
		return cookbooks;
	}




}
