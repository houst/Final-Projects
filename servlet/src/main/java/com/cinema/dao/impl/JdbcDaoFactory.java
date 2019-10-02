package com.cinema.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.cinema.dao.*;
import com.cinema.exception.RuntimeSQLException;
import com.cinema.util.DBConnectionPoolHolder;

public class JdbcDaoFactory extends DaoFactory {

    @Override
	public UserDao createUserDao() {
		return new JdbcUserDao(getConnection());
	}

	@Override
	public RoleDao createRoleDao() {
		return new JdbcRoleDao(getConnection());
	}

	@Override
	public SeanceDao createSeanceDao() {
		return new JdbcSeanceDao(getConnection());
	}

	@Override
	public TicketDao createTicketDao() {
		return new JdbcTicketDao(getConnection());
	}

	@Override
	public MovieDao createMovieDao() {
		return new JdbcMovieDao(getConnection());
	}

	private Connection getConnection(){
    	try {
			return DBConnectionPoolHolder.getDataSource().getConnection();
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
    }

}
