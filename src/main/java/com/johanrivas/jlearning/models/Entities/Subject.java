package com.johanrivas.jlearning.models.Entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "the name is required")
    @Length(min = 3, message = "the description must have at least 3 characters")
    @Column(nullable = false, unique = true)
    private String name;

    @NotEmpty(message = "the description is required")
    // @Length(min = 10, message = "the description must have at least 10
    // characters")
    @Column(nullable = false)
    private String description;

    private int credits;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;

    @Column(columnDefinition = "boolean default false")
    private Boolean enable;

    @PrePersist
    public void prePersist() {
        created = new Date();
    }

}