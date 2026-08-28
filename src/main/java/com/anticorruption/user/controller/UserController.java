package com.anticorruption.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anticorruption.user.dto.CreateUserRequest;
import com.anticorruption.user.dto.UpdateProfileRequest;
import com.anticorruption.user.dto.UserResponse;
import com.anticorruption.user.entity.User;
import com.anticorruption.user.repository.UserRepository;
import com.anticorruption.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final UserRepository userRepository;
	
	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;	
		this.userRepository = userRepository;
	}
	
	@PostMapping("")
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
		UserResponse user = userService.createUser(request.getName(), request.getEmail(), 
				request.getMobileNumber(), request.getStateId(), request.getDistrictId());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userId) {
		return ResponseEntity.ok(userService.getUserId(userId));
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserResponse> updateProfile(@PathVariable UUID userId, @Valid @RequestBody UpdateProfileRequest request) {
		return ResponseEntity.ok(userService.updateProfile(userId, request));
	}
	
	@PatchMapping("/{userId}/status")
	public ResponseEntity<UserResponse> updateStatus(@PathVariable UUID userId, @RequestParam boolean active) {
		return ResponseEntity.ok(userService.updateActiveStatus(userId, active));
	}
	
	@GetMapping("")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userRepository.findAll();
		return ResponseEntity.ok(users);
	}
	
	
	
}


