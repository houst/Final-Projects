package com.cinema.dto.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {
  
	// TODO: вынести регулярки в отдельный файл с константами для каждой локали
	private static final String NAME_PATTERN = "^[A-Z][a-z]{1,20}$" + "|"	
		  									+ "^[А-ЩЬЮЯҐІЇЄ][а-щьюяґіїє']{1,20}$"; 
  
	@Override
	public void initialize(ValidName constraintAnnotation) {
		// do nothing
	}
	  
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context){   
		return validateEmail(email);
	}
	  
	private boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(NAME_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
  
}