package com.pcotten.rr17.service;

import java.sql.SQLException;
import com.pcotten.rr17.model.Comment;

public interface CommentService {

	public int insertNewComment(Comment comment, Integer userId) throws SQLException;
	
	public int updateComment(Comment comment) throws SQLException;
	
	public int deleteComment(Integer id) throws SQLException;

	public Comment getCommentById(Integer id);
	
}
