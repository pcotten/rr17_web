package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pcotten.rr17.dao.CategoryDAO;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.User;

public class CategoryDAOImpl extends JdbcDaoSupport implements CategoryDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class CategoryRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category createCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteCategory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getRecipeCategories(Integer recipeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getMealCategories(Integer mealId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getIngredientCategories(Integer ingredientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getCookbookCategories(Integer cookbookId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


