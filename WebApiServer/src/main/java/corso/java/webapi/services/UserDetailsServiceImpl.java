package corso.java.webapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import corso.java.webapi.repositories.blog.UsersRepository;
import corso.java.webapi.services.exceptions.EntityNotFoundException;
import corso.java.webapi.services.models.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersRepository users;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = users.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
		return UserDetailsImpl.from(user);
	}

}
