package com.pcotten.rr17.service;

import java.sql.SQLException;

import com.pcotten.rr17.model.Instruction;

public interface InstructionService {

	public Instruction insertNewInstruction(Instruction instruction) throws SQLException;
	
	public int updateInstruction(Instruction instruction) throws SQLException;
	
	public int deleteInstruction(Integer id) throws SQLException;

	public Instruction getInstructionById(Integer id);
	
}
