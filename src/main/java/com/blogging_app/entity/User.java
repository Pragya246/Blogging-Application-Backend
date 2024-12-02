package com.blogging_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserSeq")
	@SequenceGenerator(name = "UserSeq", allocationSize = 1)
	private int uid;
	@Column(nullable = false, length = 60)
	private String name;
	private String about;
	@Column(unique=true)
	private String email;
	private String password;
}
