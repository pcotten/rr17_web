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

import org.apache.commons.dbutils.QueryRunner;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcotten.rr17.storage.service.DatabaseConfig;
import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Instruction;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.RecipeService;

@Component
public class RecipeServiceImpl implements RecipeService{

	@Inject
	DatabaseManager manager;
	@Inject
	IngredientServiceImpl ingredientService;
	@Inject
	InstructionServiceImpl instructionService;
	
	Connection conn = null;

	Map<String, String> constraints = new HashMap<String, String>();
	
	public RecipeServiceImpl(){
		
	}
	
	
	public Recipe createRecipe(Recipe recipe, Integer userId) throws SQLException{
		
		int r = 0;
		conn = manager.getConnection();
		recipe = insertRecipeEntity(recipe);
		if (recipe.getId() != null){
			r = 1;
		}
		if (r != 0){
			System.out.println("Recipe entity " + recipe.getTitle() + " successfully inserted into database");
		}
		else {
			System.out.println("Unable to complete recipe insert - failed to insert recipe entity");
			throw new SQLException();
		}
		
		r = linkRecipeToUser(recipe, userId);
		if (r != 0){
			System.out.println("Recipe  " + recipe.getTitle() + " successfully linked to user " + userId);
		}
		else {
			System.out.println("Unable to complete recipe insert - failed to link recipe to user");
			throw new SQLException();
		}
						
		r = insertRecipeIngredients(recipe);
		if (r != 0){
			System.out.println("Recipe ingredients successfully inserted into database");
		}
		else {
			System.out.println("Unable to complete recipe insert - failed to insert ingredients");
			throw new SQLException();
		}
						
		r = linkIngredientsToRecipe(recipe);
		if (r != 0){
			System.out.println("Recipe ingredients successfully linked in database");
		}
		else {
			System.out.println("Unable to complete recipe insert - failed to link ingredients");
			throw new SQLException();
		}
		
		r = insertRecipeInstructions(recipe);
		if (r != 0){
			System.out.println("Recipe instructions successfully inserted in database");
		}
		else {
			System.out.println("Unable to complete recipe insert - failed to insert instructions");
			throw new SQLException();
		}
		
		// insert categories
		// link categories to recipe
		// insert images
				
		return recipe;
	}


	

	public Recipe getRecipeByRecipeId(Integer recipeId) throws SQLException{
		
		conn = manager.getConnection();
		Recipe recipe = new Recipe();
		
		// retrieve entity
		constraints.clear();
		constraints.put("id", recipeId.toString());
		recipe = (Recipe) manager.retrieveSingleEntity(constraints, Recipe.class);
		
	// retrieve ingredients
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ingredients_by_recipeid WHERE recipeId = ?");
		pstmt.setInt(1, recipeId);
		
		ResultSet resultSet = pstmt.executeQuery();
		Map<String, Map<String, Object>> ingredientsMap = new HashMap<String, Map<String, Object>>();
		while (resultSet.next()){
			Map<String, Object> quantityMap = new HashMap<String, Object>();
			quantityMap.put("quantity", resultSet.getInt("quantity"));
			quantityMap.put("quantityUnit", resultSet.getString("quantityUnit"));
			ingredientsMap.put(resultSet.getString("name"), quantityMap);
		}
		recipe.setIngredients(ingredientsMap);
		
	// retrieve instructions
		pstmt = conn.prepareStatement("SELECT * FROM instruction WHERE recipeId = ?");
		pstmt.setInt(1, recipeId);
		
		resultSet = pstmt.executeQuery();
		Map<Integer, String> instructionMap = new HashMap<Integer, String>();
		while (resultSet.next()){
			instructionMap.put(resultSet.getInt("orderIndex"), resultSet.getString("text"));
		}
		recipe.setInstructions(instructionMap);
	
	
	// retrieve images
		pstmt = conn.prepareStatement("SELECT * FROM image WHERE recipeId = ?");
		pstmt.setInt(1, recipeId);
		
		resultSet = pstmt.executeQuery();
		List<String> stringList = new ArrayList<String>();
		while (resultSet.next()){
			stringList.add(resultSet.getString("imagePath"));
		}
		recipe.getImages().addAll(stringList);
	
	// retrieve categories
		pstmt = conn.prepareStatement("SELECT * FROM category_by_recipeid WHERE recipeId = ?");
		pstmt.setInt(1, recipeId);
		
		resultSet = pstmt.executeQuery();
		stringList.clear();
		while (resultSet.next()){
			stringList.add(resultSet.getString("name"));
		}
		recipe.getCategories().addAll(stringList);

		return recipe;
	}
	
	public boolean updateRecipe(Recipe recipe) throws SQLException{
		
		int r = 0;
		boolean success = false;
		
		PreparedStatement pstmt = conn.prepareStatement("UPDATE recipe SET title = ?, description = ?, owner = ?, attributedTo = ?, "
				+ "numberOfServings = ?, ovenTemp = ?, servingSize = ?, servingSizeUnit = ?, cookTime = ?, "
				+ "cookTimeUnit = ?, prepTime = ?, prepTimeUnit = ? WHERE id = ?");
		pstmt.setString(1, recipe.getTitle());
		pstmt.setString(2, recipe.getDescription());
		pstmt.setInt(3, recipe.getOwner());
		pstmt.setString(4, recipe.getAttributedTo());
		if (recipe.getNumberOfServings() != null){
			pstmt.setInt(5, recipe.getNumberOfServings());
		}
		else 
			pstmt.setNull(5, java.sql.Types.INTEGER);
		if (recipe.getOvenTemp() != null){
			pstmt.setInt(6, recipe.getOvenTemp());
		}
		else 
			pstmt.setNull(6, java.sql.Types.INTEGER);
		if (recipe.getServingSize() != null){
			pstmt.setInt(7, recipe.getServingSize());
		}
		else 
			pstmt.setNull(7, java.sql.Types.INTEGER);
		pstmt.setString(8, recipe.getServingSizeUnit());
		if (recipe.getCookTime() != null){
			pstmt.setInt(9, recipe.getCookTime());
		}
		else 
			pstmt.setNull(9, java.sql.Types.INTEGER);
		
		pstmt.setString(10, recipe.getCookTimeUnit());
		if (recipe.getPrepTime() != null){
			pstmt.setInt(11, recipe.getPrepTime());
		}
		else 
			pstmt.setNull(11, java.sql.Types.INTEGER);
		pstmt.setString(12, recipe.getPrepTimeUnit());
		pstmt.setInt(13, recipe.getId());
		
		r = pstmt.executeUpdate();
		if (r < 0) {
			success = true;
			System.out.println("Successfully updated recipe " + recipe.getId());
		}
		else {
			System.out.println("Failed to update recipe " + recipe.getId());
		}
		
		return success;
	}
	
	public boolean deleteRecipe(Integer id) throws SQLException{
		
		int result = -1;
		boolean success = false;

		result = DbCommonFunctions.deleteEntity("recipe", id);
		if (result != -1){
			System.out.println("Successfully removed recipe with recipeId " + id);
			success = true;
		}
		else {
			System.out.println("Unable to remove recipe entity with recipeId " + id);
		}
		
		return success;
	}

	public Recipe getRecipeById(Integer id) {
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", id.toString());
		return (Recipe) manager.retrieveSingleEntity(constraints, Recipe.class);
	}

	@Override
	public boolean recipeExists(Recipe recipe) throws SQLException {

		conn = manager.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM recipes_by_userId WHERE title = ? AND owner = ?");
		pstmt.setString(1, recipe.getTitle());
		pstmt.setInt(2, recipe.getOwner());
		
		return manager.isExists(pstmt);
	}
	
	@Override
	public List<Recipe> getRecipes(String category, String title, String username) throws SQLException {
		conn = manager.getConnection();
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
		
		
		return null;
	}
	
	private Recipe insertRecipeEntity(Recipe recipe) throws SQLException {

		PreparedStatement pstmt = conn.prepareStatement("INSERT INTO recipe (title, description, "
			+ "owner, attributedTo, numberOfServings, ovenTemp, servingSize, servingSizeUnit, "
			+ "cookTime, cookTimeUnit, prepTime, prepTimeUnit) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, recipe.getTitle());
		pstmt.setString(2, recipe.getDescription());
		pstmt.setInt(3, recipe.getOwner());
		pstmt.setString(4, recipe.getAttributedTo());
		
		if (recipe.getNumberOfServings() != null){
			pstmt.setInt(5, recipe.getNumberOfServings());
		}
		else 
			pstmt.setNull(5, java.sql.Types.INTEGER);
		if (recipe.getOvenTemp() != null){
			pstmt.setInt(6, recipe.getOvenTemp());
		}
		else 
			pstmt.setNull(6, java.sql.Types.INTEGER);
		if (recipe.getServingSize() != null){
			pstmt.setInt(7, recipe.getServingSize());
		}
		else 
			pstmt.setNull(7, java.sql.Types.INTEGER);
		pstmt.setString(8, recipe.getServingSizeUnit());
		if (recipe.getCookTime() != null){
			pstmt.setInt(9, recipe.getCookTime());
		}
		else 
			pstmt.setNull(9, java.sql.Types.INTEGER);
		
		pstmt.setString(10, recipe.getCookTimeUnit());
		if (recipe.getPrepTime() != null){
			pstmt.setInt(11, recipe.getPrepTime());
		}
		else 
			pstmt.setNull(11, java.sql.Types.INTEGER);
		pstmt.setString(12, recipe.getPrepTimeUnit());
		
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			recipe.setId(id);
		}
		return recipe;
	}


	private int insertRecipeIngredients(Recipe recipe) throws SQLException {
		
		constraints.clear();
		
		List<Map<String, Object>> ingredientMapList = ingredientService.queryIngredients(recipe);
		
		if (ingredientMapList != null){
			for (String s : recipe.getIngredients().keySet()){
				boolean exists = false;
				for (Map<String, Object> m : ingredientMapList){
					if (s.equals(m.get("name"))){
						exists = true;
						break;
					}
				}
				if (!exists){
					Ingredient newIngredient = new Ingredient(s, null);
					newIngredient = ingredientService.createIngredient(newIngredient);
					if (newIngredient.getId() == null){
						System.out.println("Ingredient " + s + "could not be added to database.");
						throw new SQLException();
					}
				}
			}
			return 1;
		}
		return 1;
	}
	
	private int linkIngredientsToRecipe(Recipe recipe) throws SQLException {
		int result = 0;
		
		List<Map<String, Object>> ingredientMapList = ingredientService.queryIngredients(recipe);
		
		if (ingredientMapList != null && !ingredientMapList.isEmpty()){
			if (conn.isClosed()){
				conn = manager.getConnection();
			}
			Map<String, Integer> ingredientMap = new HashMap<String, Integer>();
			for (Map<String, Object> m : ingredientMapList){
				ingredientMap.put(m.get("name").toString(), Integer.valueOf(m.get("id").toString()));
			}
			
			for (String m : recipe.getIngredients().keySet()){
				
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ingredient_recipe (recipeId, ingredientId, quantity, quantityUnit) VALUES (?, ?, ?, ?)");
				pstmt.setInt(1, recipe.getId());
				pstmt.setInt(2, ingredientMap.get(m));
				pstmt.setFloat(3, (Float) recipe.getIngredients().get(m).get("quantity"));
				pstmt.setString(4, (String) recipe.getIngredients().get(m).get("quantityUnit"));
				
				result = pstmt.executeUpdate();
				
				if (result != 1){
					System.out.println("Unable to create link between recipe '" + recipe.getTitle() + " and ingredient '" + m + "'");
				}
			}
	
			return result;
		}
		return 1;
	}


	private int insertRecipeInstructions(Recipe recipe) throws SQLException {
		constraints.clear();
		int r = 0;
		List<Map<String, Object>> instructionMapList = instructionService.queryInstructions(recipe);
		System.out.println(instructionMapList);

		for (Integer i : recipe.getInstructions().keySet()){

			Instruction newInstruction = new Instruction(i, recipe.getInstructions().get(i), recipe.getId());
			newInstruction = instructionService.insertNewInstruction(newInstruction);
			if (newInstruction.getId() == null){
				System.out.println("Instruction " + i + " could not be added to database.");
				throw new SQLException();
			}
			r = 1;
		}

		return r;
	}
	
	
	private int linkRecipeToUser(Recipe recipe, Integer userId) throws SQLException {
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		
		PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user_recipe (recipeId, userId) VALUES (?, ?)");
		pstmt.setInt(1, recipe.getId());
		pstmt.setInt(2, userId);
		
		result = pstmt.executeUpdate();
		
		if (result != 1){
			System.out.println("Unable to create link between recipe '" + recipe.getTitle() + " and user " + userId);
		}
		
		return result;
	}
	
}
