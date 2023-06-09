package com.nnk.springboot;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.RoleRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTests {

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
	public void shouldReturnDefaultMessage() throws Exception {
		mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void userLoginTest() throws Exception {
		mockMvc.perform(formLogin("/login").user("testuser").password("testPassword")).andExpect(authenticated());
	}

	@Test
	public void userLoginFailed() throws Exception {
		mockMvc.perform(formLogin("/login").user("springuser").password("wrongpassword")).andExpect(unauthenticated());
	}
}
