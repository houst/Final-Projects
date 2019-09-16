package com.cinema.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.MovieDao;
import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.entity.User;

public class JdbcMovieDao implements MovieDao {
	
	private Connection connection;

	JdbcMovieDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Movie findBySeance(Seance seance) throws SQLException {
		String sql = "SELECT m.* FROM movie m "
				+ "INNER JOIN seance s ON s.movie_id = m.movie_id "
				+ "WHERE s.id";
		Movie movie = null;
		
		try ( PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			pstmt.setLong(1, seance.getId());
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	movie = extractFromResultSet(resultSet);
                }
            }
            
            if (movie == null) {
    			throw new SQLException("There is no movie with this seance id: " + seance.getId());
    		}
            
            movie.setSeances(new ArrayList<Seance>());
            
        }
		
		return movie;
	}
	
	@Override
	public List<Movie> findByUser(User user) throws SQLException {
		String sql = "SELECT m.* FROM movie m "
				+ "INNER JOIN user_movie m2m ON m2m.user_id = ? AND m2m.movie_id = m.id";
		
		List<Movie> watchedMovies = new ArrayList<>();
		
		try (PreparedStatement psmt = connection.prepareStatement(sql);) {
			
			psmt.setLong(1, user.getId());
            try (ResultSet resultSet = psmt.executeQuery();) {
                if (resultSet.next()) {
                	Movie movie = extractFromResultSet(resultSet);
                	movie.setSeances(new ArrayList<>());
                }
            }
            
		}
           
		
		return watchedMovies;
	}

	private Movie extractFromResultSet(ResultSet rs) throws SQLException {
        return Movie.builder()
        		.id(rs.getLong("id"))
        		.title(rs.getString("title"))
        		.duration(rs.getInt("duration"))
        		.description(rs.getString("description"))
        		.build();
    }
	
	public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
}