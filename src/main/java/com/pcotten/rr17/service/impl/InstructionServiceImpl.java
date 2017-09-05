package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseConfig;
import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.model.Instruction;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.InstructionService;

@Component
public class InstructionServiceImpl implements InstructionService{

	DatabaseConfig config = new DatabaseConfig();
	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public InstructionServiceImpl(){
		
	}
	
public Instruction insertNewInstruction(Instruction instruction) throws SQLException{
		
		conn = manager.getConnection();
		
		pstmt = conn.prepareStatement("INSERT INTO instruction (orderIndex, text, recipeId) "
				+ "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setInt(1, instruction.getOrderIndex());
		pstmt.setString(2, instruction.getText());
		pstmt.setInt(3, instruction.getRecipeId());

		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			instruction.setId(id);
		}
		if (instruction.getId() != null){
			System.out.println("Instruction successfully inserted into database");
		}
		else {
			System.out.println("Instruction not created");
		}
			
		return instruction;
	}

	public int updateInstruction(Instruction instruction) throws SQLException{
		conn = manager.getConnection();
		int r = 0;

		pstmt = conn.prepareStatement("UPDATE instruction SET orderIndex = ?, text = ?, recipeId = ? WHERE id = ?");
		pstmt.setInt(1, instruction.getOrderIndex());
		pstmt.setString(2, instruction.getText());
		pstmt.setInt(3, instruction.getRecipeId());
		pstmt.setInt(4, instruction.getId());

			r = pstmt.executeUpdate();
			if (r != 0){
				System.out.println("Instruction successfully updated in database");
			}
			else {
				System.out.println("Instruction not updated");
			}
			return r;
	}
	
	public int deleteInstruction(Integer id) throws SQLException{
		int result = -1;
		
		result = DbCommonFunctions.deleteEntity("instruction", id);
		if (result != -1){
			System.out.println("Successfully removed instruction with id " + id);
		}
		else {
			System.out.println("Unable to remove instruction entity with id " + id);
		}
		
		return result;
	}
	
	public List<Map<String, Object>> queryInstructions(Recipe recipe){
		
		sql = "SELECT * FROM instruction WHERE recipeId = " + recipe.getId();
		
		conn = manager.getConnection();
		List<Map<String, Object>> instructionMapList = manager.mapListQuery(conn, sql);

		System.out.println("MapList : " + instructionMapList);
		return instructionMapList;
	}

	public Instruction getInstructionById(Integer id) {
		
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", id.toString());
		
		return (Instruction) manager.retrieveSingleEntity(constraints, Instruction.class);
	}
}
