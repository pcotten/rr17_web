package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.dao.CookbookDAO;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.CookbookService;
import com.pcotten.rr17.service.RecipeService;

@Component
public class CookbookServiceImpl implements CookbookService {
	
	@Inject
	DatabaseManager manager;
	@Inject
	RecipeService recipeService;
	@Inject
	CookbookDAO cookbookDAO;
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public CookbookServiceImpl(){
		
	}
	
	
	public Cookbook createCookbook(Cookbook cookbook, Integer userId) throws SQLException{

		int r = 0;
		conn = manager.getConnection();
		cookbook = cookbookDAO.createCookbook(cookbook);
		if (cookbook.getId() != null){
			r = 1;
		}
		if (r != 0){
			System.out.println("Cookbook entity " + cookbook.getTitle() + " successfully inserted into database");
		}
		else {
			System.out.println("Unable to complete cookbook insert - failed to insert cookbook entity");
			throw new SQLException();
		}
						
		r = linkRecipesToCookbook(cookbook);
		if (r != 0){
			System.out.println("Cookbook recipes successfully linked in database");
		}
		else {
			System.out.println("Unable to complete cookbook insert - failed to link recipes");
			throw new SQLException();
		}
		
		r = cookbookDAO.linkCookbookToUser(cookbook.getId(), userId);
		if (r != 0){
			System.out.println("Cookbook successfully linked to user in database");
		}
		else {
			System.out.println("Unable to complete cookbook insert - failed to link cookbook to user");
			throw new SQLException();
		}
		
		r = linkCookbookToCategories(cookbook);
		if (r != 0){
			System.out.println("Cookbook successfully linked to categories in database");
		}
		else {
			System.out.println("Unable to complete cookbook insert - failed to link cookbook to categories");
			throw new SQLException();
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


	public Cookbook getCookbookById(Integer id) {
		
		return cookbookDAO.getCookbook(id);

	}


	@Override
	public List<Recipe> getCookbookRecipes(Integer id) throws SQLException {
		List<Recipe> recipes = new ArrayList<Recipe>();
		conn = manager.getConnection();

		pstmt = conn.prepareStatement("SELECT * FROM recipes_by_cookbookid WHERE cookbookId = ?");		
		pstmt.setInt(1, id);
		
		ResultSet result = pstmt.executeQuery();
		while (result.next()) {
			Recipe recipe = new Recipe();
			recipe.setId(result.getInt("id"));
			recipe.setTitle(result.getString("title"));
			recipe.setDescription(result.getString("description"));
			recipe.setOwner(result.getInt("owner"));
			recipe.setAttributedTo(result.getString("attributedTo"));
			recipe.setOvenTemp(result.getInt("ovenTemp"));
			recipe.setNumberOfServings(result.getInt("numberOfServings"));
			recipe.setServingSize(result.getInt("servingSize"));
			recipe.setServingSizeUnit(result.getString("servingSize"));
			recipe.setCookTime(result.getInt("cookTime"));
			recipe.setCookTimeUnit(result.getString("cookTimeUnit"));
			recipe.setPrepTime(result.getInt("prepTime"));
			recipe.setPrepTimeUnit(result.getString("prepTimeUnit"));
			recipe.setRating(result.getInt("rating"));
			recipe.setLastPrepared(result.getDate("lastPrepared"));
			
			recipes.add(recipe);
		}
		
		return recipes;
	}
	
	@Override
	public Recipe createCookbookRecipe(Integer cookbookId, Recipe recipe) throws SQLException {
	
		if (!recipeService.recipeExists(recipe)) {
			recipe = recipeService.createRecipe(recipe, getCookbookOwner(cookbookId));
			cookbookDAO.addRecipeToCookbook(cookbookId, recipe.getId());
		}
		else {
			if (recipe != null && recipe.getId() != null) {
				cookbookDAO.addRecipeToCookbook(cookbookId, recipe.getId());
			}
			recipe = null;
			// will generate conflict - UI should fetch conflicting recipe 
			// and give the user the chance to insert it into the cookbook
		}

		return recipe;
	}

	@Override
	public boolean deleteCookbookRecipe(Integer cookbookId, Integer recipeId) throws SQLException {
		
		int r = -1;
		boolean success = false;
		
		conn = manager.getConnection();
	
		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM recipe_cookbook WHERE cookbookId = ? AND recipeId = ?");
		pstmt.setInt(1, cookbookId);
		pstmt.setInt(2, recipeId);
		
		r = pstmt.executeUpdate();
		
		if (r == 0) {
			success = true;
			System.out.println("Removed recipe " + recipeId + " from cookbook " + cookbookId);
		}
		else {
			success = false;
			System.out.println("Unable to remove recipe " + recipeId + " from cookbook " + cookbookId);
		}
		
		return success;
	}


	@Override
	public List<Category> getCookbookCategories(Integer id) throws SQLException {
		List<Category> categories = new ArrayList<Category>();
		conn = manager.getConnection();
		
		pstmt = conn.prepareStatement("SELECT * FROM categories_by_cookbookid WHERE cookbookId = ?");		
		pstmt.setInt(1, id);
		
		ResultSet result = pstmt.executeQuery();
		while (result.next()) {
			Category category = new Category();
			category.setId(result.getInt("id"));
			category.setName(result.getString("name"));
			category.setDescription(result.getString("description"));

			categories.add(category);
		}

		return categories;
	}
	
	/*
	 *  Cookbook is considered pre-existing if its title and owner match
	 */
	
	@Override
	public boolean cookbookExists(Integer userId, Cookbook cookbook) throws SQLException {
		
		conn = manager.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM cookbooks_by_userid "
				+ "WHERE title = ? AND owner = ?");
		pstmt.setString(1, cookbook.getTitle());
		pstmt.setInt(2, userId);

		return manager.isExists(pstmt);
	}

	private Integer getCookbookOwner(Integer cookbookId) {
		Cookbook cookbook = getCookbookById(cookbookId);
		return cookbook.getOwner();
	}
	
//	private Cookbook insertCookbookEntity(Cookbook cookbook) throws SQLException {
//
//		conn = manager.getConnection();
//		int r = 0;
//		pstmt = conn.prepareStatement("INSERT INTO cookbook (title, owner)"
//				+ "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
//		pstmt.setString(1, cookbook.getTitle());
//		pstmt.setInt(2, cookbook.getOwner());
//		r = pstmt.executeUpdate();
//		ResultSet rs = pstmt.getGeneratedKeys();
//		if (rs.next()){
//			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
//			cookbook.setId(id);
//		}
//		if (r != 0 && cookbook.getId() != null){
//			System.out.println("Cookbook entity successfully inserted into database");
//		}
//		else {
//			System.out.println("Cookbook creation failed - unable to insert cookbook entity");
//			throw new SQLException();
//		}
//		
//		return cookbook;
//	}


	private int linkRecipesToCookbook(Cookbook cookbook) throws SQLException{
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		if (cookbook.getRecipes() != null && !cookbook.getRecipes().isEmpty()){
			for (Recipe recipe : cookbook.getRecipes()){
				
				result = cookbookDAO.addRecipeToCookbook(cookbook.getId(), recipe.getId());
			}
		}
		else return 1;
		return result;
	}	

	private Integer linkCookbookToCategories(Cookbook cookbook) throws SQLException {
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		if (!cookbook.getCategories().isEmpty()){
			for (Category category : cookbook.getCategories()){
				
				result = cookbookDAO.linkCategoryToCookbook(cookbook.getId(), category.getId());
			}
		}
		else return 1;
		return result;	
	}


	
//	private boolean linkRecipeToCookbook(Integer cookbookId, Integer recipeId) throws SQLException {
//
//		int r = 0;
//		
//		conn = manager.getConnection();
//		
//		PreparedStatement pstmt = conn.prepareStatement("INSERT INTO recipe_cookbook (userId, recipeId) VALUES (?, ?)");
//		pstmt.setInt(1, cookbookId);
//		pstmt.setInt(2, recipeId);
//		
//		r = pstmt.executeUpdate();
//		
//		if (r != 0) {
//			System.out.println("Successfully linked recipe " + recipeId + " to cookbook " + cookbookId);
//			return true;
//		}
//		
//		System.out.println("Failed to link recipe " + recipeId + " to cookbook " + cookbookId);
//		return false;
//		
//	}


}
