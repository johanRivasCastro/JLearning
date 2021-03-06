package com.johanrivas.jlearning.auth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.johanrivas.jlearning.models.Entities.User;
import com.johanrivas.jlearning.Services.interfaces.UserService;
import com.johanrivas.jlearning.auth.service.JWTService;
import com.johanrivas.jlearning.auth.service.JWTServiceImpl;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTService jwtService;

	@Autowired
	private UserService userService;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/v1/login", "POST"));

		this.jwtService = jwtService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "*");

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username != null && password != null) {
			logger.info("Username desde request parameter (form-data): " + username);
			logger.info("Password desde request parameter (form-data): " + password);

		} else {
			User user = null;
			try {

				user = new ObjectMapper().readValue(request.getInputStream(), User.class);

				username = user.getEmail();
				password = user.getPassword();

				logger.info("Username desde request InputStream (raw): " + username);
				logger.info("Password desde request InputStream (raw): " + password);

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		logger.info("******************");

		username = username.trim();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = jwtService.create(authResult);

		response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		// body.put("user", (org.springframework.security.core.userdetails.User)
		// authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s, you have logged in!",
				((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername()));

		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error : username o password wrong!");
		body.put("error", failed.getMessage());

		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}

	// public User formatLoggedUser (Authentication auth) {
	// String username = ((org.springframework.security.core.userdetails.User)
	// auth.getPrincipal()).getUsername();

	// User user = userService.findByEmail(username);
	// user.setCourses(null);
	// user.setRoles(null);
	// }

}
