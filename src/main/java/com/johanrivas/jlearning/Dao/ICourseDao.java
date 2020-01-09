package com.johanrivas.jlearning.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.johanrivas.jlearning.Entities.Course;

@Repository
public interface ICourseDao extends CrudRepository<Course, Long> {

}
