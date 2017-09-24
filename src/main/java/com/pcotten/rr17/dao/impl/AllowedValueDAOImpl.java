package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import com.pcotten.rr17.dao.AllowedValueDAO;
import com.pcotten.rr17.util.AllowedValue;

@Component
public class AllowedValueDAOImpl extends JdbcDaoSupport implements AllowedValueDAO{

	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class AVRowMapper implements RowMapper<AllowedValue> {

		@Override
		public AllowedValue mapRow(ResultSet rs, int row) throws SQLException {
			
			AllowedValue av = new AllowedValue();
			av.setDisplay(rs.getString("display"));
			av.setValue(rs.getString("value"));
			av.setQualifier(rs.getString("qualifier"));
			
			return av;
		}
	}
	
	@Override
	public List<AllowedValue> getAllowedValues(String avTable, String qualifier) {
		
		List<AllowedValue> entries = null;
		
		if (qualifier != null) {
			entries = (List<AllowedValue>) getJdbcTemplate().query(
					"SELECT * FROM " + avTable + " WHERE qualifier = ?", 
					new Object[] {
							qualifier
							}, 
					new AVRowMapper());
			}
		else {
			entries = (List<AllowedValue>) getJdbcTemplate().query(
					"SELECT * FROM " + avTable, 
					new Object[] {}, 
					new AVRowMapper());
		}
		
		return entries;
	}
}
