package com.nnk.springboot;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.RoleRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	User u = new User();
	Role role =new Role();

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();

		u.setFullname("toto");
		u.setUsername("testuser");

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		u.setPassword(encoder.encode("testPassword"));

		role = roleRepository.findByName("USER").get();
		u.setRole(role);

		userRepository.save(u);
	}

	@AfterEach
	public void deleteSetup() throws Exception {
		userRepository.deleteAll();
	}


	@Test
	@WithMockUser(username = "testuser")
	public void ruleAddTest() throws Exception{
		mockMvc.perform(post("/ruleName/validate")
						.param("name", "rule name")
						.param("description", "description test")
						.param("json", "json test")
						.param("template","template test")
						.param("sqlStr","str test")
						.param("sqlPart","part test"))
				.andExpect(redirectedUrl("/ruleName/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void ruleUpdateTest() throws Exception{
		mockMvc.perform(post("/ruleName/update/{id}", 12)
						.param("name", "rule name")
						.param("description", "description test")
						.param("json", "json test")
						.param("template","template test")
						.param("sqlStr","str test")
						.param("sqlPart","part test"))
				.andExpect(redirectedUrl("/ruleName/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void ruleDeleteTest() throws Exception{
		RuleName ruleName = new RuleName("rule name","description test","json test", "template test","str test","part test");
		ruleName = ruleNameRepository.save(ruleName);
		mockMvc.perform(get("/ruleName/delete/{id}", ruleName.getId()))
				.andExpect(redirectedUrl("/ruleName/list")).andExpect(status().isFound());
	}

}
