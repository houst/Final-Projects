package com.cinema.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cinema.config.AppConfig;
import com.cinema.controller.command.*;
import com.cinema.entity.Movie;
import com.cinema.entity.Role;
import com.cinema.entity.Seance;
import com.cinema.entity.User;
import com.cinema.service.MovieService;
import com.cinema.service.SeanceService;
import com.cinema.service.TicketService;
import com.cinema.service.UserService;
import com.cinema.util.DBConnectionPoolHolder;


public class Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger log = LogManager.getLogger(Servlet.class);
	
	private final Map<String, Command> commands = new HashMap<>();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		commands.put("/error", new ErrorCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/login", new LoginCommand());
        commands.put("/movie", new MovieCommand());
        commands.put("/seance", new SeanceCommand());
        commands.put("/user", new UserCommand());
        commands.put("/", new IndexCommand());
        
		initDb();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI();
		log.trace("URI={}", path);
		
		req.setAttribute("path", path.length() > 3 ? path.substring(3) : "");
		
		path = path.length() > 3 && AppConfig.LOCALE_PREFIXES.contains(path.split("/")[1]) ? "/".concat(path.split("/")[2]) :
			path.length() == 3 && AppConfig.LOCALE_PREFIXES.contains(path.substring(1)) ? "/" : path;
		
        Command command = commands.getOrDefault(path, p -> "/error");
        
        log.trace("{} is processing...", command.getClass().getSimpleName());
        
        String result = command.execute(req);
        
        if (result.startsWith("error")) {
        	resp.sendError(Integer.parseInt(result.substring(6)));
        } else if (result.startsWith("json")) {
        	resp.getWriter().write(result.substring(5));
        } else if (result.startsWith("redirect")) {
        	resp.sendRedirect(result.substring(9));
        } else {
        	req.getRequestDispatcher(result).forward(req,resp);
        }
	}
	
	/**
	 * Init db with truncate
	 * @throws SQLException 
	 */
	private void initDb() {
		try {

			// Truncate tables
			String sql1 = "DELETE FROM ticket";
			String sql2 = "DELETE FROM user_role";
			String sql3 = "DELETE FROM user_movie";
			String sql4 = "DELETE FROM user";
			String sql5 = "DELETE FROM seance";
			String sql6 = "DELETE FROM movie";
			BasicDataSource dataSource = DBConnectionPoolHolder.getDataSource();
			dataSource.setMaxTotal(10);
			try ( Connection connection = dataSource.getConnection();
	                PreparedStatement pstmt1 = connection.prepareStatement(sql1);
					PreparedStatement pstmt2 = connection.prepareStatement(sql2);
					PreparedStatement pstmt3 = connection.prepareStatement(sql3);
					PreparedStatement pstmt4 = connection.prepareStatement(sql4);
					PreparedStatement pstmt5 = connection.prepareStatement(sql5);
					PreparedStatement pstmt6 = connection.prepareStatement(sql6);) {
				pstmt1.execute();
				pstmt2.execute();
				pstmt3.execute();
				pstmt4.execute();
				pstmt5.execute();
				pstmt6.execute();
	        }
			
			
			// Add Users
			UserService userService = new UserService();
			userService.create(User.builder()
        			.email("cinema2019@gmail.com")
        			.password("1111")
        			.authorities(Arrays.asList(Role.ADMIN, Role.USER))
        			.username("Oleh")
        			.tel("+380991122333")
        			.accountNonExpired(true)
        			.accountNonLocked(true)
        			.credentialsNonExpired(true)
        			.enabled(true)
        			.build());
			
			// Add seances, movies and tickets
			MovieService movieService = new MovieService();
			SeanceService seanceService = new SeanceService();
			TicketService ticketService = new TicketService();
			for (int i = 1; i <= 12; i++) {
				Movie movie = movieService.create(Movie.builder()
						.title("Transporter " + i)
						.duration(150)
						.description("Some long description about movie")
						.build());
				seanceService.create(Seance.builder()
						.movie(movie)
						.startDateTime(LocalDateTime.of(2019, 10, i, 12, 00))
						.tickets(ticketService.generateAvailableTickets())
						.build());
			}
			
		} catch (Exception e) {
			log.error("IN initDb() error has occurred", e);
		}
	}
	
}
