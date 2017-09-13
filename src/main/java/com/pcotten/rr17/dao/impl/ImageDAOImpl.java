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
import com.pcotten.rr17.dao.ImageDAO;
import com.pcotten.rr17.model.Image;

@Component
public class ImageDAOImpl extends JdbcDaoSupport implements ImageDAO{
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class ImageRowMapper implements RowMapper<Image> {

		@Override
		public Image mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Image image = new Image();
			image.setId(rs.getInt("id"));
			image.setImagePath(rs.getString("imagePath"));
			image.setEntityId(rs.getInt("entityId"));
			image.setText(rs.getString("text"));
			
			return image;
		}
	}

	@Override
	public Image getImage(Integer id) {
		Image image = (Image) getJdbcTemplate().query(
				"SELECT * FROM image WHERE id = ?", 
				new Object[] {id}, 
				new ImageRowMapper());
		
		return image;
	}

	@Override
	public Image createImage(Image image) {
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO image (imagePath, entityId, text, imageType) VALUES (?, ?, ?, ?)", 
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, image.getImagePath());
				ps.setInt(2, image.getEntityId());
				ps.setString(3, image.getText());
				ps.setString(4, image.getImageType());
				return ps;
			}
			
		}, holder);
		if (holder.getKey() != null) {
			image.setId(holder.getKey().intValue());
		}
		
		return image;
	}

	@Override
	public Integer updateImage(Image image) {
		Integer result = getJdbcTemplate().update(
				"UPDATE image SET imagePath = ?, entityId = ?, text = ?, imageType = ? WHERE id = ?", 
				new Object[] {
						image.getImagePath(),
						image.getEntityId(),
						image.getText(),
						image.getImageType(),
						image.getId()
				});
		
		return result;
	}

	@Override
	public Integer deleteImage(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM image WHERE id = ?", 
				new Object[] {id});
		
		return result;
	}

	@Override
	public List<Image> getImages(Integer entityId, String imageType) {
		List<Image> images = (List<Image>) getJdbcTemplate().query(
				"SELECT * FROM image WHERE entityId = ? AND imageType = ?)", 
				new Object[] {
						entityId,
						imageType
				}, 
				new ImageRowMapper());
		
		return images;
	}
	
}


