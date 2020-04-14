package com.johanrivas.jlearning.models.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "courses")
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "the name is required")
	@Length(min = 3, message = "the description must have at least 3 characters")
	@Column(nullable = false, unique = true)
	private String name;

	@NotEmpty(message = "the description is required")
	@Length(min = 10, message = "the description must have at least 10 characters")
	@Column(nullable = false)
	private String description;

	@Length(max = 150, message = "the img url can't have more than 150 characteres")
	private String img;

	@Column(columnDefinition = "boolean default false")
	private Boolean active;

	@Column(columnDefinition = "decimal default 0.0")
	private float rate;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<CourseContent> courseContents;

	@PrePersist
	public void prePersist() {
		created = new Date();
	}

	public Course() {
		courseContents = new ArrayList<CourseContent>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<CourseContent> getCourseContents() {
		return courseContents;
	}

	public void setCourseContents(List<CourseContent> courseContents) {
		this.courseContents = courseContents;
	}

}
