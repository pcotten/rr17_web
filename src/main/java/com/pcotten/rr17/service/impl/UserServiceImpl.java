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

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

//import com.pcotten.rr17.storage.service.DatabaseConfig;
import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.storage.service.SQLBuilder;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.Pantry;
import com.pcotten.rr17.model.User;
import com.pcotten.rr17.service.CookbookService;
import com.pcotten.rr17.service.IngredientService;
import com.pcotten.rr17.service.MealPlanService;
import com.pcotten.rr17.service.MealService;
import com.pcotten.rr17.service.PantryService;
import com.pcotten.rr17.service.UserService;

@Component
public class UserServiceImpl implements UserService {

//	DatabaseConfig config = new DatabaseConfig();
//	QueryRunner queryRunner = new QueryRunner();
//	ObjectMapper mapper = new ObjectMapper();
//	JSONParser parser = new JSONParser();
	
	@Inject
	DatabaseManager manager;
	
	@Inject
	CookbookService cookbookService;
	
	@Inject
	IngredientService ingredientService;
	
	@Inject
	MealPlanService mealPlanService;
	
	@Inject
	MealService mealService;
	
	@Inject
	PantryService pantryService;
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public UserServiceImpl(){
		
	}
	
	public User createUser(User user) throws SQLException{

		int r = 0;
		
		if (user.getPantryCode() == null){
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
	
	@Override
	public User getUserById(Integer userId) {
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", userId.toString());
		
		return (User) manager.retrieveSingleEntity(constraints, User.class);
	}
	
	@Override
	public User getUserByUsername(String username){
		
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("username", SQLBuilder.toSQLString(username));
		
		return (User) manager.retrieveSingleEntity(constraints, User.class);
	}

	@Override
	public ResponseEntity<Void> updateUser(User user) throws SQLException{
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
			
		return ResponseEntity.ok().build();
	}
	
	@Override
	public ResponseEntity<Void> deleteUser(Integer id) throws SQLException{

		int result = -1;

		result = DbCommonFunctions.deleteEntity("user", id);
		if (result != -1){
			System.out.println("Successfully removed recipe with recipeId " + id);
		}
		else {
			System.out.println("Unable to remove recipe entity with recipeId " + id);
		}
		
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<List<Cookbook>> getCookbooks(Integer userId) {
		List<Cookbook> cookbooks = new ArrayList<Cookbook>();
		try {
			conn = manager.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM cookbooks_by_userid WHERE userId = ?");
			pstmt.setInt(1, userId);
			
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				Cookbook cookbook = new Cookbook();
				cookbook.setId(result.getInt("id"));
				cookbook.setTitle(result.getString("title"));
				cookbook.setCreatorId(result.getInt("creatorId"));
				cookbook.setRecipes(cookbookService.getCookbookRecipes(cookbook.getId()));
				cookbook.setCategories(cookbookService.getCookbookCategories(cookbook.getId()));
				cookbooks.add(cookbook);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(cookbooks);
	}

	@Override
	public ResponseEntity<Cookbook> createCookbook(Integer userId, Cookbook cookbook) {
		try {
			cookbook = cookbookService.createCookbook(cookbook, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(cookbook);
	}

	@Override
	public ResponseEntity<List<Ingredient>> getPantryIngredients(Integer userId) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		try {
			conn = manager.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM ingredients_by_userid WHERE userid = ?");
			pstmt.setInt(1, userId);
			
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				Ingredient ingredient = new Ingredient();
				ingredient.setId(result.getInt("id"));
				ingredient.setName(result.getString("name"));
				ingredient.setDescription(result.getString("description"));
				ingredient.setQuantity(result.getFloat("quantity"));
				ingredient.setQuantityUnit(result.getString("quantityUnit"));
				ingredient.setCategories(ingredientService.getIngredientCategories(ingredient.getId()));
				ingredients.add(ingredient);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(ingredients);
	}

	@Override
	public ResponseEntity<Ingredient> createPantryIngredient(Integer userId, Ingredient ingredient) {
		ingredient = ingredientService.createPantryIngredient(ingredient, getPantryId(userId));
		
		return ResponseEntity.ok(ingredient);
	}

	private Integer getPantryId(Integer userId) {
		Integer pantryId = null;
		try {
			conn = manager.getConnection();
			
			pstmt = conn.prepareStatement("SELECT pantryId FROM user_pantry WHERE userId = ?");
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

	@Override
	public ResponseEntity<Void> updatePantryIngredient(Integer userId, Integer ingredientId, Ingredient ingredient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> deletePantryIngredient(Integer userId, Integer ingredientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<Meal>> getMeals(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Meal> createMeal(Integer userId, Meal meal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Meal> updateMeal(Integer userId, Integer mealId, Meal meal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Meal> deleteMeal(Integer userId, Integer mealId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<MealPlan>> getMealPlans(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<MealPlan> createMealPlan(Integer userId, MealPlan mealPlan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<MealPlan> updateMealPlan(Integer userId, Integer mealId, MealPlan mealplan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<MealPlan> deleteMealPlan(Integer userId, Integer mealPlanId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private User insertUserEntity(User user) throws SQLException{

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
}
