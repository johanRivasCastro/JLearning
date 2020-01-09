package com.johanrivas.jlearning.Services;

import com.johanrivas.jlearning.Entities.DocumentContent;

public interface IDocumentContentService {

	DocumentContent findById(Long id);

	DocumentContent save(DocumentContent course);

	void delete(Long id);
}
