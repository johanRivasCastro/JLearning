package com.johanrivas.jlearning.Execptions;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(Long id) {
		super("the requested resource with the id " + id + " Does not exist");
	}

	public ResourceNotFoundException(String email) {
		super("the requested resource with the email " + email + " Does not exist");
	}

}
