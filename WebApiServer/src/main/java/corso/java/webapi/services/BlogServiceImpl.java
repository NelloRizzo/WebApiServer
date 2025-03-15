package corso.java.webapi.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import corso.java.webapi.entities.blog.Article;
import corso.java.webapi.entities.blog.Comment;
import corso.java.webapi.repositories.blog.ArticlesRepository;
import corso.java.webapi.repositories.blog.CommentsRepository;
import corso.java.webapi.repositories.blog.UsersRepository;
import corso.java.webapi.services.dto.ArticleDto;
import corso.java.webapi.services.dto.CommentDto;
import corso.java.webapi.services.exceptions.EntityNotFoundException;
import corso.java.webapi.services.exceptions.OperationUnauthorizedException;
import corso.java.webapi.services.exceptions.ServiceException;
import corso.java.webapi.servicesexceptions.UserNotFoundException;

@Service
public class BlogServiceImpl implements BlogService {
	private final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);

	@Autowired
	private ArticlesRepository articlesRepository;
	@Autowired
	private CommentsRepository commentsRepository;
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public Optional<ArticleDto> publishArticle(UserDetails user, ArticleDto articleDto) {
		try {
			var u = usersRepository.findByUsername(user.getUsername())
					.orElseThrow(() -> new UserNotFoundException(user.getUsername()));
			var article = Article.builder() //
					.withTitle(articleDto.getTitle()) //
					.withContent(articleDto.getContent()) //
					.withAuthor(u) //
					.withCreatedAt(LocalDateTime.now()) //
					.build();
			var a = articlesRepository.save(article);
			return Optional.of(ArticleDto.builder() //
					.withId(a.getId()) //
					.withTitle(a.getTitle()) //
					.withContent(a.getContent()) //
					.withAuthor(a.getAuthor().getUsername()) //
					.withCreatedAt(a.getCreatedAt()) //
					.build());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Exception publishing article", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<CommentDto> writeComment(UserDetails user, CommentDto commentDto) {
		try {
			var u = usersRepository.findByUsername(user.getUsername())
					.orElseThrow(() -> new UserNotFoundException(user.getUsername()));
			var c = commentsRepository.save(Comment.builder() //
					.withContent(commentDto.getContent()) //
					.withAuthor(u) //
					.withCreatedAt(LocalDateTime.now()) //
					.build());
			return Optional.of(CommentDto.builder() //
					.withId(c.getId()) //
					.withContent(c.getContent()) //
					.withAuthor(c.getAuthor().getUsername()) //
					.withCreatedAt(c.getCreatedAt()) //
					.build());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Exception publishing comment", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<ArticleDto> getArticle(Integer id) {
		try {
			var article = articlesRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Article not found", id.toString()));
			return Optional.of(ArticleDto.builder() //
					.withId(article.getId()) //
					.withTitle(article.getTitle()) //
					.withContent(article.getContent()) //
					.withAuthor(article.getAuthor().getUsername()) //
					.withCreatedAt(article.getCreatedAt()) //
					.withComments(article.getComments().stream().map(c -> CommentDto.builder() //
							.withId(c.getId()) //
							.withContent(c.getContent()) //
							.withAuthor(c.getAuthor().getUsername()) //
							.withCreatedAt(c.getCreatedAt()) //
							.build() //
					).toList()) //
					.build());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Exception getting article", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ArticleDto> getArticles() {
		try {
			var articles = articlesRepository.findAll();
			return articles.stream().map(article -> ArticleDto.builder() //
					.withId(article.getId()) //
					.withTitle(article.getTitle()) //
					.withContent(article.getContent()) //
					.withAuthor(article.getAuthor().getUsername()) //
					.withCreatedAt(article.getCreatedAt()) //
					.withComments(article.getComments().stream().map(c -> CommentDto.builder() //
							.withId(c.getId()) //
							.withContent(c.getContent()) //
							.withAuthor(c.getAuthor().getUsername()) //
							.withCreatedAt(c.getCreatedAt()) //
							.build() //
					).toList()) //
					.build()) //
					.toList();
		} catch (Exception e) {
			log.error("Exception getting articles", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<ArticleDto> updateArticle(UserDetails user, ArticleDto articleDto) {
		try {
			var article = articlesRepository.findById(articleDto.getId()).orElseThrow(() -> new EntityNotFoundException("Article not found", articleDto.getId().toString()));
			if (!user.getUsername().equals(articleDto.getAuthor())) {
				throw new OperationUnauthorizedException("User not allowed to update article");
			}
			article.setTitle(articleDto.getTitle());
			article.setContent(articleDto.getContent());
			article.setCreatedAt(LocalDateTime.now()); //
			var a = articlesRepository.save(article);
			return Optional.of(ArticleDto.builder() //
					.withId(a.getId()) //
					.withTitle(a.getTitle()) //
					.withContent(a.getContent()) //
					.withAuthor(a.getAuthor().getUsername()) //
					.withCreatedAt(a.getCreatedAt()) //
					.build());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Exception updating article", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<ArticleDto> deleteArticle(UserDetails user, Integer id) {
		try {
			var a = articlesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found", id.toString()));
			if (!user.getUsername().equals(a.getAuthor().getUsername())) {
				throw new OperationUnauthorizedException("User not allowed to delete article");
			}
			articlesRepository.delete(a);
			return Optional.of(ArticleDto.builder() //
					.withId(a.getId()) //
					.withTitle(a.getTitle()) //
					.withContent(a.getContent()) //
					.withAuthor(a.getAuthor().getUsername()) //
					.withCreatedAt(a.getCreatedAt()) //
					.build());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Exception deleting article", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<CommentDto> updateComment(UserDetails user, CommentDto commentDto) {
		try {
			var c = commentsRepository.findById(commentDto.getId()).orElseThrow(() -> new EntityNotFoundException("Comment not found", commentDto.getId().toString()));
			if (c.getAuthor().getUsername().equals(user.getUsername())) {
				throw new OperationUnauthorizedException("User not allowed to update comment");
			}
			c.setContent(commentDto.getContent());
			c.setCreatedAt(LocalDateTime.now());
			c = commentsRepository.save(c);
			return Optional.of(CommentDto.builder() //
					.withId(c.getId()) //
					.withContent(c.getContent()) //
					.withAuthor(c.getAuthor().getUsername()) //
					.withCreatedAt(c.getCreatedAt()) //
					.build());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Exception updating comment", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Optional<CommentDto> deleteComment(UserDetails user, Integer id) {
		try {
			var c = commentsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found", id.toString()));
			if (c.getAuthor().getUsername().equals(user.getUsername())) {
				throw new OperationUnauthorizedException("User not allowed to update comment");
			}
			commentsRepository.delete(c);
			return Optional.of(CommentDto.builder() //
					.withId(c.getId()) //
					.withContent(c.getContent()) //
					.withAuthor(c.getAuthor().getUsername()) //
					.withCreatedAt(c.getCreatedAt()) //
					.build());
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Exception deleting comment", e);
			throw new ServiceException(e);
		}
	}

}
