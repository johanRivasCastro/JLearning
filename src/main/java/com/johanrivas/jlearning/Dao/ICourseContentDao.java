package com.johanrivas.jlearning.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.johanrivas.jlearning.Entities.CourseContent;

@Repository
public interface ICourseContentDao extends CrudRepository<CourseContent, Long> {

	@Query("select t from CourseContent t join fetch t.course c where c.id = ?1")
	public List<CourseContent> courseContentsByCourseId(Long courseId);
}
