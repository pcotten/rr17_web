package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pcotten.rr17.dao.CommentDAO;
import com.pcotten.rr17.model.Comment;
import com.pcotten.rr17.model.User;

public class CommentDAOImpl extends JdbcDaoSupport implements CommentDAO{
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class commentRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Comment comment = new Comment();
			comment.setId(rs.getInt("id"));
			comment.setUserId(rs.getInt("userId"));
			comment.setUsername(rs.getString("username"));
			comment.setParentId(rs.getInt("parentId"));
			comment.setText(rs.getString("text"));
			comment.setTimestamp(rs.getDate("timetamp"));
			
			return comment;
		}
		
	}
	
}


