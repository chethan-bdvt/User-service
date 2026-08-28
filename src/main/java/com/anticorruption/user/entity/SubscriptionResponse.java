package com.anticorruption.user.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionResponse {

	private SubscriptionType subscriptionType;
	
	private LocalDateTime subscriptionStartDate;
	
	private LocalDateTime subscriptionEndDate;
	
	private boolean subscriptionActive;
	
	
}
