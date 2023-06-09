package com.nnk.springboot.services;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService {

	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void createDefaultRoles() {
		Role adminRole = new Role(1L, "ADMIN");
		Role userRole = new Role(2L, "USER");

		roleRepository.save(adminRole);
		roleRepository.save(userRole);
	}
}