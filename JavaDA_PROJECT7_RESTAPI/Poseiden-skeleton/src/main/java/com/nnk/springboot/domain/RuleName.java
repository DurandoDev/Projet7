package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "rulename")
public class RuleName {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	@NotBlank(message = "Name is mandatory")
	String name;

	@NotBlank(message = "Description is mandatory")
	String description;

	@NotBlank(message = "Json is mandatory")
	String json;

	@NotBlank(message = "Template is mandatory")
	String template;

	@NotBlank(message = "SQLStr is mandatory")
	String sqlStr;

	@NotBlank(message = "SQLPart is mandatory")
	String sqlPart;

	public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}

	public RuleName() {

	}
	public Integer getId() {return id;}

	public void setId(Integer id) {this.id = id;}

}
