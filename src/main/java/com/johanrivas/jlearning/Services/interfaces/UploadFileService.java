package com.johanrivas.jlearning.Services.interfaces;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

	public Resource load(String filename) throws MalformedURLException;

	public String copy(MultipartFile file);

	public boolean delete(String filename);

	public void deleteAll();

	public void init() throws IOException;
}
