package corso.java.webapi.services.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class CommentDto {
	private int id;
	private int articleId;
	private String author;
	private String content;
	private LocalDateTime createdAt;
}
