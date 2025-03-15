package corso.java.webapi.services.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class CommentDto {
	private Integer id;
	private Integer articleId;
	private String author;
	private String content;
	private LocalDateTime createdAt;
}
