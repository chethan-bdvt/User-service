package com.anticorruption.user.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

	@NotBlank(message = "Name is required")
	String name;
	
	@NotBlank(message ="Email is required")
	@Email(message = "Invalid email")
	String email;
	
	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid Mobile Number")
	String mobileNumber;
	
	@NotNull(message="State is required")
	private String state;
	
	@NotNull(message = "District is required")
	private String district;
}
