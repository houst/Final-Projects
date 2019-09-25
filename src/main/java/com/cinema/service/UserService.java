package com.cinema.service;

import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.DaoFactory;
import com.cinema.dao.UserDao;
import com.cinema.entity.User;
import com.cinema.exception.EmailExistsException;
import com.cinema.exception.EmailNotFoundException;
import com.cinema.exception.IdNotFoundException;
import com.cinema.exception.RuntimeSQLException;

public class UserService {
	
	private DaoFactory daoFactory = DaoFactory.getInstance();
	
	public User findByEmail(String email) throws EmailNotFoundException {
		try (UserDao repo = daoFactory.createUserDao()) {
			return repo.findByEmail(email);
		} catch (RuntimeException e) {
			throw new EmailNotFoundException("There is no user with this email: " + email);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User create(User newUser) throws EmailExistsException {
		try (UserDao repo = daoFactory.createUserDao()) {
			return repo.create(newUser);
		} catch (RuntimeException e) {
			throw new EmailExistsException("There is already exists user with this email: " + newUser.getEmail());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<User> findAll(int page, int size) {
		try (UserDao repo = daoFactory.createUserDao()) {
			return repo.findAllUsers(page, size); 
		} catch (Exception e) {
			return new ArrayList<>();
		} 
	}

	public long findCount() {
		try (UserDao repo = daoFactory.createUserDao()) {
			return repo.findCount();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User findById(int id) throws IdNotFoundException {
		try (UserDao repo = daoFactory.createUserDao()) {
			return repo.findById(id);
		} catch (RuntimeSQLException e) {
			throw new IdNotFoundException("There is no user with this id: " + id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User update(User user) throws IdNotFoundException {
		try (UserDao repo = daoFactory.createUserDao()) {
			return repo.update(user);
		} catch (RuntimeSQLException e) {
			throw new IdNotFoundException("Thre is no user for updating with this id: " + user.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
