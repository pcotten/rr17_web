package com.pcotten.rr17.rest.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pcotten.rr17.model.Cookbook;

@Controller
public interface CookbookRestService {
	
	@RequestMapping(value="/cookbooks/{cookbookId}", method=RequestMethod.PUT)
	public Cookbook updateCookbook(
			@PathVariable ("cookbookId") Integer cookbookId,
			@RequestBody String payload);
	
	@RequestMapping(value="/cookbooks/{cookbookId}", method=RequestMethod.DELETE)
	public boolean deleteCookbook(
			@PathVariable ("cookbookId") Integer cookbookId);
	
	@RequestMapping(value="/cookbooks/{cookbookId}/recipes", method=RequestMethod.GET)
	public Cookbook getCookbookRecipes(
			@PathVariable ("cookbookId") Integer cookbookId);
	
	@RequestMapping(value="/cookbooks/{cookbookId}/recipes", method=RequestMethod.POST)
	public boolean createCookbookRecipe(
			@PathVariable ("cookbookId") Integer cookbookId,
			@RequestBody String payload);
	
	@RequestMapping(value="/cookbooks/{cookbookId}/recipes/{recipeId}", method=RequestMethod.DELETE)
	public Cookbook deleteCookbookRecipe(
			@PathVariable ("cookbookId") Integer cookbookId,
			@PathVariable ("recipeId") Integer recipeId);
}
