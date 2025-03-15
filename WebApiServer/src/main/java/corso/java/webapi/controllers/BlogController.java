package corso.java.webapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import corso.java.webapi.controllers.models.ArticleModel;
import corso.java.webapi.controllers.models.CommentModel;
import corso.java.webapi.controllers.responses.ApiError;
import corso.java.webapi.controllers.responses.ApplicationResponse;
import corso.java.webapi.services.BlogService;
import corso.java.webapi.services.dto.ArticleDto;
import corso.java.webapi.services.dto.CommentDto;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
	@Autowired
	private BlogService service;

	@GetMapping()
	public ResponseEntity<ApplicationResponse<List<ArticleDto>>> getArticles() {
		try {
			return ResponseEntity.ok(new ApplicationResponse<List<ArticleDto>>(service.getArticles()));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApplicationResponse<List<ArticleDto>>(new ApiError(e.getMessage())));
		}
	}

	@PostMapping("publish")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApplicationResponse<Optional<ArticleDto>>> publishArticle(@RequestBody ArticleModel model) {
		try {
			var user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			var articleDto = service.publishArticle(user, ArticleDto.builder() //
					.withAuthor(user.getUsername()) //
					.withContent(model.getContent()) //
					.withTitle(model.getTitle()) //
					.build());
			return ResponseEntity.ok(new ApplicationResponse<Optional<ArticleDto>>(articleDto));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApplicationResponse<Optional<ArticleDto>>(new ApiError(e.getMessage())));
		}
	}

	@PostMapping("comment/{articleId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApplicationResponse<Optional<CommentDto>>> writeComment(@PathVariable Integer articleId, @RequestBody CommentModel model) {
		try {
			var user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			var articleDto = service.writeComment(user, CommentDto.builder() //
					.withAuthor(user.getUsername()) //
					.withContent(model.getContent()) //
					.build());
			return ResponseEntity.ok(new ApplicationResponse<Optional<CommentDto>>(articleDto));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApplicationResponse<Optional<CommentDto>>(new ApiError(e.getMessage())));
		}
	}

}
