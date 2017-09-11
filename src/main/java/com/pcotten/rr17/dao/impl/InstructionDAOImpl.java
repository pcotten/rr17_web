package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pcotten.rr17.dao.InstructionDAO;
import com.pcotten.rr17.model.Instruction;
import com.pcotten.rr17.model.User;

public class InstructionDAOImpl extends JdbcDaoSupport implements InstructionDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class InstructionRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Instruction instruction = new Instruction();
			instruction.setId(rs.getInt("id"));
			instruction.setOrderIndex(rs.getInt("orderIndex"));
			instruction.setRecipeId(rs.getInt("recipeId"));
			instruction.setText(rs.getString("text"));
			
			return instruction;
		}
		
	}

	@Override
	public Instruction createInstruction(Instruction instruction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateInstruction(Instruction instruction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteInstruction(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Instruction> getRecipeInstructions(Integer recipeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


