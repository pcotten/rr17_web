package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Image;

public interface ImageDAO {

	public Image getImage(Integer id);
	
	public Image createImage(Image image);
	
	public Integer updateImage(Image image);
	
	public Integer deleteImage(Integer id);
	
	public List<Image> getImages(Integer entityId, String imageType);
	
}
