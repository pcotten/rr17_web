package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Comment;

public interface CommentDAO {

	public Comment getComment(Integer id);
	
	public Comment createComment(Comment comment);
	
	public Integer updateComment(Comment comment);
	
	public Integer deleteComment(Integer id);
	
	public List<Comment> getComments(Integer recipeId);
	
	public List<Comment> getChildComments(Integer commentId);
	
}
