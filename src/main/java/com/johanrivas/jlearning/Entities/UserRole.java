package com.johanrivas.jlearning.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "algo")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id", nullable = false)
//	@JsonBackReference
//	private User user;
//	
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "role_id", nullable = false)
//	@JsonBackReference
//	private Role role;
}
