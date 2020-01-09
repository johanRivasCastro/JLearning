package com.johanrivas.jlearning.Execptions;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(Long id) {
		super("the requested resource with the id " + id + " Does not exist");
	}

}
