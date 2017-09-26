package com.pcotten.rr17.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.mysql.cj.api.jdbc.Statement;
import com.pcotten.rr17.dao.InstructionDAO;
import com.pcotten.rr17.model.Instruction;

@Component
public class InstructionDAOImpl extends JdbcDaoSupport implements InstructionDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class InstructionRowMapper implements RowMapper<Instruction> {

		@Override
		public Instruction mapRow(ResultSet rs, int row) throws SQLException {
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
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO instruction (orderIndex, text, recipeId) VALUES (?, ?, ?)", 
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, instruction.getOrderIndex());
				ps.setString(2, instruction.getText());
				ps.setInt(3, instruction.getRecipeId());
				return ps;
			}
			
		}, holder);
		if (holder.getKey() != null) {
			instruction.setId(holder.getKey().intValue());
		}
		
		return instruction;
	}

	@Override
	public Integer updateInstruction(Instruction instruction) {
		Integer response = getJdbcTemplate().update(
				"UPDATE instruction SET orderIndex = ?, text = ? WHERE id = ?", 
				new Object[] {
						instruction.getOrderIndex(),
						instruction.getText(),
						instruction.getId()
				});
		
		return response;
	}

	@Override
	public Integer deleteInstruction(Integer id) {
		Integer response = getJdbcTemplate().update(
				"DELETE FROM instruction WHERE id = ?", 
				new Object[] {id});
		
		return response;
	}

	@Override
	public List<Instruction> getRecipeInstructions(Integer recipeId) {
		List<Instruction> instructions = getJdbcTemplate().query("SELECT * FROM instruction WHERE recipeId = ?", 
				new Object[] {recipeId}, 
				new InstructionRowMapper());
		
		return instructions;
	}
	
}


