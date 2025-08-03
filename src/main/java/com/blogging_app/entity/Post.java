package com.blogging_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.util.Date;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PostSeq")
	@SequenceGenerator(name = "PostSeq", allocationSize = 1)
	private int postId;

	private String title;

	@Column(length = 10000)
	private String caption;

	private String imgName;

	private Date addedDate;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
