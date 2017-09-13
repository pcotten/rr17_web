package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Image;

public interface ImageService {

	public Image createImage(Image image) throws SQLException;
	
	public Integer updateImage(Image image) throws SQLException;
	
	public Integer deleteImage(Integer id) throws SQLException;

	public Image getImage(Integer id);

	public List<Image> getRecipeImages(Integer recipeId);
	
	public List<Image> getActiveUserImages(Integer userId);
	
	public List<Image> getInactiveUserImages(Integer userId);
	
	public List<Image> getIngredientImages(Integer ingredientId);
	
	public List<Image> getInstructionImages(Integer instructionId);
	
	public List<Image> getCookbookImages(Integer cookbookId);
	
	public List<Image> getCommentImages(Integer commentId);
}
