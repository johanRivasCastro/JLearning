package com.johanrivas.jlearning.auth.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johanrivas.jlearning.models.Entities.Role;
import com.johanrivas.jlearning.models.Entities.User;
import com.johanrivas.jlearning.Services.interfaces.RoleService;
import com.johanrivas.jlearning.Services.interfaces.UserService;
import com.johanrivas.jlearning.auth.SimpleGrantedAuthorityMixin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTServiceImpl implements JWTService {
	public static final String SECRET = Base64Utils.encodeToString("Alguna.Clave.Secreta.123456".getBytes());

	public static final long EXPIRATION_DATE = 14000000L;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Override
	public String create(Authentication auth) throws IOException {

		String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

		// ArrayList<Role> roles = (ArrayList<Role>) roleService.findAll();
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

		Claims claims = Jwts.claims();
		User user = userService.findByEmail(username);
		user.setCourses(null);
		// user.setRoles(null);
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		claims.put("user", new ObjectMapper().writeValueAsString(user));

		String token = Jwts.builder().setClaims(claims).setSubject(username)
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)).compact();

		return token;
	}

	@Override
	public boolean validate(String token) {
		try {
			getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public Claims getClaims(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(resolve(token)).getBody();
		return claims;
	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("authorities");
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
						.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
		return authorities;
	}

	@Override
	public String resolve(String token) {
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			return token.replace(TOKEN_PREFIX, "");
		}
		return null;
	}

}
