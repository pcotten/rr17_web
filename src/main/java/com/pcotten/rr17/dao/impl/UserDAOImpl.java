package com.pcotten.rr17.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.pcotten.rr17.dao.UserDAO;
import com.pcotten.rr17.model.User;

@Component
public class UserDAOImpl extends JdbcDaoSupport implements UserDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
		
	public void initDao() {	}
	
	public User createUser(User user) {
		String sql = "INSERT INTO user (username, password, email, bio, firstName, "
				+ "lastName, age, city, state, country, gender, pantryCode) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		KeyHolder holder = new GeneratedKeyHolder();
		Integer id = getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
				pstmt.setString(11, user.getGender());
				pstmt.setString(12, user.getPantryCode());
				return pstmt;
			}
		}, holder);

		if (id != null) {
			user.setId(id);
		}
		return user;
	}
	
	public Integer updateUser(User user) { 
		Integer result = getJdbcTemplate().update(
				"UPDATE user SET username = ?, password = ?, email = ?, bio = ?, "
				+ "firstName = ?, lastName = ?, age = ?, city = ?, state = ?, "
				+ "country = ?, gender = ?, pantryCode = ? WHERE id = ?", 
				new Object[] {
						user.getUsername(),
						user.getPassword(),
						user.getEmail(),
						user.getBio(),
						user.getFirstName(),
						user.getLastName(),
						user.getAge(),
						user.getCity(),
						user.getState(),
						user.getCountry(),
						user.getGender(),
						user.getPantryCode(),
						user.getId()
				});
		return result;
	}
	
	public Integer deleteUser(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM user WHERE id = ?", 
				new Object[] {id});
		return result;
	}
	
	public User getUser(Integer id) {
		User user = (User) getJdbcTemplate().queryForObject(
				"SELECT * FROM user WHERE id = ?", 
				new Object[] {id}, 
				new UserRowMapper());
		return user;
	}
	
	private class UserRowMapper implements RowMapper<User>{

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			user.setBio(rs.getString("bio"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setAge(rs.getInt("age"));
			user.setCity(rs.getString("city"));
			user.setState(rs.getString("state"));
			user.setCountry(rs.getString("country"));
			user.setGender(rs.getString("gender"));
			user.setPantryCode(rs.getString("pantryCode"));
			return user;
		}
		
	}
}


