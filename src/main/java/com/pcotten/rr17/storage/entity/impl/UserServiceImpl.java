package com.pcotten.rr17.storage.entity.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.dbutils.QueryRunner;
import org.json.simple.parser.JSONParser;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcotten.rr17.storage.entity.PantryService;
import com.pcotten.rr17.storage.entity.UserService;
//import com.pcotten.rr17.storage.service.DatabaseConfig;
import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.storage.service.SQLBuilder;
import com.pcotten.rr17.model.Pantry;
import com.pcotten.rr17.model.User;

@Component
public class UserServiceImpl implements UserService {

//	DatabaseConfig config = new DatabaseConfig();
	QueryRunner queryRunner = new QueryRunner();
	ObjectMapper mapper = new ObjectMapper();
	JSONParser parser = new JSONParser();
	@Inject
	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public UserServiceImpl(){
		
	}
	
	public User insertNewUser(User user) throws SQLException{

		int r = 0;
			
		if (user.getPantryCode() == null){
			PantryService pantryService = new PantryServiceImpl();
			user.setPantryCode(pantryService.insertNewPantry(new Pantry()).getPantryCode());
		}
		conn = manager.getConnection();
		user = insertUserEntity(user);
		if (user.getId() != null){
			r = 1;
		}
		if (r != 0){
			System.out.println("User entity " + user.getUsername() + " successfully inserted into database");
		}
		else {
			System.out.println("Unable to complete user insert - failed to insert user entity");
			throw new SQLException();
		}
		
		return user;
	}
	
	public User insertUserEntity(User user) throws SQLException{

		conn = manager.getConnection();
		int r = 0;
			pstmt = conn.prepareStatement("INSERT INTO user (username, password, email, bio, "
					+ "firstName, lastName, age, city, state, country, gender, pantryCode) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getBio());
			pstmt.setString(5, user.getFirstName());
			pstmt.setString(6, user.getLastName());
			pstmt.setInt(7,  user.getAge());
			pstmt.setString(8, user.getCity());
			pstmt.setString(9, user.getState());
			pstmt.setString(10, user.getCountry());
			pstmt.setString(11, user.getGender().toString());
			pstmt.setString(12, user.getPantryCode());
			
			r = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()){
				Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
				user.setId(id);
			}
			if (r != 0){
				System.out.println("User " + user.getUsername() + " successfully inserted into database");
			}
			else {
				System.out.println("User " + user.getUsername() + " not created");
			}
			
		return user;
	}
	
	public int updateUser(User user) throws SQLException{
		conn = manager.getConnection();
		int r = 0;
			pstmt = conn.prepareStatement("UPDATE user SET username = ?, password = ?, email = ?, "
					+ "bio = ?, firstName = ?, lastName = ?, age = ?, city = ?, state = ?, country = ?, "
					+ "gender = ?, pantryCode = ? WHERE id = ?");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getBio());
			pstmt.setString(5, user.getFirstName());
			pstmt.setString(6, user.getLastName());
			pstmt.setInt(7, user.getAge());
			pstmt.setString(8, user.getCity());
			pstmt.setString(9, user.getState());
			pstmt.setString(10, user.getCountry());
			pstmt.setString(11, user.getGender().toString());
			pstmt.setString(12, user.getPantryCode());
			pstmt.setInt(13, user.getId());
			
			r = pstmt.executeUpdate();
			
			if (r != 0){
				System.out.println("User " + user.getUsername() + " successfully updated");
			}
			else {
				System.out.println("User " + user.getUsername() + " not updated");
			}
			
		return r;
	}
	
	public int deleteUser(Integer id) throws SQLException{

		int result = -1;

		result = DbCommonFunctions.deleteEntity("user", id);
		if (result != -1){
			System.out.println("Successfully removed recipe with recipeId " + id);
		}
		else {
			System.out.println("Unable to remove recipe entity with recipeId " + id);
		}
		
		return result;
	}

	
	public User getUserByUsername(String username){
		
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("username", SQLBuilder.toSQLString(username));
		
		return (User) manager.retrieveSingleEntity(constraints, User.class);
	}
}
