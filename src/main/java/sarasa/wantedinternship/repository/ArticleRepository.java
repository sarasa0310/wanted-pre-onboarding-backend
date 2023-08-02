package sarasa.wantedinternship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sarasa.wantedinternship.domain.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
