package com.nnk.springboot.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "curvepoint")
public class CurvePoint {

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	@NotNull(message = "must not be null")
	private Integer curveId;

	Timestamp asOfDate;

	@NotNull(message = "must not be null")
	double term;

	@NotNull(message = "must not be null")
	double value;

	Timestamp creationDate;

	public CurvePoint(int curveId, double term, double value) {

	}

	public CurvePoint() {

	}

	public int getCurveId() {return curveId;}

	public void setCurveId(int id) {this.curveId = curveId;}

	public Integer getId() {return id;}
}
