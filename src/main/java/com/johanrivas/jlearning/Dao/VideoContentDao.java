package com.johanrivas.jlearning.Dao;

import org.springframework.data.repository.CrudRepository;

import com.johanrivas.jlearning.models.Entities.VideoContent;

public interface VideoContentDao extends CrudRepository<VideoContent, Long> {

}
