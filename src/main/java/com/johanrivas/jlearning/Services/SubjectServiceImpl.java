package com.johanrivas.jlearning.Services;

import java.util.List;

import com.johanrivas.jlearning.Dao.SubjectDao;
import com.johanrivas.jlearning.Execptions.UniqueConstraintViolationException;
import com.johanrivas.jlearning.Services.interfaces.SubjectService;
import com.johanrivas.jlearning.models.Entities.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    @Override
    public List<Subject> findAll() {
        return (List<Subject>) subjectDao.findAll();
    }

    @Override
    public Subject save(Subject subject) {
        Subject subjectByName = subjectDao.findByName(subject.getName());
        if (subjectByName != null) {
            throw new UniqueConstraintViolationException("there is allready a subject with this name");
        }
        return subjectDao.save(subject);
    }

    @Override
    public void delete(Long id) {
        subjectDao.deleteById(id);
    }

    @Override
    public Subject edit(Subject subject) {
        Subject subjectByName = subjectDao.findByName(subject.getName());
        if (subjectByName != null) {
            if (subjectByName.getId() != subject.getId()) {
                throw new UniqueConstraintViolationException("there is allready a subject with this name");
            }
        }

        return subjectDao.save(subject);
    }

}