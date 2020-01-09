package com.johanrivas.jlearning.Entities;

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
@Table(name = "course_contents")
public class CourseContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "the title is required")
	@Column(name = "title", nullable = false)
	@Length(min = 3, max = 150, message = "the title must have between 3 and 150 characteres")
	private String title;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	@JsonBackReference
	private Course course;

	@OneToMany(mappedBy = "courseContent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<DocumentContent> documents;

	@OneToMany(mappedBy = "courseContent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<VideoContent> videoContents;

	@PrePersist
	public void prePersist() {
		created = new Date();
	}

	public CourseContent() {
		documents = new ArrayList<DocumentContent>();
		videoContents = new ArrayList<VideoContent>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<DocumentContent> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentContent> documents) {
		this.documents = documents;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<VideoContent> getVideoContents() {
		return videoContents;
	}

	public void setVideoContents(List<VideoContent> videoContents) {
		this.videoContents = videoContents;
	}

}
