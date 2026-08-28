package com.anticorruption.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.anticorruption.user.entity.SubscriptionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

	private UUID id;
	
	private String name;
	
	private String email;
	
	private String state;
	
	private String mobile;
	
	private String stateCode;
	
	private String district;
	
	private SubscriptionType subscriptionType;
	
	private LocalDateTime subscriptionStartDate;
	
	private LocalDateTime subscriptionEndDate;
	
	private boolean subscriptioActive;
	
	private boolean active;
}
