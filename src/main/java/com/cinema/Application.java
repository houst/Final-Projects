package com.cinema;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cinema.entity.Role;
import com.cinema.entity.User;
import com.google.common.collect.ImmutableList;

@SpringBootApplication
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@EventListener
    public void afterApplicationReady(ApplicationReadyEvent event) {

		try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
			
			Transaction transaction = session.beginTransaction();
			try {
				
				User admin = User.builder()
						.email("cinema2019@gmail.com")
						.password(new BCryptPasswordEncoder().encode("1111"))
						.authorities(ImmutableList.of(Role.ADMIN, Role.USER))
						.username("Oleh")
						.tel("+380991122333")
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.enabled(true)
						.build();
				
				User user = User.builder()
						.email("goodman@gmail.com")
						.password(new BCryptPasswordEncoder().encode("1111"))
						.authorities(ImmutableList.of(Role.USER))
						.username("Oleh")
						.tel("+380991122333")
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.enabled(true)
						.build();

				session.save(admin);
				session.save(user);

				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
			}
		}
	}
	
}