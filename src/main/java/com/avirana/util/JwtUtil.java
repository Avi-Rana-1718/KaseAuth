package com.avirana.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expirationMillis;

	@Value("${jwt.refresh-expiration:604800000}")
	private long refreshExpirationMillis;

	public String generateRefreshToken(String subject) {
		return Jwts.builder()
				.subject(subject)
				.claim("type", "refresh")
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + refreshExpirationMillis))
				.signWith(getSigningKey())
				.compact();
	}

	public boolean isRefreshTokenValid(String token) {
		try {
			String type = extractClaim(token, claims -> claims.get("type", String.class));
			return "refresh".equals(type) && !isTokenExpired(token);
		} catch (JwtException e) {
			return false;
		}
	}

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	public String generateToken(String subject) {
		return generateToken(subject, Map.of());
	}

	public String generateToken(String subject, Map<String, Object> extraClaims) {
		return Jwts.builder()
				.claims(extraClaims)
				.subject(subject)
				.claim("type", "access")
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expirationMillis))
				.signWith(getSigningKey())
				.compact();
	}

	public String extractSubject(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean isTokenValid(String token) {
		try {
			return !isTokenExpired(token);
		} catch (JwtException e) {
			return false;
		}
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
