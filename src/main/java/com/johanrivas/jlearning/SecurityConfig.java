package com.johanrivas.jlearning;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/courses", "/listar**", "/locale").permitAll()

				/*
				 * .and() .formLogin() .successHandler(successHandler) .loginPage("/login")
				 * .permitAll() .and() .logout().permitAll() .and()
				 * .exceptionHandling().accessDeniedPage("/error_403")
				 *
				 */

				.and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

}
