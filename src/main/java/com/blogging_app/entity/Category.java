package com.blogging_app.entity;

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
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CategorySeq")
	@SequenceGenerator(name = "CategorySeq", allocationSize = 1)
	private int categoryId;

	private String categoryTitle;

	private String categoryDescription;

}
