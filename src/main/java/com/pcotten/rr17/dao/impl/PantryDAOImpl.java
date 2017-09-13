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
import com.pcotten.rr17.dao.PantryDAO;
import com.pcotten.rr17.model.Pantry;
import com.pcotten.rr17.model.User;

@Component
public class PantryDAOImpl extends JdbcDaoSupport implements PantryDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class PantryRowMapper implements RowMapper<Pantry> {

		@Override
		public Pantry mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Pantry pantry = new Pantry();
			pantry.setId(rs.getInt("pantryId"));
			pantry.setPantryCode(rs.getString("pantryCode"));
			pantry.setDescription(rs.getString("description"));
			
			return pantry;
		}
	}
	
	private static class LimitedUserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			
			return user;
		}
	}

	@Override
	public Pantry getPantry(Integer id) {
		Pantry pantry = getJdbcTemplate().queryForObject(
				"SELECT * FROM pantry WHERE id = ?", 
				new Object[] {id}, 
				new PantryRowMapper());
		
		return pantry;
	}
	
	@Override
	public Pantry createPantry(Pantry pantry) {
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO pantry (pantryCode, description) VALUES (?, ?)", 
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, pantry.getPantryCode());
				ps.setString(2, pantry.getDescription());
			
				return ps;
			}
			
		}, holder);
		if (holder.getKey() != null) {
			pantry.setId(holder.getKey().intValue());
		}
		
		return pantry;
	}

	@Override
	public Integer updatePantry(Pantry pantry) {
		Integer result = getJdbcTemplate().update(
				"UPDATE pantry SET pantryCode = ?, description = ? WHERE id = ?", 
				new Object[] {
						pantry.getPantryCode(),
						pantry.getDescription(),
						pantry.getId()
						});
		return result;
	}

	@Override
	public Integer deletePantry(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM pantry WHERE id = ?", 
				new Object[] {id});
		return result;
	}

	@Override
	public Pantry getUserPantry(Integer userId) {
		Pantry pantry = getJdbcTemplate().queryForObject(
				"SELECT * FROM user_pantry WHERE id = ?", 
				new Object[] {userId}, 
				new PantryRowMapper());
		
		return pantry;
	}

	@Override
	public List<User> getPantryUsers(Integer id) {
		Pantry pantry = getPantry(id);
		List<User> users = getJdbcTemplate().query(
				"SELECT (id, username, firstName, lastName) FROM user WHERE pantryCode = ?", 
				new Object[] {pantry.getPantryCode()}, 
				new LimitedUserRowMapper());
		
		return users;
		}

	@Override
	public List<Pantry> getPantries() {
		
		List<Pantry> pantries = getJdbcTemplate().query(
				"SELECT * FROM pantry", 
				new Object[] {}, 
				new PantryRowMapper());
	
		return pantries;
	}


}


