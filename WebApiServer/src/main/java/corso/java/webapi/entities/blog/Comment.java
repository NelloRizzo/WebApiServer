package corso.java.webapi.entities.blog;

import java.time.LocalDateTime;

import corso.java.webapi.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
@Table(name = "comments")
public class Comment extends BaseEntity {
	private String content;
	@ManyToOne
	private User author;
	@ManyToOne
	private Article article;
	
	@Builder(setterPrefix = "with")
	public Comment(int id, LocalDateTime createdAt, String content, User author) {
		super(id, createdAt);
		this.content = content;
		this.author = author;
	}

}
