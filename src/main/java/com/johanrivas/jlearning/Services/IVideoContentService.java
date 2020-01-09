package com.johanrivas.jlearning.Services;

import com.johanrivas.jlearning.Entities.VideoContent;

public interface IVideoContentService {
	VideoContent findById(Long id);

	VideoContent save(VideoContent videoContent);

	void delete(Long id);
}
