package com.cinema.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LocaleSessionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String uri = req.getRequestURI();
		String defaultLocale = (String) req.getServletContext().getAttribute("defaultLocale");
		
		if ("/".equals(uri)) {
			((HttpServletResponse) response).sendRedirect("/" + defaultLocale);
			return;
		}
		
        String localePrefixes = (String) req.getServletContext().getAttribute("localePrefixes"); 
        String locale = uri.split("/")[1];
        
        if (localePrefixes.contains(locale)) {
        	if (session.getAttribute("locale") == null) {
        		session.setAttribute("locale", defaultLocale);
        	} else {
				session.setAttribute("locale", locale);
			}
        }
        
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// do nothing
	}
	
}
