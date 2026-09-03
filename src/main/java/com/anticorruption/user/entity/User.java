package com.anticorruption.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(name="name", nullable = false, length = 20)
	private String firstName;
	
	@Column(name = "email",nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(name = "mobile_number", nullable = true, unique = true,length = 10)
	private String mobileNumber;
	
	@Column(name = "state_id", nullable = false)
	private UUID stateId;
	
	@Column(name = "district_id", nullable = false)
	private UUID districtId;
	
	@Column(name = "subscription_type", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private SubscriptionType subscriptionType = SubscriptionType.FREE;
	
	@Column(name = "subscription_start_date")
	private LocalDateTime subscriptionStartDate;
	
	@Column(name ="subscription_end_date")
	private LocalDateTime subscriptionEndDate;
	
	@Column(name = "subscription_active", nullable = false)
	private boolean subscriptionActive = false;
	
	@Column(nullable = true)
	private boolean active = true;
	
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	
	@PrePersist
	protected void onCreate() {
		if(id == null) {
			id = UUID.randomUUID();
		}
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
		
		if(subscriptionType == null) {
			subscriptionType = SubscriptionType.FREE;
		}
		active = true;
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
	
	
}
