package com.johanrivas.jlearning.Dao;

import org.springframework.data.repository.CrudRepository;

import com.johanrivas.jlearning.Entities.VideoContent;

public interface IVideoContentDao extends CrudRepository<VideoContent, Long> {

}
