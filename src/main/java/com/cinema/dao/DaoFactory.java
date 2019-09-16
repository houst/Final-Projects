package com.cinema.dao;

import com.cinema.dao.impl.JdbcDaoFactory;

public abstract class DaoFactory {
	
    private static DaoFactory daoFactory;
    
    public abstract UserDao createUserDao();
    public abstract RoleDao createRoleDao();
    public abstract SeanceDao createSeanceDao();
    public abstract TicketDao createTicketDao();
    public abstract MovieDao createMovieDao();

    public static DaoFactory getInstance(){
       if( daoFactory == null ){
           synchronized (DaoFactory.class){
               if(daoFactory==null){
                   DaoFactory temp = new JdbcDaoFactory();
                   daoFactory = temp;
               }
           }
       }   return daoFactory;

    }
}
