package com.johanrivas.jlearning.Services.interfaces;

import java.util.List;

import com.johanrivas.jlearning.models.Entities.Subject;

public interface SubjectService {

    List<Subject> findAll();

    Subject save(Subject subject);

    void delete(Long id);

    Subject edit(Subject subject);

}