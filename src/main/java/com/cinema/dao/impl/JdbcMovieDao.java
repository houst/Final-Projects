package com.cinema.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.MovieDao;
import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.entity.User;
import com.cinema.exception.RuntimeSQLException;

@SuppressWarnings("resource")
public class JdbcMovieDao implements MovieDao {
	
	private Connection connection;

	JdbcMovieDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Movie findBySeance(Seance seance) {
		String sql = "SELECT m.* FROM movie m "
				+ "INNER JOIN seance s ON s.movie_id = m.id "
				+ "WHERE s.id = ?";
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
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return movie;
	}
	
	@Override
	public List<Movie> findByUser(User user) {
		String sql = "SELECT m.* FROM movie m "
				+ "INNER JOIN user_movie m2m ON m2m.user_id = ? AND m2m.movie_id = m.id";
		
		List<Movie> watchedMovies = new ArrayList<>();
		
		try (PreparedStatement psmt = connection.prepareStatement(sql);) {
			
			psmt.setLong(1, user.getId());
            try (ResultSet resultSet = psmt.executeQuery();) {
                while (resultSet.next()) {
                	Movie movie = extractFromResultSet(resultSet);
                	movie.setSeances(new JdbcSeanceDao(connection).findByMovie(movie));
                }
            }
            
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
           
		
		return watchedMovies;
	}

	@Override
	public Movie findByTitle(String title) {
		String sql = "SELECT * FROM movie WHERE title = ?";
		Movie movie = null;
		
		try ( PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			pstmt.setString(1, title);
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	movie = extractFromResultSet(resultSet);
                }
            }
            
            if (movie == null) {
    			throw new SQLException("There is no movie with this title: " + title);
    		}
            
            movie.setSeances(new ArrayList<Seance>());
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return movie;
	}

	@Override
	public List<Movie> findByTitleStartsWith(String titleStartsWith) {
		String sql = "SELECT * FROM movie "
				+ "WHERE title LIKE CONCAT(?, '%')";
		
		List<Movie> movies = new ArrayList<>();
		
		try (PreparedStatement psmt = connection.prepareStatement(sql);) {
			
			psmt.setString(1, titleStartsWith);
            try (ResultSet resultSet = psmt.executeQuery();) {
                while (resultSet.next()) {
                	Movie movie = extractFromResultSet(resultSet);
                	movie.setSeances(new ArrayList<>());
                	movies.add(movie);
                }
            }
            
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
           
		
		return movies;
	}

	@Override
	public Movie create(Movie movie) {
		String sql = "INSERT INTO movie "
				+ "(title, duration, descr) "
				+ "values (?, ?, ?)";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql, 
                		Statement.RETURN_GENERATED_KEYS);) {
			
			pstmt.setString(1, movie.getTitle());
			pstmt.setInt(2, movie.getDuration());
			pstmt.setString(3, movie.getDescription());
			
			
            int affectedRowsMovies = pstmt.executeUpdate();
            
            if (affectedRowsMovies == 0) {
                throw new SQLException("Creating movie failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                	movie.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating movie failed, no ID obtained.");
                }
            }
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return movie;
		
	}

	@Override
	public List<Movie> findAll() {
		String sql = "SELECT * FROM movie"; 
		List<Movie> movies = new ArrayList<>();
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
            try (ResultSet resultSet = pstmt.executeQuery();) {
                while (resultSet.next()) {
                	Movie movie = extractFromResultSet(resultSet); 
                	movie.setSeances(new JdbcSeanceDao(connection).findByMovie(movie));
                	movies.add(movie);
                }
            }
            
        } catch (SQLException e) {
        	throw new RuntimeSQLException(e);
		}
		
		return movies;
	}

	@Override
	public List<Movie> findAll(int page, int size) {
		String sql = "SELECT * FROM movie LIMIT ?, ?"; 
		List<Movie> movies = new ArrayList<>();
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setInt(1, (page - 1) * size);
			pstmt.setInt(2, size);
            try (ResultSet resultSet = pstmt.executeQuery();) {
                while (resultSet.next()) {
                	Movie movie = extractFromResultSet(resultSet); 
                	movie.setSeances(new ArrayList<>());
                	movies.add(movie);
                }
            }
            
            
            
        } catch (SQLException e) {
        	throw new RuntimeSQLException(e);
		}
		
		return movies;
	}

	@Override
	public long findCount() {
		String sql = "SELECT COUNT(*) FROM movie";
		long count = 0;
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	count = resultSet.getLong(1);
                }
            }
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return count;
	}

	@Override
	public Movie findById(long id) {
		String sql = "SELECT * FROM movie WHERE id = ?";
		
		Movie movie = null;
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			pstmt.setLong(1, id);
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	movie = extractFromResultSet(resultSet);
                }
            }
            
            if (movie == null) {
    			throw new SQLException("There is no user with this id: " + id);
    		}
            
            movie.setSeances(new JdbcSeanceDao(connection).findByMovie(movie));
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return movie;
	}

	@Override
	public Movie update(Movie movie) {
		String sql = "UPDATE movie SET "
				+ "title = ?, duration = ?, descr = ? "
				+ "WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			pstmt.setString(1, movie.getTitle());
			pstmt.setInt(2, movie.getDuration());
			pstmt.setString(3, movie.getDescription());
			pstmt.setLong(4, movie.getId());
			pstmt.execute();
			
        } catch (SQLException e) {
        	throw new RuntimeSQLException(e);
		}
		
		return findById(movie.getId());
	}

	private Movie extractFromResultSet(ResultSet rs) throws SQLException {
        return Movie.builder()
        		.id(rs.getLong("id"))
        		.title(rs.getString("title"))
        		.duration(rs.getInt("duration"))
        		.description(rs.getString("descr"))
        		.build();
    }
	
	public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }
	
}