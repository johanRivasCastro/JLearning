package com.johanrivas.jlearning.Services.interfaces;

import com.johanrivas.jlearning.models.Entities.VideoContent;

public interface VideoContentService {
	VideoContent findById(Long id);

	VideoContent save(VideoContent videoContent);

	void delete(Long id);
}
