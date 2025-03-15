package corso.java.webapi.repositories.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import corso.java.webapi.entities.blog.Article;

public interface ArticlesRepository extends JpaRepository<Article, Integer>{

}
