package com.johanrivas.jlearning.Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "video_contents")
public class VideoContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "the title is required")
	@Column(name = "title", nullable = false)
	@Length(min = 3, max = 150, message = "the title must have between 3 and 150 characteres")
	private String title;

	@Column(name = "url_resource", nullable = false)
	@Length(min = 5, max = 200)
	private String urlResource;

	@Column(name = "content_type")
	@Length(min = 5, max = 10)
	private String contentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courseContent_id", nullable = false)
	@JsonBackReference
	private CourseContent courseContent;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created;

	public VideoContent() {

	}

	public VideoContent(String title, String urlResource, CourseContent courseContent) {
		this.title = title;
		this.urlResource = urlResource;
		this.courseContent = courseContent;
	}

	@PrePersist
	public void prePersist() {
		created = new Date();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlResource() {
		return urlResource;
	}

	public void setUrlResource(String urlResource) {
		this.urlResource = urlResource;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public CourseContent getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(CourseContent courseContent) {
		this.courseContent = courseContent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
