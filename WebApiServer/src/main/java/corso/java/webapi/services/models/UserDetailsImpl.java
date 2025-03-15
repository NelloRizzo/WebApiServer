package corso.java.webapi.services.models;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import corso.java.webapi.entities.blog.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private int id;
	private String username;
	private String email;
	@JsonIgnore
	private String password;
	private boolean accountNonLocked = true;
	private boolean accountNonExpired = true;
	private boolean creadentialsNonExpired = true;
	private boolean enabled = true;
	private Date expirationTime;
	private Collection<? extends GrantedAuthority> authorities;

	public static UserDetailsImpl from(User user) {
		var authorities = user.getRoles().stream()
				.map(r -> new SimpleGrantedAuthority(r.getName())).toList();
		return new UserDetailsImpl(
				user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), true, true,
				true, true, new Date(), authorities);
	}
}
