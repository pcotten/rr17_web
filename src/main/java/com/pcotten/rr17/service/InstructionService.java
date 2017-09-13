package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Instruction;

public interface InstructionService {

	public List<Instruction> getInstructions(Integer recipeId);
	
	public Instruction createInstruction(Instruction instruction) throws SQLException;
	
	public Integer updateInstruction(Instruction instruction) throws SQLException;
	
	public Integer deleteInstruction(Integer id) throws SQLException;

}
