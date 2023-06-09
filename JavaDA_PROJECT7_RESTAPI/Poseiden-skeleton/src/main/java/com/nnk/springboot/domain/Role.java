package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "role")
public class Role {

	@Id
	private Long id;
	private String name;

	public Role(String roleName) {
		this.name = roleName;
	}

	public Role() {

	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}

