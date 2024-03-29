package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RoleRepository;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

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

		role.setName("USER");

		u.setFullname("toto");
		u.setUsername("testuser");
		u.setPassword("testPassword");

		u.setRole(role);

		roleRepository.save(role);
		userRepository.save(u);
	}

	@AfterEach
	public void deleteSetup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();

		roleRepository.delete(role);
		userRepository.delete(u);
	}

//	@Test
//	public void curvePointTest() {
//		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
//
//		// Save
//		curvePoint = curvePointRepository.save(curvePoint);
//		Assert.assertNotNull(curvePoint.getId());
//		Assert.assertTrue(curvePoint.getCurveId() == 10);
//
//		// Update
//		curvePoint.setCurveId(20);
//		curvePoint = curvePointRepository.save(curvePoint);
//		Assert.assertTrue(curvePoint.getCurveId() == 20);
//
//		// Find
//		List<CurvePoint> listResult = curvePointRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = curvePoint.getId();
//		curvePointRepository.delete(curvePoint);
//		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
//		Assert.assertFalse(curvePointList.isPresent());
//	}

	@Test
	@WithMockUser(username = "testuser")
	public void curvePointAddTest() throws Exception{
		mockMvc.perform(post("/curvePoint/validate")
						.param("curveId", "42")
						.param("term", "15")
						.param("value", "10"))
				.andExpect(redirectedUrl("/curvePoint/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void curvePointUpdateTest() throws Exception{
		mockMvc.perform(post("/curvePoint/update/{id}", 12)
						.param("curveId", "42")
						.param("term", "16")
						.param("value", "11"))
				.andExpect(redirectedUrl("/curvePoint/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void curvePointDeleteTest() throws Exception{
		CurvePoint curvePoint = new CurvePoint(28, 15, 10d);
		curvePoint = curvePointRepository.save(curvePoint);
		mockMvc.perform(get("/curvePoint/delete/{id}", curvePoint.getId()))
				.andExpect(redirectedUrl("/curvePoint/list")).andExpect(status().isFound());
	}

}
