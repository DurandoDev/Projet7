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
	@Column(name = "id", nullable = false)
	private Integer id;

	String name;

	String description;

	String json;

	String template;

	String sqlStr;

	String sqlPart;

	public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
	}

	public RuleName() {

	}

	public Integer getId() {return id;}

	public void setId(Integer id) {this.id = id;}

	public Object getName() {return name;}

	public void setName(String name) {this.name = name;}
}
