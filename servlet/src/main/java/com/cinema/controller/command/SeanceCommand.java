package com.cinema.controller.command;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cinema.dto.SeanceDto;
import com.cinema.entity.Seance;
import com.cinema.exception.IdNotFoundException;
import com.cinema.exception.TitleNotFoundException;
import com.cinema.service.MovieService;
import com.cinema.service.SeanceService;
import com.cinema.service.TicketService;

public class SeanceCommand implements Command {
	
	private SeanceService service = new SeanceService();
	private MovieService movieService = new MovieService();
	private TicketService ticketService = new TicketService();
	
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;
	
	@Override
	public String execute(HttpServletRequest request) {
		String method = request.getMethod();
		
		if ("GET".equals(method)) {
			return doGet(request);
		}
		
		if ("POST".equals(method)) {
			return doPost(request);
		}
		
		if ("PUT".equals(method)) {
			return doPut(request);
		}
		
		if ("DELETE".equals(method)) {
			return doDelete(request);
		}
		
		return CommandUtility.error(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	private String doGet(HttpServletRequest request) {
		
		String path = request.getRequestURI();
		String regexSeanceList = ".*/seance/?$";
		String regexSeanceEdit = ".*/[0-9]+/edit/?$";
		String regexSeanceAdd = ".*/seance/add/?$";
		String regexSeancePage = ".*/seance/[0-9]+/?$";
		
		if (path.matches(regexSeanceEdit)) {
			return doGetSeanceEditPage(request);
		}
		
		if (path.matches(regexSeanceList)) {
			return doGetSeanceListPage(request);
		}
		
		if (path.matches(regexSeanceAdd)) {
			return doGetSeanceAddPage(request);
		}
		
		if (path.matches(regexSeancePage)) {
			return doGetSeancePage(request);
		}
		
		return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
	}
	
	private String doGetSeancePage(HttpServletRequest request) {
		int seanceIdFromPath = Integer.parseInt(request.getRequestURI().split("/")[3]);
		Seance seance = null;
		try {
			seance = service.findById(seanceIdFromPath);
		} catch (IdNotFoundException e) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		
		request.setAttribute("seanceAtt", seance);
		return "/WEB-INF/seance.jsp";
	}

	private String doGetSeanceEditPage(HttpServletRequest request) {
		int seanceIdFromPath = Integer.parseInt(request.getRequestURI().split("/")[3]);
		Seance seance = null;
		try {
			seance = service.findById(seanceIdFromPath);
		} catch (IdNotFoundException e) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		
		request.setAttribute("seance", seance);
		
		return "/WEB-INF/seance-edit.jsp";
	}
	
	private String doGetSeanceListPage(HttpServletRequest request) {
		Integer page = null;
		Integer size = null;

		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			page = DEFAULT_PAGE;
		}
		
		try {
			size = Integer.parseInt(request.getParameter("size"));
		} catch (NumberFormatException e) {
			size = DEFAULT_SIZE;
		}
		
		long elementsCount = service.findCount();
		request.setAttribute("elements", service.findAll(page, size));
		request.setAttribute("elementsCount", elementsCount);
		request.setAttribute("page", page);
		request.setAttribute("size", size);
		request.setAttribute("pagesCount", (int) Math.ceil(elementsCount * 1.0 / size));
		
		return "/WEB-INF/seance-list.jsp";
	}
	
	private String doGetSeanceAddPage(HttpServletRequest request) {
		request.setAttribute("movies", movieService.findByTitleStartsWith(""));
		return "/WEB-INF/seance-add.jsp";
	}
	
	private String doPost(HttpServletRequest request) {
		if (!CommandUtility.checkUserIsGranted(request)) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		SeanceDto seanceDto = SeanceDto.builder()
				.begin(request.getParameter("begin"))
				.movieTitle(request.getParameter("movieTitle"))
				.build();
		
		if (seanceDto.getBegin() == null || seanceDto.getBegin().trim() == "" ||
				seanceDto.getMovieTitle() == null || seanceDto.getMovieTitle().trim() == "") {
			
			return CommandUtility.error(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		Seance newSeance;
		try {
			newSeance = convertToEntity(seanceDto);
		} catch (TitleNotFoundException e1) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		
		newSeance.setTickets(ticketService.generateAvailableTickets());
		
		
		service.create(newSeance);
		
		
		return CommandUtility.json("{ \"created\" : true}");
	}
	
	private String doPut(HttpServletRequest request) {
//		if (!CommandUtility.checkUserIsGranted(request)) {
//			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
//		}
//		Map<String, String> data = CommandUtility.getParameterMap(request);
//		Movie movie = Movie.builder()
//				.id(Long.parseLong(CommandUtility.decodeValue(data.get("movieId"))))
//				.title(CommandUtility.decodeValue(data.get("title")))
//				.duration(Integer.parseInt(CommandUtility.decodeValue(data.get("duration"))))
//				.description(CommandUtility.decodeValue(data.get("description")))
//				.build();
//		
//		try {
//			service.update(movie);
//		} catch (IdNotFoundException e) {
//			CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
//		}
		return CommandUtility.json("{ \"updated\" : true}");
	}
	
	private String doDelete(HttpServletRequest request) {
		return CommandUtility.error(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	private Seance convertToEntity(SeanceDto seanceDto) throws TitleNotFoundException {
		return Seance.builder()
				.startDateTime(LocalDateTime.parse(seanceDto.getBegin()))
				.movie(movieService.findByTitle(seanceDto.getMovieTitle()))
				.build();
	}
	
}
