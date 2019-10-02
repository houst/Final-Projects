package com.cinema.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cinema.config.AppConfig;

public class LocaleAppListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("defaultLocale", AppConfig.DEFAULT_LOCALE);
		sce.getServletContext().setAttribute("localePrefixes", AppConfig.LOCALE_PREFIXES);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing
	}
	
}
