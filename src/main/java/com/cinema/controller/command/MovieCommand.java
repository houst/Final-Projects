package com.cinema.controller.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cinema.dto.MovieDto;
import com.cinema.entity.Movie;
import com.cinema.exception.IdNotFoundException;
import com.cinema.exception.TitleExistsException;
import com.cinema.service.MovieService;

public class MovieCommand implements Command {
	
	private MovieService service = new MovieService();
	
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
		if (!CommandUtility.checkUserIsGranted(request)) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		
		String path = request.getRequestURI();
		String regexMovieList = ".*/movie/?$";
		String regexMovieEdit = ".*/[0-9]+/edit/?$";
		String regexMovieAdd = ".*/movie/add/?$";
		
		if (path.matches(regexMovieEdit)) {
			return doGetMovieEditPage(request);
		}
		
		if (path.matches(regexMovieList)) {
			return doGetMovieListPage(request);
		}
		
		if (path.matches(regexMovieAdd)) {
			return doGetMovieAddPage(request);
		}
		
		return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
	}
	
	private String doGetMovieEditPage(HttpServletRequest request) {
		int movieIdFromPath = Integer.parseInt(request.getRequestURI().split("/")[3]);
		Movie movie = null;
		try {
			movie = service.findById(movieIdFromPath);
		} catch (IdNotFoundException e) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		
		request.setAttribute("movie", movie);
		
		return "/WEB-INF/movie-edit.jsp";
	}
	
	private String doGetMovieListPage(HttpServletRequest request) {
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
		
		return "/WEB-INF/movie-list.jsp";
	}
	
	private String doGetMovieAddPage(HttpServletRequest request) {
		return "/WEB-INF/movie-add.jsp";
	}
	
	private String doPost(HttpServletRequest request) {
		if (!CommandUtility.checkUserIsGranted(request)) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		MovieDto movieDto = MovieDto.builder()
				.title(request.getParameter("title"))
				.duration(Integer.parseInt(request.getParameter("duration")))
				.description(request.getParameter("description"))
				.build();
		
		if (movieDto.getTitle() == null || movieDto.getTitle().trim() == "" ||
				movieDto.getDuration() <= 0 ||
				movieDto.getDescription() == null || movieDto.getDescription().trim() == "") {
			
			return CommandUtility.error(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		try {
			service.create(convertToEntity(movieDto));
		} catch (TitleExistsException e) {
			return CommandUtility.error(HttpServletResponse.SC_CONFLICT);
		}
		
		return CommandUtility.json("{ \"created\" : true}");
	}
	
	private String doPut(HttpServletRequest request) {
		if (!CommandUtility.checkUserIsGranted(request)) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		Map<String, String> data = CommandUtility.getParameterMap(request);
		Movie movie = Movie.builder()
				.id(Long.parseLong(CommandUtility.decodeValue(data.get("movieId"))))
				.title(CommandUtility.decodeValue(data.get("title")))
				.duration(Integer.parseInt(CommandUtility.decodeValue(data.get("duration"))))
				.description(CommandUtility.decodeValue(data.get("description")))
				.build();
		
		try {
			service.update(movie);
		} catch (IdNotFoundException e) {
			CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		return CommandUtility.json("{ \"updated\" : true}");
	}
	
	private String doDelete(HttpServletRequest request) {
		return CommandUtility.error(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	private Movie convertToEntity(MovieDto movieDto) {
		return Movie.builder()
				.title(movieDto.getTitle())
				.duration(movieDto.getDuration())
				.description(movieDto.getDescription())
				.build();
	}
	
}
