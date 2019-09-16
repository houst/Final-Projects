package com.cinema.config;

public interface AppConfig {
	
	/* Locale */
	String DEFAULT_LOCALE = "en";
	String LOCALE_PREFIXES = "en,ua";
	
	/* DB */
	String DB_URL = "jdbc:mysql://localhost:3306/cinema?useTimezone=true&serverTimezone=UTC";
	String DB_USERNAME = "root";
	String DB_PASSWORD = "11111111";
	
	/* DB Connection Pooling */
	Integer DBCP_MIN_IDLE = 5;
	Integer DBCP_MAX_IDLE = 10;
	Integer DBCP_MAX_OPEN_PREPARED_STATEMETS = 100;

}
