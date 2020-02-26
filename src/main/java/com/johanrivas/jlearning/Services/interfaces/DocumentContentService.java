package com.johanrivas.jlearning.Services.interfaces;

import com.johanrivas.jlearning.Entities.DocumentContent;

public interface DocumentContentService {

	DocumentContent findById(Long id);

	DocumentContent save(DocumentContent course);

	void delete(Long id);
}
