package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "rating")
public class Rating {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	@NotBlank(message = "Moodys Rating is mandatory")
	String moodysRating;

	@NotBlank(message = "Sand PRating is mandatory")
	String sandPRating;

	@NotBlank(message = "Fitch Rating is mandatory")
	String fitchRating;

	@NotNull(message = "must not be null")
	Integer orderNumber;

	public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	public Rating() {

	}

	public Integer getId() {return id;}

	public void setId(Integer id) {this.id = id;}

}
