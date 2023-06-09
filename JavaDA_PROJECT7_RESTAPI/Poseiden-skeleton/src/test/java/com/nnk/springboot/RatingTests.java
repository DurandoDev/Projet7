package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

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
//	public void ratingTest() {
//		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
//
//		// Save
//		rating = ratingRepository.save(rating);
//		Assert.assertNotNull(rating.getId());
//		Assert.assertTrue(rating.getOrderNumber() == 10);
//
//		// Update
//		rating.setOrderNumber(20);
//		rating = ratingRepository.save(rating);
//		Assert.assertTrue(rating.getOrderNumber() == 20);
//
//		// Find
//		List<Rating> listResult = ratingRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = rating.getId();
//		ratingRepository.delete(rating);
//		Optional<Rating> ratingList = ratingRepository.findById(id);
//		Assert.assertFalse(ratingList.isPresent());
//	}

	@Test
	@WithMockUser(username = "testuser")
	public void ratingsAddTest() throws Exception{
		mockMvc.perform(post("/rating/validate")
						.param("moodysRating", "42")
						.param("sandPRating", "15")
						.param("fitchRating", "10")
						.param("orderNumber","3"))
				.andExpect(redirectedUrl("/rating/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void ratingsUpdateTest() throws Exception{
		mockMvc.perform(post("/rating/update/{id}", 12)
						.param("moodysRating", "42")
						.param("sandPRating", "15")
						.param("fitchRating", "10")
						.param("orderNumber","3"))
				.andExpect(redirectedUrl("/rating/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void ratingsDeleteTest() throws Exception{
		Rating rating = new Rating("42", "15","10", 3);
		rating = ratingRepository.save(rating);
		mockMvc.perform(get("/rating/delete/{id}", rating.getId()))
				.andExpect(redirectedUrl("/rating/list")).andExpect(status().isFound());
	}
}
