package com.pcotten.rr17.service.impl;

import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.dao.InstructionDAO;
import com.pcotten.rr17.model.Instruction;
import com.pcotten.rr17.service.InstructionService;

@Component
public class InstructionServiceImpl implements InstructionService{

	@Inject
	DatabaseManager manager = new DatabaseManager();
	@Inject
	InstructionDAO instructionDAO;
	
	public InstructionServiceImpl(){
		
	}
	
	@Override
	public List<Instruction> getInstructions(Integer recipeId) {
		
		List<Instruction> instructions = instructionDAO.getRecipeInstructions(recipeId);
		
		return instructions;
	}
	
	
	public Instruction createInstruction(Instruction instruction) throws SQLException{
		
	instruction = instructionDAO.createInstruction(instruction);
		
		if (instruction.getId() != null){
			System.out.println("Instruction successfully inserted into database");
		}
		else {
			System.out.println("Instruction not created");
		}
			
		return instruction;
	}


	public Integer updateInstruction(Instruction instruction) throws SQLException{
		
		int result = instructionDAO.updateInstruction(instruction);
			
		if (result > 0){
			System.out.println("Instruction successfully updated in database");
		}
		else {
			System.out.println("Instruction not updated");
		}
		return result;
	}
	
	
	public Integer deleteInstruction(Integer id) throws SQLException{
		
		int result = instructionDAO.deleteInstruction(id);
		
		if (result != -1){
			System.out.println("Successfully removed instruction with id " + id);
		}
		else {
			System.out.println("Unable to remove instruction entity with id " + id);
		}
		
		return result;
	}

}
