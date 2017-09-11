package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Instruction;

public interface InstructionDAO {

	public Instruction createInstruction(Instruction instruction);
	
	public Integer updateInstruction(Instruction instruction);
	
	public Integer deleteInstruction(Integer id);
	
	public List<Instruction> getRecipeInstructions(Integer recipeId);
	
}
