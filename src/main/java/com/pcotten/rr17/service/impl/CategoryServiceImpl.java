package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseConfig;
import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.storage.service.SQLBuilder;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.service.CategoryService;

@Component
public class CategoryServiceImpl implements CategoryService {
	
	DatabaseConfig config = new DatabaseConfig();
	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public CategoryServiceImpl(){
		
	}

	public Category insertNewCategory(Category category) throws SQLException{
		
		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("INSERT INTO category (name, description) "
				+ "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, category.getName());
		pstmt.setString(2, category.getDescription());

		r = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			category.setId(id);
		}
		if (r != 0){
			System.out.println("Category '" + category.getName() + "' successfully inserted into database");
		}
		else {
			System.out.println("Category '" + category.getName() + "' not created");
		}

		return category;
	}
	
	public int updateCategory(Category category) throws SQLException{
		conn = manager.getConnection();
		int r = 0;

		pstmt = conn.prepareStatement("UPDATE category SET name = ?, description = ? WHERE id = ?");
		pstmt.setString(1, category.getName());
		pstmt.setString(2, category.getDescription());
		pstmt.setInt(3, category.getId());
		
		r = pstmt.executeUpdate();
		if (r != 0){
			System.out.println("Category '" + category.getName() + "' successfully updated in database");
		}
		else {
			System.out.println("Category '" + category.getName() + "' not updated");
		}

		return r;
	}
	
	public int deleteCategory(Integer id) throws SQLException{
		
		int result = -1;

		result = DbCommonFunctions.deleteEntity("category", id);
		if (result != -1){
			System.out.println("Successfully removed category with id " + id);
		}
		else {
			System.out.println("Unable to remove image category with id " + id);
		}
		
		return result;
	}
	
	public Category getCategoryByName(String name){
		
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("name", SQLBuilder.toSQLString(name));
		
		return (Category) manager.retrieveSingleEntity(constraints, Category.class);

	}
}
