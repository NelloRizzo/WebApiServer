package corso.java.webapi.entities.blog;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import corso.java.webapi.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Builder

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {
	@Column(length = 80, nullable = false)
	private String title;
	@Column(length = 2048, nullable = false)
	private String content;
	@ManyToOne
	private User author;
	@OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
	private final Set<Comment> comments = new HashSet<Comment>();

	@Builder(setterPrefix = "with")
	public Article(int id, LocalDateTime createdAt, String title, String content, User author) {
		super(id, createdAt);
		this.title = title;
		this.content = content;
		this.author = author;
	}

}
