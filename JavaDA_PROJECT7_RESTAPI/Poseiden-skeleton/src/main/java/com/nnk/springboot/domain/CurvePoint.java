package com.nnk.springboot.domain;

import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "curvepoint")
public class CurvePoint {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	@NotNull(message = "must not be null")
	private Integer curveId;
	@NotNull(message = "must not be null")
	double term;
	@NotNull(message = "must not be null")
	double value;

	Timestamp asOfDate;

	Timestamp creationDate;

	public CurvePoint(int curveId, double term, double value) {
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

	public CurvePoint() {

	}

	public Integer getId() {return id;}

	public void setId(Integer id) {this.id = id;}
}
