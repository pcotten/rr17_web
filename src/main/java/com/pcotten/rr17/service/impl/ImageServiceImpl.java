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


	public boolean updateImage(Image image) throws SQLException {

		boolean success = false;
		int result = imageDAO.updateImage(image);
		
		if (result != 0){
			System.out.println("Image at " + image.getImagePath() + " successfully updated in database");
			success = true;
		}
		else {
			System.out.println("Image not updated");
		}
			
		return success;
	}


	public boolean deleteImage(Integer id) throws SQLException {
		
		boolean success = false;
		int result = -1;

		result = imageDAO.deleteImage(id);
		
		if (result != -1){
			System.out.println("Successfully removed image with id " + id);
			success = true;
		}
		else {
			System.out.println("Failed to remove image entity with id " + id);
		}
		
		return success;
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


	@Override
	public boolean addImageToRecipe(Integer imageId, Integer recipeId) {
		
		boolean success = false;
		int result = -1;

		result = imageDAO.addImageToRecipe(imageId, recipeId);
		
		if (result != -1){
			System.out.println("Successfully added image to recipe");
			success = true;
		}
		else {
			System.out.println("Failed to add image to recipe");
		}
		
		return success;
	}


	@Override
	public boolean addImageToIngredient(Integer imageId, Integer ingredientId) {
		boolean success = false;
		int result = -1;

		result = imageDAO.addImageToIngredient(imageId, ingredientId);
		
		if (result != -1){
			System.out.println("Successfully added image to ingredient");
			success = true;
		}
		else {
			System.out.println("Failed to remove image from ingredient");
		}
		
		return success;
	}


	@Override
	public boolean addImageToInstruction(Integer imageId, Integer instructionId) {
		boolean success = false;
		int result = -1;

		result = imageDAO.addImageToInstruction(imageId, instructionId);
		
		if (result != -1){
			System.out.println("Successfully added image to instruction");
			success = true;
		}
		else {
			System.out.println("Failed to remove image from instruction");
		}
		
		return success;
	}


	@Override
	public boolean addImageToCookbook(Integer imageId, Integer cookbookId) {
		boolean success = false;
		int result = -1;

		result = imageDAO.addImageToCookbook(imageId, cookbookId);
		
		if (result != -1){
			System.out.println("Successfully added image to cookbook");
			success = true;
		}
		else {
			System.out.println("Failed to remove image from cookbook");
		}
		
		return success;
	}


	@Override
	public boolean addImageToComment(Integer imageId, Integer commentId) {
		boolean success = false;
		int result = -1;

		result = imageDAO.addImageToComment(imageId, commentId);
		
		if (result != -1){
			System.out.println("Successfully added image to comment");
			success = true;
		}
		else {
			System.out.println("Failed to remove image from comment");
		}
		
		return success;
	}


	@Override
	public boolean removeImageFromRecipe(Integer imageId, Integer recipeId) {
		boolean success = false;
		int result = -1;

		result = imageDAO.removeImageFromRecipe(imageId, recipeId);
		
		if (result != -1){
			System.out.println("Successfully removed image from recipe");
			success = true;
		}
		else {
			System.out.println("Failed to remove image from recipe");
		}
		
		return success;
	}


	@Override
	public boolean removeImageFromIngredient(Integer imageId, Integer ingredientId) {
		boolean success = false;
		int result = -1;

		result = imageDAO.removeImageFromIngredient(imageId, ingredientId);
		
		if (result != -1){
			System.out.println("Successfully added image to ingredient");
			success = true;
		}
		else {
			System.out.println("Failed to remove image from ingredient");
		}
		
		return success;
	}


	@Override
	public boolean removeImageFromInstruction(Integer imageId, Integer instructionId) {
		boolean success = false;
		int result = -1;

		result = imageDAO.removeImageFromInstruction(imageId, instructionId);
		
		if (result != -1){
			System.out.println("Successfully added image to instruction");
			success = true;
		}
		else {
			System.out.println("Failed to remove image from instruction");
		}
		
		return success;
	}


	@Override
	public boolean removeImageFromCookbook(Integer imageId, Integer cookbookId) {
		boolean success = false;
		int result = -1;

		result = imageDAO.removeImageFromCookbook(imageId, cookbookId);
		
		if (result != -1){
			System.out.println("Successfully added image to cookbook");
			success = true;
		}
		else {
			System.out.println("Failed to remove image from cookbook");
		}
		
		return success;
	}


	@Override
	public boolean removeImageFromComment(Integer imageId, Integer commentId) {
		boolean success = false;
		int result = -1;

		result = imageDAO.removeImageFromComment(imageId, commentId);
		
		if (result != -1){
			System.out.println("Successfully added image to comment");
			success = true;
		}
		else {
			System.out.println("Failed to remove image from comment");
		}
		
		return success;
	}
	
}
