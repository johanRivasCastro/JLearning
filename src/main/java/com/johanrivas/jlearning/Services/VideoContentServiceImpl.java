package com.johanrivas.jlearning.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johanrivas.jlearning.Dao.IVideoContentDao;
import com.johanrivas.jlearning.Entities.VideoContent;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;

@Service
public class VideoContentServiceImpl implements IVideoContentService {

	@Autowired
	private IVideoContentDao videoContentDao;

	@Override
	public VideoContent findById(Long id) {
		return videoContentDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public VideoContent save(VideoContent videoContent) {

		return videoContentDao.save(videoContent);
	}

	@Override
	public void delete(Long id) {
		videoContentDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		videoContentDao.deleteById(id);
	}

}
