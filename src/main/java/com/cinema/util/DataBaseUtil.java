package com.cinema.util;

import org.apache.commons.dbcp2.BasicDataSource;

import com.cinema.config.AppConfig;

public class DataBaseUtil {
	
	private static BasicDataSource dataSource;
	
	private DataBaseUtil() {}
	 
    public static BasicDataSource getDataSource() {
 
        if (dataSource == null) {
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl(AppConfig.DB_URL);
            ds.setUsername(AppConfig.DB_USERNAME);
            ds.setPassword(AppConfig.DB_PASSWORD);
 
            ds.setMinIdle(AppConfig.DBCP_MIN_IDLE);
            ds.setMaxIdle(AppConfig.DBCP_MAX_IDLE);
            ds.setMaxOpenPreparedStatements(AppConfig.DBCP_MAX_OPEN_PREPARED_STATEMETS);
 
            dataSource = ds;
        }
        return dataSource;
    }
    
}
