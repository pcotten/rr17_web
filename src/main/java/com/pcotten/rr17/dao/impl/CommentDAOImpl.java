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

import com.pcotten.rr17.dao.CommentDAO;
import com.pcotten.rr17.model.Comment;

@Component
public class CommentDAOImpl extends JdbcDaoSupport implements CommentDAO{
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class CommentRowMapper implements RowMapper<Comment> {

		@Override
		public Comment mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Comment comment = new Comment();
			comment.setId(rs.getInt("id"));
			comment.setUserId(rs.getInt("userId"));
			comment.setUsername(rs.getString("username"));
			comment.setParentId(rs.getInt("parentId"));
			comment.setText(rs.getString("text"));
			comment.setTimestamp(rs.getDate("timestamp"));
			
			return comment;
		}		
	}

	@Override
	public Comment getComment(Integer id) {
		Comment comment = (Comment) getJdbcTemplate().query(
				"SELECT * FROM comment WHERE id = ?", 
				new Object[] {id}, 
				new CommentRowMapper());
		
		return comment;
	}

	@Override
	public Comment createComment(Comment comment) {
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps =con.prepareStatement("INSERT INTO comment (text, timestamp, "
						+ "userId, parentId, recipeId) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, comment.getText());
				ps.setObject(2, comment.getTimestamp());
				ps.setInt(3, comment.getUserId());
				if (comment.getParentId() != null){
					ps.setInt(4, comment.getParentId());
				}
				else {
					ps.setNull(4, java.sql.Types.INTEGER);
				}
				ps.setInt(5, comment.getRecipeId());
				
				return ps;
			}
			
		}, holder);
		if (holder.getKey() != null) {
			comment.setId(holder.getKey().intValue());
		}
		
		return comment;
	}

	@Override
	public Integer updateComment(Comment comment) {
		Integer result = getJdbcTemplate().update(
				"UPDATE comment SET text= ?, timestamp = ?, userId = ?, "
						+ "username = ?, parentId = ?, recipeId = ? WHERE id = ?", 
				new Object[] {
						comment.getText(),
						comment.getTimestamp(),
						comment.getUserId(),
						comment.getUsername(),
						comment.getParentId(),
						comment.getRecipeId(),
						comment.getId()
				});
		
		return result;
	}

	@Override
	public Integer deleteComment(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM comment WHERE id = ?", 
				new Object[] {id});
		
		return result;
	}

	@Override
	public List<Comment> getComments(Integer recipeId) {
		List<Comment> comments = (List<Comment>) getJdbcTemplate().query(
				"SELECT * FROM comments_by_recipeid WHERE recipeId = ?", 
				new Object[] {recipeId}, 
				new CommentRowMapper());
		
		return comments;
	}

	@Override
	public List<Comment> getChildComments(Integer commentId) {
		List<Comment> comments = (List<Comment>) getJdbcTemplate().query(
				"SELECT * FROM comments_by_commentid WHERE commentId = ?", 
				new Object[] {commentId}, 
				new CommentRowMapper());
		
		return comments;
	}
}


