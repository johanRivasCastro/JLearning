package com.johanrivas.jlearning.Dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.johanrivas.jlearning.Entities.Course;

@Repository
public interface ICourseDao extends PagingAndSortingRepository<Course, Long> {

}
