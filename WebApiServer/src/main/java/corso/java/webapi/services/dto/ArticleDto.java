package corso.java.webapi.services.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class ArticleDto {
	private Integer id;
	private LocalDateTime createdAt;
	private String title;
	private String content;
	private String author;
	@Builder.Default
	private List<CommentDto> comments = new ArrayList<CommentDto>();
}
