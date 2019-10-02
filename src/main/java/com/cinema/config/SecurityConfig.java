package com.cinema.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cinema.service.UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userService;
	
	@Value("${locale.prefixes}")
    private String localePrefixes;
	
	@Autowired
	public PasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userService)
		.passwordEncoder(bcryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] matchers = {"/", "/ua", "/en", "/css/**", "/js/**", "/lib/**", "/img/**", "/user", "/en/seance/*", "/ua/seance/*"};
		
		http
		.authorizeRequests()
		.antMatchers(matchers).permitAll().anyRequest().authenticated()
//		.and()
//		.authorizeRequests()
//		.antMatchers(Stream.of(localePrefixes.split(","))
//				.map(el -> '/' + el + '/')
//				.toArray(size -> new String[size])).permitAll().anyRequest().authenticated()
		.and()
			.formLogin()
			.usernameParameter("email")
			.loginProcessingUrl("/login")
			.failureHandler(new SimpleUrlAuthenticationFailureHandler())
			.permitAll()
		.and()
        	.exceptionHandling()
        	.authenticationEntryPoint(getAuthenticationEntryPoint())
		.and()
			.logout()
			.logoutSuccessUrl("/?logout")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.permitAll();
	}
	
	private AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
				response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString());
			}
    	};
	}
	
}
