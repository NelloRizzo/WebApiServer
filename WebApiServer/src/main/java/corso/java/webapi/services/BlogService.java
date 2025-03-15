package corso.java.webapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import corso.java.webapi.services.dto.ArticleDto;
import corso.java.webapi.services.dto.CommentDto;

public interface BlogService {
	Optional<ArticleDto> publishArticle(UserDetails user, ArticleDto articleDto);
	Optional<ArticleDto> updateArticle(UserDetails user, ArticleDto articleDto);
	Optional<ArticleDto> deleteArticle(UserDetails user, Integer id);
	Optional<CommentDto> writeComment(UserDetails user, CommentDto commentDto);
	Optional<CommentDto> updateComment(UserDetails user, CommentDto commentDto);
	Optional<CommentDto> deleteComment(UserDetails user, Integer id);
	Optional<ArticleDto> getArticle(Integer id);
	List<ArticleDto> getArticles();
}
