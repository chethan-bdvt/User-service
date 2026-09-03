package com.anticorruption.user.config;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
		name = "reference-service",
		url = "${reference.service.url}",
		configuration = FeignErrorDecoder.class
		)
public interface ReferenceServiceClient {

	@GetMapping("/states/name/{name}")
	StateResponse getStateByName(@PathVariable("name") String name );
	
	@GetMapping("/district/state/{stateName}/district/{districtName}")
	DistrictResponse getStateAndDisttictByName(@PathVariable("stateName") String stateName, 
			@PathVariable("districtName") String districtName);
	
	@GetMapping("/states/{id}")
	StateResponse getStateById(@PathVariable("id") UUID id);
	
	@GetMapping("/district/{id}")
	DistrictResponse getDistrictById(@PathVariable("id") UUID id);
	
	record StateResponse(UUID id,  String code, String name, boolean active) {}
	
	record DistrictResponse(UUID id, String code, String name, boolean active) {}
	
}
