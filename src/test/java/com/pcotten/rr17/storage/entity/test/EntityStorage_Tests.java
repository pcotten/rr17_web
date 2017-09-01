package com.pcotten.rr17.storage.entity.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CategoryService_Test.class, CommentService_Test.class, CookbookService_Test.class,
		ImageService_Test.class, IngredientService_Test.class, InstructionService_Test.class,
		MealPlanService_Test.class, MealService_Test.class, PantryService_Test.class, RecipeService_Test.class,
		UserService_Test.class })
public class EntityStorage_Tests {

}
