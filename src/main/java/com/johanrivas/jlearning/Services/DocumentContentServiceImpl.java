package com.johanrivas.jlearning.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johanrivas.jlearning.Dao.DocumentContentDao;
import com.johanrivas.jlearning.Entities.DocumentContent;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;
import com.johanrivas.jlearning.Services.interfaces.DocumentContentService;

@Service
public class DocumentContentServiceImpl implements DocumentContentService {

	@Autowired
	private DocumentContentDao documentsDao;

	@Override
	public DocumentContent save(DocumentContent documentContent) {
		return documentsDao.save(documentContent);
	}

	@Override
	public void delete(Long id) {
		documentsDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		documentsDao.deleteById(id);
	}

	@Override
	public DocumentContent findById(Long id) {
		return documentsDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

}
