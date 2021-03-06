package com.johanrivas.jlearning.Dao;

import org.springframework.data.repository.CrudRepository;

import com.johanrivas.jlearning.models.Entities.DocumentContent;

public interface DocumentContentDao extends CrudRepository<DocumentContent, Long> {
    // @Query("select t from DocumentContent t join fetch t.courseContent c where
    // c.id = ?1")
    // public List<DocumentContent> documentContentBy(Long courseId);
}
