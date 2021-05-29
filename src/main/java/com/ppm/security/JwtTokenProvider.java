package com.ppm.security;

import static com.ppm.security.SecurityConstants.EXPIRATION_TIME;
import static com.ppm.security.SecurityConstants.SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ppm.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
		String userId = Long.toString(user.getId());

		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("id", userId);
		claims.put("userName", user.getUsername());
		claims.put("fullName", user.getFullName());

		return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			log.error("invalid JWT Signature");
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			log.error("JWT token has been Expired");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			log.error("JWT claims String is empty");
		}

		return false;
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String userId = (String) claims.get("id");
		return Long.parseLong(userId);
	}

}
