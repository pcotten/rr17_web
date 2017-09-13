package com.pcotten.rr17.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import com.pcotten.rr17.dao.CookbookDAO;
import com.pcotten.rr17.model.Cookbook;

@Component
public class CookbookDAOImpl extends JdbcDaoSupport implements CookbookDAO{
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class CookbookRowMapper implements RowMapper<Cookbook> {

		@Override
		public Cookbook mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Cookbook cookbook = new Cookbook();
			cookbook.setId(rs.getInt("id"));
			cookbook.setTitle(rs.getString("title"));
			cookbook.setOwner(rs.getInt("owner"));
			
			return cookbook;
		}
	}

	@Override
	public Cookbook getCookbook(Integer id) {
		Cookbook cookbook = (Cookbook) getJdbcTemplate().queryForObject(
				"SELECT * FROM cookbook WHERE id = ?", 
				new Object[] {id}, 
				new CookbookRowMapper());
		
		return cookbook;
	}

	@Override
	public Cookbook createCookbook(Cookbook cookbook) {
		KeyHolder holder = new GeneratedKeyHolder();
		Integer id = getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO cookbook (title, owner) VALUES (?, ?);";
				PreparedStatement ps = con.prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, cookbook.getTitle());
				ps.setInt(2, cookbook.getOwner());
				
				return ps;
			}
		}, holder);
		if (id != null) {
			cookbook.setId(id);
		}
		
		return cookbook;
	}

	@Override
	public Integer updateCookbook(Cookbook cookbook) {
		Integer result = getJdbcTemplate().update(
				"UPDATE cookbook SET title = ?, owner = ? WHERE id = ?",
				new Object[] {
						cookbook.getTitle(),
						cookbook.getOwner(),
						cookbook.getId()
				});
		
		return result;
	}

	@Override
	public Integer deleteCookbook(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM cookbook WHERE id = ?", 
				new Object[] {id});
		
		return result;
	}

	@Override
	public List<Cookbook> getUserCookbooks(Integer userId) {
		List<Cookbook> cookbooks = getJdbcTemplate().query(
				"SELECT * FROM cookbooks_by_userId WHERE userId = ?",
				new Object[] {userId},
				new CookbookRowMapper());
		
		return cookbooks;
	}

	@Override
	public Integer linkCookbookToUser(Integer cookbookId, Integer userId) {
		Integer result = getJdbcTemplate().update(
				"call link_cookbook_to_user (?, ?)", 
				new Object[] {cookbookId, userId});
		
		return result;
	}

	@Override
	public Integer linkCategoryToCookbook(Integer cookbookId, Integer categoryId) {
		Integer result = getJdbcTemplate().update(
				"call link_category_to_cookbook (?, ?)", 
				new Object[] {
						cookbookId, 
						categoryId
				});
		
		return result;
	}
	
	@Override
	public Integer addRecipeToCookbook(Integer cookbookId, Integer recipeId) {
		Integer result = getJdbcTemplate().update(
				"call add_recipe_to_cookbook (?, ?)", 
				new Object[] {
						cookbookId, 
						recipeId
				});
		
		return result;
	}

	@Override
	public Integer deleteCookbookRecipe(Integer cookbookId, Integer recipeId) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM recipe_cookbook WHERE cookbookId = ? AND recipeId = ?", 
				new Object[] {
						cookbookId, 
						recipeId
				});
		
		return result;
	}
	
}


