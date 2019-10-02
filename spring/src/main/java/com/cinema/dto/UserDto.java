package com.cinema.dto;

import javax.validation.constraints.NotBlank;

import com.cinema.dto.validator.PasswordMatches;
import com.cinema.dto.validator.ValidEmail;
import com.cinema.dto.validator.ValidName;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatches
@ToString
public class UserDto {
	
	@ValidEmail
	@NotBlank(message = "Email may not be blank")
	private String email;
	
    @NotBlank(message = "Password may not be blank")
    private String password;
    private String matchingPassword;
    
    @ValidName
    @NotBlank(message = "Name may not be blank")
    private String username;
    
    @NotBlank(message = "Phone number may not be blank")
    private String tel;
    
}
