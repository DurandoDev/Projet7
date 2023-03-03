package com.nnk.springboot.config;

import com.nnk.springboot.services.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {




	@Autowired
	private UserDetailsService userDetailsService;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomOAuth2UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/user/add").permitAll()
				.antMatchers("/admin/*").authenticated()
				.antMatchers("/user/*").authenticated()
				.anyRequest().permitAll()
				.and()
				.csrf().disable()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/bidList/add",true)
				.and()
				.logout().logoutSuccessUrl("/login")
				.and()
				.oauth2Login()
					.loginPage("/login")
					.userInfoEndpoint()
						.userService(userService)
				.and()
				.defaultSuccessUrl("/bidList/add",true);
	}
}
