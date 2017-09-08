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

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseConfig;
import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.CookbookService;

@Component
public class CookbookServiceImpl implements CookbookService {

	DatabaseConfig config = new DatabaseConfig();
	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public CookbookServiceImpl(){
		
	}
	
	
	public Cookbook createCookbook(Cookbook cookbook, Integer userId) throws SQLException{

		int r = 0;
		conn = manager.getConnection();
		cookbook = insertCookbookEntity(cookbook);
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
		
		r = linkCookbookToUser(cookbook, userId);
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


	private Cookbook insertCookbookEntity(Cookbook cookbook) throws SQLException {

		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("INSERT INTO cookbook (title, creatorId)"
				+ "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, cookbook.getTitle());
		pstmt.setInt(2, cookbook.getCreatorId());
		r = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			cookbook.setId(id);
		}
		if (r != 0 && cookbook.getId() != null){
			System.out.println("Cookbook entity successfully inserted into database");
		}
		else {
			System.out.println("Cookbook creation failed - unable to insert cookbook entity");
			throw new SQLException();
		}
		
		return cookbook;
	}


	private int linkRecipesToCookbook(Cookbook cookbook) throws SQLException{
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		if (!cookbook.getRecipes().isEmpty()){
			for (Recipe recipe : cookbook.getRecipes()){
				
				pstmt = conn.prepareStatement("INSERT INTO recipe_cookbook (recipeId, cookbookId) VALUES (?, ?)");
				pstmt.setInt(1, recipe.getId());
				pstmt.setInt(2, cookbook.getId());
	
				result = pstmt.executeUpdate();
			}
		}
		else return 1;
		return result;
	}


	private int linkCookbookToUser(Cookbook cookbook, Integer userId) throws SQLException{
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		
		pstmt = conn.prepareStatement("INSERT INTO user_cookbook (userId, cookbookId) VALUES (?, ?)");
		pstmt.setInt(1, userId);
		pstmt.setInt(2, cookbook.getId());

		result = pstmt.executeUpdate();
		
		return result;
	}
	

	private int linkCookbookToCategories(Cookbook cookbook) throws SQLException {
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		if (!cookbook.getCategories().isEmpty()){
			for (Category category : cookbook.getCategories()){
				
				pstmt = conn.prepareStatement("INSERT INTO cookbook_category (cookbookId, categoryId) VALUES (?, ?)");
				pstmt.setInt(1, cookbook.getId());
				pstmt.setInt(2, category.getId());
			
				result = pstmt.executeUpdate();
			}
		}
		else return 1;
		return result;	
	}


	public int updateCookbook(Cookbook cookbook) throws SQLException {
		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("UPDATE cookbook SET title = ?, creatorId = ? WHERE id = ?");
		pstmt.setString(1, cookbook.getTitle());
		pstmt.setInt(2, cookbook.getCreatorId());
		pstmt.setInt(3, cookbook.getId());
		r = pstmt.executeUpdate();

		if (r != 0 && cookbook.getId() != null){
			System.out.println("Cookbook entity successfully updated in database");
		}
		else {
			System.out.println("Cookbook update failed");
			throw new SQLException();
		}
		
		return r;
	}


	public int deleteCookbook(Integer id) throws SQLException {
		int result = -1;

		result = DbCommonFunctions.deleteEntity("cookbook", id);
		if (result != -1){
			System.out.println("Successfully removed cookbook with id " + id);
		}
		else {
			System.out.println("Unable to remove cookbook entity with id " + id);
		}
		
		return result;
	}


	public Cookbook getCookbookById(Integer id) {
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", id.toString());
		
		return (Cookbook) manager.retrieveSingleEntity(constraints, Cookbook.class);
	}


	@Override
	public List<Recipe> getCookbookRecipes(Integer id) {
		List<Recipe> recipes = new ArrayList<Recipe>();
		conn = manager.getConnection();
		try {
			pstmt = conn.prepareStatement("SELECT * FROM recipes_by_cookbookid WHERE cookbookId = ?");		
			pstmt.setInt(1, id);
			
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				Recipe recipe = new Recipe();
				recipe.setId(result.getInt("id"));
				recipe.setTitle(result.getString("title"));
				recipe.setDescription(result.getString("description"));
				recipe.setOwner(result.getString("owner"));
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
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recipes;
	}


	@Override
	public List<Category> getCookbookCategories(Integer id) {
		List<Category> categories = new ArrayList<Category>();
		conn = manager.getConnection();
		try {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categories;
	}
	
	@Override
	public boolean cookbookExists(Integer userId, Cookbook cookbook) {
		
		conn = manager.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM cookbooks_by_userid "
					+ "WHERE title = ? AND userId = ?");
			pstmt.setString(1, cookbook.getTitle());
			pstmt.setInt(2, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
			return manager.isExists(pstmt);
	}
}
