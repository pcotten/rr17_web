package com.pcotten.rr17.storage.entity.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.entity.CommentService;
import com.pcotten.rr17.storage.service.DatabaseConfig;
import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.model.Comment;

@Component
public class CommentServiceImpl implements CommentService {

	DatabaseConfig config = new DatabaseConfig();
	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public CommentServiceImpl(){
		
	}
	
	
	public int insertNewComment(Comment comment, Integer userId) throws SQLException{

		int r = 0;
		
		conn = manager.getConnection();
		comment = insertCommentEntity(comment);
		if (comment.getId() != null){
			r = 1;
		}
		if (r != 0){
			System.out.println("Comment entity successfully inserted into database");
		}
		else {
			System.out.println("Unable to complete comment insert - failed to insert comment entity");
			throw new SQLException();
		}

		return r;
	}


	private Comment insertCommentEntity(Comment comment) throws SQLException {
		conn = manager.getConnection();
		pstmt = conn.prepareStatement("INSERT INTO comment (text, timestamp, userId, parentId, recipeId)"
				+ "VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, comment.getText());
		pstmt.setObject(2, comment.getTimestamp());
		pstmt.setInt(3, comment.getUserId());
		if (comment.getParentId() != null){
			pstmt.setInt(4, comment.getParentId());
		}
		else {
			pstmt.setNull(4, java.sql.Types.INTEGER);
		}
		pstmt.setInt(5, comment.getRecipeId());
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			comment.setId(id);
		}
		if (comment.getId() != null){
			System.out.println("Comment entity successfully inserted into database");
		}
		else {
			System.out.println("Comment creation failed - unable to insert comment entity");
			throw new SQLException();
		}
		
		return comment;
	}


	public int updateComment(Comment comment) throws SQLException {
		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("UPDATE comment SET text= ?, timestamp = ?, userId = ?, "
				+ "parentId = ?, recipeId = ? WHERE id = ?");
		pstmt.setString(1, comment.getText());
		pstmt.setObject(2, comment.getTimestamp());
		pstmt.setInt(3, comment.getUserId());
		if (comment.getParentId() != null){
			pstmt.setInt(4, comment.getParentId());
		}
		else {
			pstmt.setNull(4, java.sql.Types.INTEGER);
		}
		pstmt.setInt(5, comment.getRecipeId());
		pstmt.setInt(6, comment.getId());
		
		r = pstmt.executeUpdate();
		if (r != 0 && comment.getId() != null){
			System.out.println("Comment entity successfully updated in database");
		}
		else {
			System.out.println("Comment update failed");
			throw new SQLException();
		}
		
		return r;
	}


	public int deleteComment(Integer id) throws SQLException {
		
		int result = -1;

		result = DbCommonFunctions.deleteEntity("comment", id);
		if (result != -1){
			System.out.println("Successfully removed comment with id " + id);
		}
		else {
			System.out.println("Unable to remove image comment with id " + id);
		}
		
		return result;
	}


	public Comment getCommentById(Integer id) {
		
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", id.toString());
		
		return (Comment) manager.retrieveSingleEntity(constraints, Comment.class);
	}
}
