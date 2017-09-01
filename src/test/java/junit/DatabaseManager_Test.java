package junit;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.model.User;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class DatabaseManager_Test {

	
	static DatabaseManager dbManager;
	static User user;
	static Recipe recipe;
	static Map<String, String> constraints;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbManager = new DatabaseManager();
		constraints = new HashMap<String, String>();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void checkDatabaseConnection(){
		Connection conn = dbManager.getConnection();
		Assert.assertTrue(conn != null);
	}
	
	@Test
	public void getSingleUser(){
		constraints.clear();
		constraints.put("id", "1");
		User user = (User) dbManager.retrieveSingleEntity(constraints, User.class);
		
		Assert.assertTrue(user.getUsername().equals("pcotten"));
		Assert.assertTrue(user.getFirstName().equals("Paul"));
		Assert.assertTrue(user.getLastName().equals("Cotten"));
		Assert.assertTrue(user.getAge() == 41);
		Assert.assertTrue(user.getState().equals("TX"));
	}
	
	@Test
	public void getSingleIngredient(){
		constraints.clear();
		constraints.put("id", "1");
		Ingredient ingredient = (Ingredient) dbManager.retrieveSingleEntity(constraints, Ingredient.class);
		
		Assert.assertTrue(ingredient.getName().equals("salt"));
	}
	
	
	
	
}
