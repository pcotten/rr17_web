package com.pcotten.rr17.service;

import java.sql.SQLException;

import com.pcotten.rr17.model.Image;

public interface ImageService {

	public Image insertNewImage(Image image) throws SQLException;
	
	public int updateImage(Image image) throws SQLException;
	
	public int deleteImage(Integer id) throws SQLException;

	public Image getImageById(Integer id);
	
}
