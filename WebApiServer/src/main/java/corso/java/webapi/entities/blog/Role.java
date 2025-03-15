package corso.java.webapi.entities.blog;

import java.util.HashSet;
import java.util.Set;

import corso.java.webapi.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
	@Column(length = 12, nullable = false, unique = true)
	private String name;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	public final Set<User> users = new HashSet<User>();
}
