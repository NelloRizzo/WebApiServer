package corso.java.webapi.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import corso.java.webapi.services.models.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
@Scope("singleton")
public class JwtUtils {
	private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expirationms}")
	private long jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {
		var principal = (UserDetailsImpl) authentication.getPrincipal();
		var now = new Date();
		var exp = new Date(now.getTime() + jwtExpirationMs);
		principal.setExpirationTime(exp);
		var key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.builder() //
				.setSubject(principal.getUsername()) //
				.setIssuedAt(now) //
				.setExpiration(exp) //
				.signWith(key) //
				.compact();
	}

	public String getUsernameFromJwtToken(String token) {
		try {
			var key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e) {
			log.error("Exception retrieving username from token", e);
			throw new RuntimeException(e);
		}
	}

	public boolean validateJwtToken(String token) {
		try {
			var key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

			Jwts.parserBuilder().setSigningKey(key).build().parse(token);
			return true;
		} catch (Exception e) {
			log.error("Exception validating token", e);
		}
		return false;
	}
}
