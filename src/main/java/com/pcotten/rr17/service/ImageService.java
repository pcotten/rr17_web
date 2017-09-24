package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Image;

public interface ImageService {

	public Image createImage(Image image) throws SQLException;
	
	public boolean updateImage(Image image) throws SQLException;
	
	public boolean deleteImage(Integer id) throws SQLException;

	public Image getImage(Integer id);

	public List<Image> getRecipeImages(Integer recipeId);
	
	public List<Image> getActiveUserImages(Integer userId);
	
	public List<Image> getInactiveUserImages(Integer userId);
	
	public List<Image> getIngredientImages(Integer ingredientId);
	
	public List<Image> getInstructionImages(Integer instructionId);
	
	public List<Image> getCookbookImages(Integer cookbookId);
	
	public List<Image> getCommentImages(Integer commentId);

	public boolean addImageToRecipe(Integer imageId, Integer recipeId);
	
	public boolean addImageToIngredient(Integer imageId, Integer ingredientId);
	
	public boolean addImageToInstruction(Integer imageId, Integer instructionId);
	
	public boolean addImageToCookbook(Integer imageId, Integer cookbookId);
	
	public boolean addImageToComment(Integer imageId, Integer commentId);
	
	public boolean removeImageFromRecipe(Integer imageId, Integer recipeId);
	
	public boolean removeImageFromIngredient(Integer imageId, Integer ingredientId);
	
	public boolean removeImageFromInstruction(Integer imageId, Integer instructionId);
	
	public boolean removeImageFromCookbook(Integer imageId, Integer cookbookId);
	
	public boolean removeImageFromComment(Integer imageId, Integer commentId);
}
