package com.johanrivas.jlearning.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lectureContents")
public class LectureContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "url_resource")
	@Length(min = 5, max = 150)
	private String urlResource;

	@Column(name = "content_type")
	@Length(min = 5, max = 10)
	private String contentType;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "lecture_id")
//	private Lecture lecture;
}
