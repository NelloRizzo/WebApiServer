package corso.java.webapi.repositories.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import corso.java.webapi.entities.blog.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {

}
