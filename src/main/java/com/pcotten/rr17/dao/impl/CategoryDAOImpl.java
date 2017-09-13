package com.pcotten.rr17.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.mysql.cj.api.jdbc.Statement;
import com.pcotten.rr17.dao.CategoryDAO;
import com.pcotten.rr17.model.Category;

@Component
public class CategoryDAOImpl extends JdbcDaoSupport implements CategoryDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class CategoryRowMapper implements RowMapper<Category> {

		@Override
		public Category mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Category category = new Category();
			category.setId(rs.getInt("id"));
			category.setName(rs.getString("name"));
			category.setDescription(rs.getString("description"));
			
			return category;
		}
	}

	@Override
	public Category getCategory(Integer id) {
		Category category = (Category) getJdbcTemplate().query(
				"SELECT * FROM category WHERE id = ?", 
				new Object[] {id}, 
				new CategoryRowMapper());
		
		return category;
	}

	@Override
	public Category createCategory(Category category) {
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO category (name, description) VALUES (?, ?)", 
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, category.getName());
				ps.setString(2, category.getDescription());
				return ps;
			}
			
		}, holder);
		if (holder.getKey() != null) {
			category.setId(holder.getKey().intValue());
		}
		
		return category;
	}

	@Override
	public Integer updateCategory(Category category) {
		Integer result = getJdbcTemplate().update(
				"UPDATE category SET name = ?, description = ? WHERE id = ?", 
				new Object[] {
				category.getName(),
				category.getDescription(),
				category.getId()
		});
		
		return result;
	}

	@Override
	public Integer deleteCategory(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM category WHERE id = ?", 
				new Object[] {id});
		
		return result;
	}

	@Override
	public List<Category> getRecipeCategories(Integer recipeId) {
		List<Category> categories = getJdbcTemplate().query(
				"SELECT * FROM categories_by_recipeid WHERE recipeId = ?", 
				new Object[] {recipeId}, 
				new CategoryRowMapper());
		
		return categories;
	}

	@Override
	public List<Category> getMealCategories(Integer mealId) {
		List<Category> categories = getJdbcTemplate().query(
				"SELECT * FROM categories_by_mealid WHERE mealId = ?", 
				new Object[] {mealId}, 
				new CategoryRowMapper());
		
		return categories;
	}

	@Override
	public List<Category> getIngredientCategories(Integer ingredientId) {
		List<Category> categories = getJdbcTemplate().query(
				"SELECT * FROM categories_by_ingredientid WHERE ingredientId = ?", 
				new Object[] {ingredientId}, 
				new CategoryRowMapper());
		
		return categories;
	}

	@Override
	public List<Category> getCookbookCategories(Integer cookbookId) {
		List<Category> categories = getJdbcTemplate().query(
				"SELECT * FROM categories_by_cookbookid WHERE cookbookId = ?", 
				new Object[] {cookbookId}, 
				new CategoryRowMapper());
		
		return categories;
	}

	@Override
	public int linkCategoryToRecipe(Integer categoryId, Integer recipeId) {
		Integer result = getJdbcTemplate().update(
				"call link_category_to_recipe (?, ?)", 
				new Object[] {
						categoryId, 
						recipeId
				});
		
		return result;
	}

	@Override
	public int linkCategoryToIngredient(Integer categoryId, Integer ingredientId) {
		Integer result = getJdbcTemplate().update(
				"call link_category_to_ingredient (?, ?)", 
				new Object[] {
						categoryId, 
						ingredientId
				});
		
		return result;
	}

	@Override
	public int linkCategoryToCookbook(Integer categoryId, Integer cookbookId) {
		Integer result = getJdbcTemplate().update(
				"call link_category_to_cookbook (?, ?)", 
				new Object[] {
						categoryId, 
						cookbookId
				});
		
		return result;
	}
	
}


