package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.RoleRepository;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

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
//	@WithMockUser(username = "testuser", password = "testPassword", roles = "USER")
//	public void bidListTest() {
//		BidList bid = new BidList("Account Test", "Type Test", 10d);
//
//		// Save
//		bid = bidListRepository.save(bid);
//		Assert.assertNotNull(bid.getBidListId());
//		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
//
//		// Update
//		bid.setBidQuantity(20d);
//		bid = bidListRepository.save(bid);
//		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);
//
//		// Find
//		List<BidList> listResult = bidListRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = bid.getBidListId();
//		bidListRepository.delete(bid);
//		Optional<BidList> bidList = bidListRepository.findById(id);
//		Assert.assertFalse(bidList.isPresent());
//	}

	@Test
	@WithMockUser(username = "testuser")
	public void bidAddTest() throws Exception{
		mockMvc.perform(post("/bidList/validate")
				.param("account", "Account Test")
				.param("type", "Type test")
				.param("bidQuantity", "10"))
				.andExpect(redirectedUrl("/bidList/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void bidUpdateTest() throws Exception{
		mockMvc.perform(post("/bidList/update/{id}", 12)
						.param("account", "Account Test")
						.param("type", "Type test")
						.param("bidQuantity", "20"))
				.andExpect(redirectedUrl("/bidList/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void bidDeleteTest() throws Exception{
		BidList bid = new BidList("Account Test", "Type Test", 10d);
		bid = bidListRepository.save(bid);
		mockMvc.perform(get("/bidList/delete/{id}", bid.getBidListId()))
				.andExpect(redirectedUrl("/bidList/list")).andExpect(status().isFound());
	}
}
