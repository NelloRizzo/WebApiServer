package corso.java.webapi.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import corso.java.webapi.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	private static final String HEADER = "Authorization";
	private static final String BEARER = "Bearer ";

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserDetailsService detailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			var jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				var username = jwtUtils.getUsernameFromJwtToken(jwt);
				var details = detailService.loadUserByUsername(username);
				var authentication = new UsernamePasswordAuthenticationToken(details, null, 
						details.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.error("Exception authenticating from token", e);
		}
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		var authHeader = request.getHeader(HEADER);
		if (authHeader == null)
			return null;
		if (!authHeader.isBlank() && authHeader.startsWith(BEARER)) {
			return authHeader.substring(BEARER.length());
		}
		return null;
	}
}
