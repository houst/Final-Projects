package com.cinema.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.entity.Seance;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

	List<Seance> findAllByStartDateTimeBetween(LocalDateTime afterDateTime, LocalDateTime beforeDateTime);

}
