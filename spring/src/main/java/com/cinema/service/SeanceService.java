package com.cinema.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cinema.dto.SeanceDto;
import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;
import com.cinema.exception.SeanceBadTimeException;
import com.cinema.exception.SeanceTimeIsAlreadyTakenException;
import com.cinema.repository.SeanceRepository;

import lombok.NonNull;

@Service
public class SeanceService {
	
	private static final String MIN_TIME = "09:00";
	
	private static final String MAX_TIME = "22:00";
	
	private static final int MINUTES_AMOUNT_FOR_CLEANING = 20;
	
	@Autowired
	SeanceRepository repository;
	
	public Page<Seance> findAll(int page, int size) {
		return repository.findAll(PageRequest.of(page, size));
	}
	
	public Seance addNewSeance(@NonNull SeanceDto seanceDto,@NonNull List<Ticket> tickets, @NonNull Movie movie) 
			throws SeanceBadTimeException, SeanceTimeIsAlreadyTakenException {	
		// TODO: Optimize 
		return Optional.of(seanceDto)
				.map(this::toSeance)
				.filter(this::isTimeInBoundsOfMinAndMaxTime)
				.map(Optional::of)
				.orElseThrow(() -> new SeanceBadTimeException(
						MIN_TIME, 
						MAX_TIME, 
						LocalDateTime.parse(seanceDto.getBegin()).toLocalTime().toString()))
				.filter(this::isTimeNotTaken)
				.map(Optional::of)
				.orElseThrow(() -> new SeanceTimeIsAlreadyTakenException(seanceDto.getBegin()))
				.map(seance -> {seance.setMovie(movie); return seance;})
				.map(seance -> {
					tickets.stream().forEach(ticket -> ticket.setSeance(seance));
					seance.setTickets(tickets);
					return seance;
					})
				.map(repository::save)
				.orElseThrow(NoSuchElementException::new);
	}
	
	private boolean isTimeInBoundsOfMinAndMaxTime(@NonNull Seance seance) {
		return isTimeInBounds(seance, LocalTime.parse(MIN_TIME), LocalTime.parse(MAX_TIME));
	}
	
	private boolean isTimeInBoundsOfTimeOfAnotherSeance(@NonNull Seance seance, @NonNull Seance anotherSeance) {
		LocalDateTime startDateTimeOfAnotherSeance = anotherSeance.getStartDateTime();
		LocalTime afterTime = startDateTimeOfAnotherSeance.toLocalTime();
		LocalTime beforTime = startDateTimeOfAnotherSeance.toLocalTime()
				.plusMinutes(anotherSeance.getMovie().getDuration())
				.plusMinutes(MINUTES_AMOUNT_FOR_CLEANING);
		
		return isTimeInBounds(seance, afterTime, beforTime);
	}
	
	private boolean isTimeInBounds(@NonNull Seance seance, @NonNull LocalTime afterTime, @NonNull LocalTime beforeTime) {
		LocalTime startTime = seance.getStartDateTime().toLocalTime(); 
		return startTime.isAfter(afterTime.minusMinutes(1)) && 
				startTime.isBefore(beforeTime.plusMinutes(1));
	}	
	
	private boolean isTimeNotTaken(@NonNull Seance seance) {
		LocalDateTime afterDateTime = seance.getStartDateTime().with(LocalTime.parse(MIN_TIME));
		LocalDateTime beforeDateTime = seance.getStartDateTime().with(LocalTime.parse(MAX_TIME));
		
		return repository.findAllByStartDateTimeBetween(afterDateTime, beforeDateTime).stream()
				.noneMatch(someSeanceAtSameDate -> isTimeInBoundsOfTimeOfAnotherSeance(seance, someSeanceAtSameDate));
	}

	private Seance toSeance(@NonNull SeanceDto seanceDto) {
		return Seance.builder()
				.startDateTime(LocalDateTime.parse(seanceDto.getBegin()))
				.build();
	}
	
}
