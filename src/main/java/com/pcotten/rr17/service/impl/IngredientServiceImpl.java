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
import com.pcotten.rr17.storage.service.SQLBuilder;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.IngredientService;

@Component
public class IngredientServiceImpl implements IngredientService {

	DatabaseConfig config = new DatabaseConfig();
	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public IngredientServiceImpl(){
		
	}
	
	
	public Ingredient createIngredient(Ingredient ingredient) throws SQLException{
		
		conn = manager.getConnection();
		int r = 0;

		pstmt = conn.prepareStatement("INSERT INTO ingredient (name, description) "
				+ "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, ingredient.getName());
		pstmt.setString(2, ingredient.getDescription());

		r = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			ingredient.setId(id);
		}
		if (r != 0){
			System.out.println("Ingredient " + ingredient.getName() + " successfully inserted into database");
		}
		else {
			System.out.println("Ingredient " + ingredient.getName() + " not created");
		}

		return ingredient;
	}
	

	public List<Map<String, Object>> queryIngredients(Recipe recipe){
		
		String ingredientList = "";
		for (String s : recipe.getIngredients().keySet()){
			ingredientList += SQLBuilder.toSQLString(s) + ",";
		}
		if (ingredientList.length() > 0 ){
			ingredientList = ingredientList.substring(0, ingredientList.length()-1);
			sql = "SELECT id, name FROM ingredient WHERE name IN (" + ingredientList + ");";
			
			conn = manager.getConnection();
			List<Map<String, Object>> ingredientMapList = manager.mapListQuery(conn, sql);
	
			System.out.println("MapList : " + ingredientMapList);
			return ingredientMapList;
		}
		return null;
	}


	public int updateIngredient(Ingredient ingredient) throws SQLException {

		conn = manager.getConnection();
		int r = 0;

		pstmt = conn.prepareStatement("UPDATE ingredient SET name = ?, description = ? WHERE id = ?");
		pstmt.setString(1, ingredient.getName());
		pstmt.setString(2, ingredient.getDescription());
		pstmt.setInt(3, ingredient.getId());

		r = pstmt.executeUpdate();
		if (r != 0){
			System.out.println("Ingredient " + ingredient.getName() + " successfully updated in database");
		}
		else {
			System.out.println("Ingredient " + ingredient.getName() + " not updated");
		}
		return r;
	}


	public int deleteIngredient(Integer id) throws SQLException {
		int result = -1;

		result = DbCommonFunctions.deleteEntity("ingredient", id);
		if (result != -1){
			System.out.println("Successfully removed ingredient with id " + id);
		}
		else {
			System.out.println("Unable to remove ingredient entity with id " + id);
		}
		
		return result;
	}


	public Ingredient getIngredientById(Integer id) {
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", id.toString());
		
		return (Ingredient) manager.retrieveSingleEntity(constraints, Ingredient.class);
	}


	@Override
	public List<Category> getIngredientCategories(Integer id) {
		List<Category> categories = new ArrayList<Category>();
		conn = manager.getConnection();
		try {
			pstmt = conn.prepareStatement("SELECT * FROM categories_by_ingredientid WHERE ingredientId = ?");
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
	public Ingredient createRecipeIngredient(Ingredient ingredient, Integer recipeId) {
		try {
			if (!ingredientExists(ingredient)) {
				ingredient = this.createIngredient(ingredient);
			}
			else {
				ingredient.setId(getIngredientId(ingredient.getName()));
			}
			if (ingredient.getId() != null) {
				linkIngredientToRecipe(ingredient, recipeId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredient;
	}

	private void linkIngredientToRecipe(Ingredient ingredient, Integer recipeId) {
		try {
			if (conn.isClosed())
				conn = manager.getConnection();
			
			pstmt = conn.prepareStatement("INSERT INTO ingredient_recipe (ingredientId, recipeId, quantity, quantityUnit) "
					+ "VALUES (?, ?, ?, ?)");
			pstmt.setInt(1, ingredient.getId());
			pstmt.setInt(2, recipeId);
			pstmt.setFloat(3, ingredient.getQuantity());
			pstmt.setString(4, ingredient.getQuantityUnit());
			
			int r =pstmt.executeUpdate();
			if (r == 1)
				System.out.println("Successfully linked ingredient to recipe");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}


	@Override
	public Ingredient createPantryIngredient(Ingredient ingredient, Integer pantryId) {
		try {
			if (!ingredientExists(ingredient)) {
				ingredient = this.createIngredient(ingredient);
			}
			else {
				ingredient.setId(getIngredientId(ingredient.getName()));
			}
			if (ingredient.getId() != null) {
				linkIngredientToPantry(ingredient, pantryId);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ingredient;	
		
	}


	private Integer getIngredientId(String name) {
		conn = manager.getConnection();
		try {
			pstmt = conn.prepareStatement("SELECT id FROM ingredient WHERE name = ?");
			pstmt.setString(1, name);
			
			ResultSet result = pstmt.executeQuery();
			if (result.next()) {
				return result.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	private boolean ingredientExists(Ingredient ingredient) {
		conn = manager.getConnection();
		int count = 0;
		try {
			pstmt = conn.prepareStatement("SELECT count(*) AS 'COUNT' FROM ingredient WHERE name = ?");
			pstmt.setString(1, ingredient.getName());
			
			ResultSet result = pstmt.executeQuery();
			if (result.next()) {
				count = result.getInt("COUNT");
				if (count > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}


	private void linkIngredientToPantry(Ingredient ingredient, Integer pantryId) {
		try {
			if (conn.isClosed())
				conn = manager.getConnection();
			
			pstmt = conn.prepareStatement("INSERT INTO pantry_ingredient (pantryId, ingredientId, quantity, quantityUnit) "
					+ "VALUES (?, ?, ?, ?)");
			pstmt.setInt(1, pantryId);
			pstmt.setInt(2, ingredient.getId());
			pstmt.setFloat(3, ingredient.getQuantity());
			pstmt.setString(4, ingredient.getQuantityUnit());
			
			int r =pstmt.executeUpdate();
			if (r == 1)
				System.out.println("Successfully linked ingredient to pantry");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
