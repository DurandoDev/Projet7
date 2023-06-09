package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.RoleRepository;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

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
//	public void tradeTest() {
//		Trade trade = new Trade("Trade Account", "Type");
//
//		// Save
//		trade = tradeRepository.save(trade);
//		Assert.assertNotNull(trade.getTradeId());
//		Assert.assertTrue(trade.getAccount().equals("Trade Account"));
//
//		// Update
//		trade.setAccount("Trade Account Update");
//		trade = tradeRepository.save(trade);
//		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));
//
//		// Find
//		List<Trade> listResult = tradeRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = trade.getTradeId();
//		tradeRepository.delete(trade);
//		Optional<Trade> tradeList = tradeRepository.findById(id);
//		Assert.assertFalse(tradeList.isPresent());
//	}


	@Test
	@WithMockUser(username = "testuser")
	public void tradeAddTest() throws Exception{
		mockMvc.perform(post("/trade/validate")
						.param("account", "42")
						.param("type", "15")
						.param("buyQuantity", "10"))
				.andExpect(redirectedUrl("/trade/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void tradeUpdateTest() throws Exception{
		mockMvc.perform(post("/trade/update/{id}", 12)
						.param("account", "42")
						.param("type", "15")
						.param("buyQuantity", "10"))
				.andExpect(redirectedUrl("/trade/list")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "testuser")
	public void tradeDeleteTest() throws Exception{
		Trade trade = new Trade("42","15",10d);
		trade = tradeRepository.save(trade);
		mockMvc.perform(get("/trade/delete/{id}", trade.getTradeId()))
				.andExpect(redirectedUrl("/trade/list")).andExpect(status().isFound());
	}

}
