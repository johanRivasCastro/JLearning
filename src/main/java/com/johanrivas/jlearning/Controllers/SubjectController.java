package com.johanrivas.jlearning.Controllers;

import java.util.List;

import javax.validation.Valid;

import com.johanrivas.jlearning.Execptions.BindingResultException;
import com.johanrivas.jlearning.Services.interfaces.SubjectService;
import com.johanrivas.jlearning.models.Entities.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> getSubjects() {
        return new ResponseEntity<List<Subject>>(subjectService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/subjects")
    public ResponseEntity<Subject> save(@Valid @RequestBody Subject subject, BindingResult result) {
        if (result.hasErrors()) {
            throw new BindingResultException(result);
        }
        return new ResponseEntity<Subject>(subjectService.save(subject), HttpStatus.OK);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        subjectService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/subjects")
    public ResponseEntity<?> edit(@Valid @RequestBody Subject subject, BindingResult result) {
        if (result.hasErrors()) {
            throw new BindingResultException(result);
        }
        return new ResponseEntity<>(subjectService.edit(subject), HttpStatus.OK);
    }
}