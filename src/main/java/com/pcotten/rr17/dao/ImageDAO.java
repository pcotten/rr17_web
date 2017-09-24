package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Image;

public interface ImageDAO {

	public Image getImage(Integer id);
	
	public Image createImage(Image image);
	
	public Integer updateImage(Image image);
	
	public Integer deleteImage(Integer id);
	
	public List<Image> getImages(Integer entityId, String imageType);

	public Integer addImageToRecipe(Integer imageId, Integer recipeId);

	public Integer addImageToIngredient(Integer imageId, Integer ingredientId);

	public Integer addImageToInstruction(Integer imageId, Integer instructionId);

	public Integer addImageToCookbook(Integer imageId, Integer cookbookId);

	public Integer addImageToComment(Integer imageId, Integer commentId);

	public Integer removeImageFromRecipe(Integer imageId, Integer recipeId);

	public Integer removeImageFromIngredient(Integer imageId, Integer ingredientId);

	public Integer removeImageFromInstruction(Integer imageId, Integer instructionId);

	public Integer removeImageFromCookbook(Integer imageId, Integer cookbookId);

	public Integer removeImageFromComment(Integer imageId, Integer commentId);
	
}
