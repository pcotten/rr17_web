package com.pcotten.rr17.service.impl;

import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.dao.ImageDAO;
import com.pcotten.rr17.model.Image;
import com.pcotten.rr17.service.ImageService;

@Component
public class ImageServiceImpl implements ImageService{

	@Inject
	DatabaseManager manager;
	@Inject
	ImageDAO imageDAO;
	
	
	public ImageServiceImpl(){
		
	}
	
	
	public Image getImage(Integer id) {
		
		Image image = imageDAO.getImage(id);
		
		return image;
	}
	
	
	public Image createImage(Image image) throws SQLException{

		image = imageDAO.createImage(image);
		
		if (image.getId() != null){
			System.out.println("Image at " + image.getImagePath() 
			+ " successfully inserted into database with id " + image.getId());
		}
		else {
			System.out.println("Failed to create image");
		}
		
		return image;
	}


	public Integer updateImage(Image image) throws SQLException {

		int result = imageDAO.updateImage(image);
		
		if (result != 0){
			System.out.println("Image at " + image.getImagePath() + " successfully updated in database");
		}
		else {
			System.out.println("Image not updated");
		}
			
		return result;
	}


	public Integer deleteImage(Integer id) throws SQLException {
		
		int result = -1;

		result = imageDAO.deleteImage(id);
		
		if (result != -1){
			System.out.println("Successfully removed image with id " + id);
		}
		else {
			System.out.println("Failed to remove image entity with id " + id);
		}
		
		return result;
	}


	@Override
	public List<Image> getRecipeImages(Integer recipeId) {

		List<Image> images = imageDAO.getImages(recipeId, Image.Type.RECIPE);
		
		return images;
	}
	
	@Override
	public List<Image> getActiveUserImages(Integer userId) {
		
		List<Image> images = imageDAO.getImages(userId, Image.Type.USER_PROFILE_ACTIVE);
		
		return images;
	}


	@Override
	public List<Image> getInactiveUserImages(Integer userId) {

		List<Image> images = imageDAO.getImages(userId, Image.Type.USER_PROFILE_INACTIVE);
		
		return images;
	}


	@Override
	public List<Image> getIngredientImages(Integer ingredientId) {
		
		List<Image> images = imageDAO.getImages(ingredientId, Image.Type.INGREDIENT);
		
		return images;
	}


	@Override
	public List<Image> getInstructionImages(Integer instructionId) {
		
		List<Image> images = imageDAO.getImages(instructionId, Image.Type.INSTRUCTION);
		
		return images;
	}


	@Override
	public List<Image> getCookbookImages(Integer cookbookId) {
		
		List<Image> images = imageDAO.getImages(cookbookId, Image.Type.COOKBOOK);
		
		return images;
	}


	@Override
	public List<Image> getCommentImages(Integer commentId) {
		
		List<Image> images = imageDAO.getImages(commentId, Image.Type.COMMENT);
		
		return images;
	}
	
}
