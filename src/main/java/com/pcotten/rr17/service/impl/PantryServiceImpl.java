package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Pantry;
import com.pcotten.rr17.service.PantryService;

@Component
public class PantryServiceImpl implements PantryService{

//	DatabaseConfig config = new DatabaseConfig();
	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public PantryServiceImpl(){
		
	}
	
	// creates a new Pantry Entity in the database and returns it's pantryCode
	public Pantry createPantry(Pantry pantry) throws SQLException{
		
		int result = 0;
		if (pantry.getPantryCode() == null){
			pantry.setPantryCode(generatePantryCode());
		}

		conn = manager.getConnection();
		pstmt = conn.prepareStatement("INSERT INTO pantry (pantryCode, description) "
				+ "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, pantry.getPantryCode());
		pstmt.setString(2, pantry.getDescription());

		result = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			pantry.setId(id);
		}
		if (result != 0){
			System.out.println("New pantry successfully created in database");
		}
		else System.out.println("Pantry not created");
			
		return pantry;
	}
	
	public int updatePantry(Pantry pantry){
		int result = 0;
		try {
			conn = manager.getConnection();
			pstmt = conn.prepareStatement("UPDATE pantry SET pantryCode = ?, description = ? WHERE id = ?");
			pstmt.setString(1, pantry.getPantryCode());
			pstmt.setString(2, pantry.getDescription());
			pstmt.setInt(3, pantry.getId());

				result = pstmt.executeUpdate();
				if (result != 0){
					System.out.println("Pantry updated in database");
				}
				else System.out.println("Pantry not updated");
				
			} catch (SQLException e) {
				System.out.println("SQLException: Unable to update pantry");
				e.printStackTrace();
			}
			return result;
	}
	
	public int deletePantry(Integer id) throws SQLException{
		int result = -1;

		result = DbCommonFunctions.deleteEntity("pantry", id);
		if (result != -1){
			System.out.println("Successfully removed pantry with id " + id);
		}
		else {
			System.out.println("Unable to remove pantry entity with id " + id);
		}
		
		return result;
	}
	

	
	private String generatePantryCode(){
		String newCode = null;
		boolean unique = true;
		boolean complete = false;
		Map<String, String> constraints = new HashMap<String, String>();
		List<Pantry> pantryList = (List<Pantry>) manager.retrieveEntities("pantry", constraints, Pantry.class);
		while (!complete){
			newCode = RandomStringUtils.randomAlphanumeric(8);
			newCode = newCode.substring(0, 4) + "-" + newCode.substring(4);
			if (!pantryList.isEmpty())
				for (Pantry p: pantryList){
					if (p.getPantryCode().equals(newCode)){
						unique = false;
						break;
					}
					if (pantryList.indexOf(p) == pantryList.size()-1){
						unique = true;
					}
				}
			if (unique == true){
				complete = true;
			}
		}
		
		return newCode;
	}

	public Pantry getPantryById(Integer id) {
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", id.toString());
		
		return (Pantry) manager.retrieveSingleEntity(constraints, Pantry.class);
	}
	
	@Override
	public boolean pantryIngredientExists(Integer userId, Ingredient ingredient) {
		
		conn = manager.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM ingredients_by_pantryid "
					+ "WHERE name = ? AND pantryId = ?");
			pstmt.setString(1, ingredient.getName());
			pstmt.setInt(2, getPantryId(userId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return manager.isExists(pstmt);
	
	}
	
	public Integer getPantryId(Integer userId) {
		Integer pantryId = null;
		try {
			conn = manager.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT pantryId FROM user_pantry WHERE userId = ?");
			pstmt.setInt(1, userId);
			
			ResultSet result = pstmt.executeQuery();
			if (result.next()) {
				pantryId = result.getInt("pantryId");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pantryId;
	}
}
