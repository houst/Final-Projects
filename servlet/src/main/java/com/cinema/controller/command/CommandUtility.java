package com.cinema.controller.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Splitter;

public class CommandUtility {
	
	private CommandUtility() {}
	
	static boolean checkUserIsLogged(HttpServletRequest request) {
        return request.getSession().getAttribute("loggedUser") != null;
    }
	
	static boolean checkUserIsGranted(HttpServletRequest request) {
        return checkUserIsLogged(request) ? (Boolean) request.getSession().getAttribute("isAdmin") : Boolean.FALSE;
    }
	
	static String error(int statusCode) {
		return "error:" + statusCode;
	}
	
	static String json(String jsonObject) {
		return "json:" + jsonObject;
	}
	
	static String redirect(String path) {
		return "redirect:" + path;
	}
	
	public static Map<String, String> getParameterMap(HttpServletRequest request) {

	    BufferedReader br = null;
	    Map<String, String> dataMap = null;

	    try (InputStreamReader reader = new InputStreamReader(
                request.getInputStream());) {

	        br = new BufferedReader(reader);

	        String data = br.readLine();

	        dataMap = Splitter.on('&')
	                .trimResults()
	                .withKeyValueSeparator(
	                        Splitter.on('=')
	                        .limit(2)
	                        .trimResults())
	                .split(data);
	        return dataMap;
	    } catch (IOException ex) {
	        // TODO: put logger
	    }

	    return dataMap;
	}
	
	// Decodes a URL encoded string using `UTF-8`
    public static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

}
