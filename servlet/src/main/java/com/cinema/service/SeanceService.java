package com.cinema.service;

import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.DaoFactory;
import com.cinema.dao.SeanceDao;
import com.cinema.entity.Seance;
import com.cinema.exception.IdNotFoundException;
import com.cinema.exception.RuntimeSQLException;

public class SeanceService {
	
	private DaoFactory daoFactory = DaoFactory.getInstance();

	public Seance create(Seance newSeance) {
		try (SeanceDao repo = daoFactory.createSeanceDao()) {
			return repo.create(newSeance);
		} catch (RuntimeException e) {
			return new Seance();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Seance> findAll(int page, int size) {
		try (SeanceDao repo = daoFactory.createSeanceDao()) {
			return repo.findAll(page, size); 
		} catch (RuntimeException e) {
			return new ArrayList<>();
		} catch (Exception e) {
			return null;
		}
	}

	public long findCount() {
		try (SeanceDao repo = daoFactory.createSeanceDao()) {
			return repo.findCount();
		} catch (RuntimeException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public Seance findById(long id) throws IdNotFoundException {
		try (SeanceDao repo = daoFactory.createSeanceDao()) {
			return repo.findById(id);
		} catch (RuntimeSQLException e) {
			throw new IdNotFoundException("There is no seance with this id: " + id);
		} catch (Exception e) {
			return null;
		}
	}

	public Seance update(Seance seance) throws IdNotFoundException {
		try (SeanceDao repo = daoFactory.createSeanceDao()) {
			return repo.update(seance);
		} catch (RuntimeSQLException e) {
			throw new IdNotFoundException("Thre is no seance for updating with this id: " + seance.getId());
		} catch (Exception e) {
			return null;
		}
	}
	
}
