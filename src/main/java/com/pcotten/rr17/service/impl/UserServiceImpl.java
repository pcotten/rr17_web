package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
	
	String sql = null;
	
	public UserServiceImpl(){
		
	}
	
	public User createUser(User user) throws SQLException{

		int r = 0;
		
		if (user.getPantryCode() == null){
			user.setPantryCode(pantryService.createPantry(new Pantry()).getPantryCode());
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
	public boolean updateUser(User user) throws SQLException{
		conn = manager.getConnection();
		PreparedStatement pstmt = null;
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
				return true;
			}
			else {
				System.out.println("User " + user.getUsername() + " not updated");
				return false;
			}
	}
	
	@Override
	public boolean deleteUser(Integer id) throws SQLException{

		int result = -1;

		result = DbCommonFunctions.deleteEntity("user", id);
		if (result != -1){
			System.out.println("Successfully removed recipe with recipeId " + id);
			return true;
		}
		else {
			System.out.println("Unable to remove recipe entity with recipeId " + id);
			return false;
		}
	}

	@Override
	public List<Cookbook> getCookbooks(Integer userId) {
		
		List<Cookbook> cookbooks = new ArrayList<Cookbook>();
		try {
			conn = manager.getConnection();
			PreparedStatement pstmt = null;
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
		
		return cookbooks;
	}

	@Override
	public Cookbook createCookbook(Integer userId, Cookbook cookbook) {
		try {
			cookbook = cookbookService.createCookbook(cookbook, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cookbook;
	}

	@Override
	public List<Ingredient> getPantryIngredients(Integer userId) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		try {
			conn = manager.getConnection();
			PreparedStatement pstmt = null;
			Integer pantryId = pantryService.getPantryId(userId);
			pstmt = conn.prepareStatement("SELECT * FROM ingredients_by_pantryid WHERE pantryId = ?");
			pstmt.setInt(1, pantryId);
			
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				Ingredient ingredient = new Ingredient();
				ingredient.setId(result.getInt("ingredientId"));
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
		
		return ingredients;
	}

	@Override
	public Ingredient createPantryIngredient(Integer userId, Ingredient ingredient) {
		ingredient = ingredientService.createPantryIngredient(ingredient, pantryService.getPantryId(userId));
		
		return ingredient;
	}

	@Override
	public boolean updatePantryIngredient(Integer userId, Integer ingredientId, Ingredient ingredient) {
		int r = 0;
		try {
			conn = manager.getConnection();
			PreparedStatement pstmt = null;
			
			pstmt = conn.prepareStatement("UPDATE pantry_ingredient SET quantity = ?, quantityUnit = ? "
					+ "WHERE pantryId = ? AND ingredientId = ?");
			pstmt.setFloat(1, ingredient.getQuantity());
			pstmt.setString(2, ingredient.getQuantityUnit());
			pstmt.setInt(3, pantryService.getPantryId(userId));
			pstmt.setInt(4, ingredient.getId());
			
			r = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (r != 0){
			System.out.println("Ingredient successfully updated");
			return true;
		}
		else {
			System.out.println("Ingredient not updated");
			return false;
		}
	}

	@Override
	public boolean deletePantryIngredient(Integer userId, Integer ingredientId) {
		
		int r = -1;
		
		conn = manager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("DELETE FROM pantry_ingredient WHERE pantryId = ? AND ingredientId = ?");
			pstmt.setInt(1, pantryService.getPantryId(userId));
			pstmt.setInt(2, ingredientId);
			
			r = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (r == 0) {
			System.out.println("Ingredient " + ingredientId + " deleted from pantry.");
			return true;
		}
		else {
			System.out.println("Failed to delete " + ingredientId + " ingredient from pantry");
			return false;
		}
	}

	@Override
	public List<Meal> getMeals(Integer userId) {
		List<Meal> meals = new ArrayList<Meal>();
		try {
			conn = manager.getConnection();
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement("SELECT * FROM meals_by_userid WHERE userid = ?");
			pstmt.setInt(1, userId);
			
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				Meal meal = new Meal();
				meal.setId(result.getInt("mealId"));
				meal.setName(result.getString("name"));
				Date date = result.getDate("lastPrepared");
				meal.setLastPrepared(date.toLocalDate());
				meals.add(meal);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return meals;
	}

	@Override
	public Meal createMeal(Integer userId, Meal meal) {
		try {
			meal = mealService.createMeal(meal, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return meal;
	}

	@Override
	public boolean updateMeal(Integer userId, Integer mealId, Meal meal) {
		
		int r = 0;
		
		try {
			meal.setId(mealId);
			r = mealService.updateMeal(meal);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (r != 0){
			System.out.println("Meal successfully updated");
			return true;
		}
		else {
			System.out.println("Meal not updated");
			return false;
		}
	}

	@Override
	public boolean deleteMeal(Integer userId, Integer mealId) {
		
		int r = -1;
		
		try {
			r = mealService.deleteMeal(mealId, userId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (r == 0) {
			System.out.println("Ingredient " + mealId + " deleted from pantry.");
			return true;
		}
		else {
			System.out.println("Failed to delete " + mealId + " ingredient from pantry");
			return false;
		}
	}

	@Override
	public List<MealPlan> getMealPlans(Integer userId) {
		List<MealPlan> mealPlans = new ArrayList<MealPlan>();
		try {
			conn = manager.getConnection();
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement("SELECT * FROM mealplans_by_userid WHERE userid = ?");
			pstmt.setInt(1, userId);
			
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				MealPlan mealPlan = new MealPlan();
				mealPlan.setId(result.getInt("mealPlanId"));
				mealPlan.setName(result.getString("name"));
				mealPlan.setOwnerId(result.getInt("ownerId"));
				mealPlan.setMeals(mealService.getMealPlanMeals(mealPlan.getId()));
				mealPlans.add(mealPlan);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mealPlans;
	}

	@Override
	public MealPlan createMealPlan(Integer userId, MealPlan mealPlan) {
		try {
			mealPlan = mealPlanService.createMealPlan(mealPlan, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mealPlan;
	}

	@Override
	public boolean updateMealPlan(Integer userId, Integer mealPlanId, MealPlan mealPlan) {
		
		int r = 0;
		
		try {
			mealPlan.setId(mealPlanId);
			r = mealPlanService.updateMealPlan(mealPlan);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (r != 0){
			System.out.println("MealPlan successfully updated");
			return true;
		}
		else {
			System.out.println("MealPlan not updated");
			return false;
		}
	}

	@Override
	public boolean deleteMealPlan(Integer userId, Integer mealPlanId) {
		
		int r = -1;
		
		try {
			r = mealPlanService.deleteMealPlan(mealPlanId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (r == 0) {
			System.out.println("Ingredient " + mealPlanId + " deleted from pantry.");
			return true;
		}
		else {
			System.out.println("Failed to delete " + mealPlanId + " ingredient from pantry");
			return false;
		}
	}
	
	private User insertUserEntity(User user) throws SQLException{

		conn = manager.getConnection();
		PreparedStatement pstmt = null;
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

	@Override
	public boolean userExists(User user) {
		conn = manager.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM user WHERE username = ?");
			pstmt.setString(1, user.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return manager.isExists(pstmt);
	}








	
	
	
}
