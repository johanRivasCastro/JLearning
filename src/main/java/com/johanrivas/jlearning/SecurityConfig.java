package com.johanrivas.jlearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.johanrivas.jlearning.auth.filter.JWTAuthenticationFilter;
import com.johanrivas.jlearning.auth.filter.JWTAuthorizationFilter;
import com.johanrivas.jlearning.auth.service.JWTService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JWTService jwtService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/courses").permitAll().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService)).csrf().disable()

				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

}
