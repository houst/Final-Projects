package com.cinema.resolver;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.LocaleResolver;
 
public class UrlLocaleResolver implements LocaleResolver {
 
    private static final String URL_LOCALE_ATTRIBUTE_NAME = "URL_LOCALE_ATTRIBUTE_NAME";

    @Value("${locale.default}")
    private String localeDef;
    
    @Value("${locale.prefixes}")
    private String localePrefixes;
    
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String uri = request.getRequestURI();
        Locale locale = null;

        for (String localePrefix : localePrefixes.split(",")) {
        	String prefix = request.getServletContext().getContextPath() + '/' + localePrefix;
        	
        	if (uri.equals(prefix) || uri.startsWith(prefix + '/') || uri.startsWith(prefix + '?'))
        		locale = new Locale(localePrefix);
        }
        
        if (locale != null) {
            request.getSession().setAttribute(URL_LOCALE_ATTRIBUTE_NAME, locale);
        }
        
        if (locale == null) {
            locale = (Locale) request.getSession().getAttribute(URL_LOCALE_ATTRIBUTE_NAME);
            if (locale == null) {
                locale = new Locale(localeDef);
            }
        }
        return locale;
    }
 
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    	// default implementation ignored
    }
 
}
