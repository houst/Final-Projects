package com.cinema.config;

import java.lang.reflect.Array;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cinema.interceptor.UrlLocaleInterceptor;
import com.cinema.resolver.UrlLocaleResolver;
 
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Value("${locale.prefixes}/")
    private String localePrefixes;
 
    @Bean(name = "messageSource")
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:i18n/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }
 
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
        LocaleResolver resolver = new UrlLocaleResolver();
        return resolver;
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
 
    	HandlerInterceptorAdapter localeInterceptor = new UrlLocaleInterceptor();
        
    	String[] prefixes = concatenate(
    			Stream.of(localePrefixes.split(","))
	        		.map(el -> '/' + el)
	        		.toArray(size -> new String[size]),
        		concatenate(
        		Stream.of(localePrefixes.split(","))
	        		.map(el -> '/' + el + "/")
	        		.toArray(size -> new String[size]),
	        	Stream.of(localePrefixes.split(","))
	        		.map(el -> '/' + el + "/")
	        		.toArray(size -> new String[size])));
        		
    	
    	
    	registry.addInterceptor(localeInterceptor).addPathPatterns(prefixes);
    	
    	
    }
    
    private <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }
 
}