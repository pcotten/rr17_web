package com.pcotten.rr17.model;

public class Image {
	
	private Integer id;
	private String imagePath;
	private Integer entityId;
	private String text;
	private String imageType;
	
	public static interface Type {
		public static String USER_PROFILE_INACTIVE = "user_inactive";
		public static String USER_PROFILE_ACTIVE = "user_active";
		public static String RECIPE = "recipe";
		public static String COOKBOOK = "cookbook";
		public static String INGREDIENT = "ingredient";
		public static String INSTRUCTION = "instruction";
		public static String COMMENT = "comment";
	}
	
	public Image(){
		
	}
	
	public Image(String imagePath, String imageText, Integer entityId, String imageType) {
		super();
		this.imagePath = imagePath;
		this.text = imageText;
		this.entityId = entityId;
		this.imageType = imageType;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer imageId) {
		this.id = imageId;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public Integer getEntityId() {
		return entityId;
		
	}
	
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String imageText) {
		this.text = imageText;
	}
	
	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	
}
