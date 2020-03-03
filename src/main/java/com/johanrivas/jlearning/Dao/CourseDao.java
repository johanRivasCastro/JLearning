package com.johanrivas.jlearning.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.johanrivas.jlearning.Entities.Course;

@Repository
public interface CourseDao extends PagingAndSortingRepository<Course, Long> {

    // @Query("select u from User u where u.name like %?1% or u.lastname like %?1%
    // or u.identification like %?1% or u.email like %?1%")
    @Query("select c from Course c where c.name like %?1% ")
    Page<Course> findByTerm(String term, PageRequest pageRequest);

    Course findByName(String name);

    // @Query("SELECT * FROM COURSES c WHERE c.user_id =?1")
    // Course findCourseByUserId(String id);
}
