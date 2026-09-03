package com.anticorruption.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anticorruption.user.config.ReferenceServiceClient;
import com.anticorruption.user.dto.UpdateProfileRequest;
import com.anticorruption.user.dto.UserResponse;
import com.anticorruption.user.entity.SubscriptionRequest;
import com.anticorruption.user.entity.SubscriptionResponse;
import com.anticorruption.user.entity.SubscriptionType;
import com.anticorruption.user.entity.User;
import com.anticorruption.user.exception.UserNotFoundException;
import com.anticorruption.user.repository.UserRepository;

@Service
@Transactional
public class UserService {
	

	private UserRepository userRepository;
	
	private ReferenceServiceClient referenceServiceClient;
	
	public UserResponse userResponse;
	
	public SubscriptionResponse subscriptionResponse;

	@Autowired
	public UserService(UserRepository userRepository, ReferenceServiceClient referenceClient) {
		this.userRepository = userRepository;
		this.referenceServiceClient = referenceClient;
	}
	
	@Transactional(readOnly= true)
	public  UserResponse getUserId(UUID userId) {
			User user = userRepository.findById(userId).orElseThrow(()->
			new UserNotFoundException("User not found"));
			return mapToResponse(user);
	}
	
	@Transactional(readOnly = true)
	public UserResponse getUserByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(()->
		new UserNotFoundException("User Not found"));
		return mapToResponse(user);
	}
	
	public UserResponse createUser(String name,
			String email, String mobile, String stateName,
			String districName) {
		if(userRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("Email Already exists");
		}
		
		if(userRepository.existsByMobileNumber(mobile)) {
			throw new IllegalArgumentException("Mobile Number is already exist");
		}
		
		ReferenceServiceClient.StateResponse state = referenceServiceClient.getStateByName(stateName);
		
		ReferenceServiceClient.DistrictResponse district = referenceServiceClient.getStateAndDisttictByName(
				stateName, districName);
		
		User user = new User();
		user.setFirstName(name);
		user.setEmail(email);
		user.setMobileNumber(mobile);
		user.setStateId(state.id());
		user.setDistrictId(district.id());
		User createdUser = userRepository.save(user);
		return mapToResponse(createdUser);
	}
	
	public UserResponse updateProfile(UUID userId, UpdateProfileRequest request) {
		
		User user = userRepository.findById(userId).orElseThrow(()->
		new UserNotFoundException("User not found"));
	
	
	if(request.getName() != null && !request.getName().isBlank()) {
		user.setFirstName(request.getName());
	}
	
	if(request.getMobile() != null && !request.getMobile().isBlank()) {
		if(request.getMobile().equals(user.getMobileNumber()) && userRepository.existsByMobileNumber(request.getMobile())) {
		throw new IllegalArgumentException("Mobile already registered");
		}
		user.setMobileNumber(request.getMobile());
	}
	
		User updatedUser = userRepository.save(user);
		
		return mapToResponse(updatedUser);
	}
	
	public UserResponse updateActiveStatus(UUID userId, boolean active) {
		User user = userRepository.findById(userId).orElseThrow(()->
				new RuntimeException("User not found"));
		
		user.setActive(active);
		
		return mapToResponse(userRepository.save(user));
	}

	private UserResponse mapToResponse(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setName(user.getFirstName());
		userResponse.setEmail(user.getEmail());
		userResponse.setMobile(user.getMobileNumber());
		if(user.getStateId() != null) {
			ReferenceServiceClient.StateResponse state = referenceServiceClient.getStateById(user.getStateId());
			
			userResponse.setState(state.name());
			userResponse.setStateCode(state.code());
		}
		
		if(user.getDistrictId() != null) {
			ReferenceServiceClient.DistrictResponse district = referenceServiceClient.getDistrictById(user.getDistrictId());
			userResponse.setDistrict(district.name());
		}
		
		userResponse.setSubscriptionType(user.getSubscriptionType());
		userResponse.setSubscriptionEndDate(user.getSubscriptionEndDate());
		userResponse.setActive(user.isActive());
		return userResponse;
	}
	
	public SubscriptionResponse updateSubscription(UUID userId,
			SubscriptionRequest request) {
		User user = userRepository.findById(userId).orElseThrow(()->
		new UserNotFoundException("User not found"));
		
		SubscriptionType type = request.getSubscriptionType();
		
		if(type == null) {
			throw new IllegalArgumentException("Subscription type is required");
		}
		
		if(type == SubscriptionType.FREE) {
			user.setSubscriptionType(SubscriptionType.FREE);
			user.setSubscriptionStartDate(null);
			user.setSubscriptionEndDate(null);
			user.setSubscriptionActive(false);
			
		} else {
			LocalDateTime startDate = LocalDateTime.now();
			LocalDateTime endDate = null;
			if(type == SubscriptionType.PREMIUM_MONTHLY) {
				endDate = startDate.plusMonths(1);
			} else if(type == SubscriptionType.PREMIUM_YEARLY) {
				endDate = startDate.plusYears(1);
			}
			
			user.setSubscriptionType(type);
			user.setSubscriptionStartDate(startDate);
			user.setSubscriptionEndDate(endDate);
			user.setSubscriptionActive(true);
		}
		User savedUser = userRepository.save(user);
		
		return mapToSubscriptionResponse(savedUser);
		
	}
	
	@Transactional(readOnly = true)
	public SubscriptionResponse getSubscription(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(()->
		 new UserNotFoundException("User not found"));
		
		return mapToSubscriptionResponse(user);
	}
	
	private SubscriptionResponse mapToSubscriptionResponse(User user) {
		subscriptionResponse.setSubscriptionStartDate(user.getSubscriptionStartDate());
		subscriptionResponse.setSubscriptionEndDate(user.getSubscriptionEndDate());
		subscriptionResponse.setSubscriptionType(user.getSubscriptionType());
		subscriptionResponse.setSubscriptionActive(user.isSubscriptionActive());
		return subscriptionResponse;
	}
	
	public List<UserResponse> getAllUsers() {
			List<User> users = userRepository.findAll();
			return users.stream().map(this::mapToResponse).toList();
	}
	
}
