package com.johanrivas.jlearning.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johanrivas.jlearning.Dao.IDocumentContentDao;
import com.johanrivas.jlearning.Entities.DocumentContent;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;

@Service
public class DocumentContentServiceImpl implements IDocumentContentService {

	@Autowired
	private IDocumentContentDao documentsDao;

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
