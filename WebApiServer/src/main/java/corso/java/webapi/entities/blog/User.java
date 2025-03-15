package corso.java.webapi.entities.blog;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import corso.java.webapi.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User extends BaseEntity {
	@Column(length = 80, nullable = false, unique = true)
	private String email;
	@Column(length = 255, nullable = false)
	private String password;
	@Column(length = 25, nullable = false, unique = true)
	private String username;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();

	@Builder(setterPrefix = "with")
	public User(int id, LocalDateTime createdAt, String email, String password, String username) {
		super(id, createdAt);
		this.email = email;
		this.password = password;
		this.username = username;
	}

}
