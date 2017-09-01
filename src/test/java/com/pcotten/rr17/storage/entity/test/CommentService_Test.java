package com.pcotten.rr17.storage.entity.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Comment;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.model.User;
import com.pcotten.rr17.storage.entity.CommentService;
import com.pcotten.rr17.storage.entity.RecipeService;
import com.pcotten.rr17.storage.entity.UserService;
import com.pcotten.rr17.storage.entity.impl.CommentServiceImpl;
import com.pcotten.rr17.storage.entity.impl.RecipeServiceImpl;
import com.pcotten.rr17.storage.entity.impl.UserServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class CommentService_Test {

	DatabaseManager manager;
	User user;
	UserService userService = new UserServiceImpl();
	Recipe recipe;
	RecipeService recipeService = new RecipeServiceImpl();
	LocalDate timestamp;
	Comment comment;
	CommentService commentService = new CommentServiceImpl();

	@Before
	public void init(){
		timestamp = LocalDate.now();
		user = userService.getUserByUsername("pcotten");
		
		recipe = new Recipe();
		recipe.setTitle("Test Recipe");
		recipe.setOwner("testUser1");
		recipe.setAttributedTo("unknown");
		recipe.setDescription("This is the best chocolate cake recipe I have found to date");
		recipe.setOvenTemp(350);
		recipe.setAttributedTo("Unknown");
		recipe.setNumberOfServings(12);
		recipe.setCookTime(30);
		recipe.setCookTimeUnit("minutes");
		recipe.setPrepTime(30);
		recipe.setPrepTimeUnit("minutes");
		Map<String, Object> quantityMap = new HashMap<String, Object>();
		quantityMap.put("quantity", 1f);
		quantityMap.put("quantityUnit", "teaspoon");
		recipe.getIngredients().put("salt", quantityMap);
		recipe.getInstructions().put(1, "Do this.");
		
		try {
			recipe = recipeService.insertNewRecipe(recipe, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		manager = new DatabaseManager();
		comment = new Comment();
		comment.setText("TestComment");
		comment.setTimestamp(timestamp);
		comment.setUsername(user.getUsername());
		comment.setUserId(user.getId());
		comment.setRecipeId(recipe.getId());
		
	}
	
	@Test
	public void CommentService_CRUD() throws SQLException {
		int result = 0;
		
		// Test create operation
		result = commentService.insertNewComment(comment, user.getId());
		assertTrue(result != 0);
		
		comment = commentService.getCommentById(comment.getId());
		
		// Test update operation
		
		comment.setText("Changed comment text");
		
		result = commentService.updateComment(comment);
		assertTrue(result != -1);
		
		// Test delete operation
		result = -1;
		try {
			result = commentService.deleteComment(comment.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(result != -1);
		
		// Check database for user
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM comment WHERE id = " + comment.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		assertTrue(rs.getInt("COUNT(*)") == 0);
		
	}
	
	@After
	public void cleanup() throws SQLException{
		recipeService.deleteRecipe(recipe.getId());
	}
	

}
