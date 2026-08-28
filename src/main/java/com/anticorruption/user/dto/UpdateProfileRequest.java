package com.anticorruption.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateProfileRequest {

	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
	String mobile;
	
	String name;
}
