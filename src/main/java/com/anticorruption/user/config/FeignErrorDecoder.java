package com.anticorruption.user.config;

import com.anticorruption.user.exception.ReferenceServiceException;
import com.anticorruption.user.exception.ReferenceServiceNotFoundException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		if(response.status() == 404) {
			return new ReferenceServiceNotFoundException("Reference service not found");
		}
		
		if(response.status() >= 500) {
			return new ReferenceServiceException("Reference service is unavailable");
		}
		
		return new ReferenceServiceException("Reference service is returned an error");
	}

	
}
