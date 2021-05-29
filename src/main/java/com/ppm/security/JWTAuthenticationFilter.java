package com.ppm.security;

import static com.ppm.security.SecurityConstants.HEADER_STRING;
import static com.ppm.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ppm.domain.User;
import com.ppm.service.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider provider;
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = getJWTFromRequest(request);
			if (!ObjectUtils.isEmpty(token) && provider.validateToken(token)) {
				Long userId = provider.getUserIdFromJWT(token);
				User user = userDetailsService.findUserById(userId);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
						null, Collections.emptyList());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
			log.error("Could not set User authentication in Security Context ", e);
		}
		filterChain.doFilter(request, response);
	}

	private String getJWTFromRequest(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
			return token.substring(7, token.length());
		}
		return null;
	}

}
