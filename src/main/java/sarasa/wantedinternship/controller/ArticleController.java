package sarasa.wantedinternship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sarasa.wantedinternship.domain.entity.Article;
import sarasa.wantedinternship.dto.request.ArticleRequest;
import sarasa.wantedinternship.dto.response.ArticleResponse;
import sarasa.wantedinternship.mapper.ArticleMapper;
import sarasa.wantedinternship.service.ArticleService;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleMapper articleMapper;
    private final ArticleService articleService;

    @PostMapping("/articles")
    public ResponseEntity<?> createArticle(@AuthenticationPrincipal Long memberId,
                                           @RequestBody ArticleRequest dto) {
        Article article = articleMapper.toArticle(dto);

        Long createdArticleId = articleService.createArticle(memberId, article);

        return ResponseEntity.created(
                URI.create("/articles/" + createdArticleId)).build();
    }

    @GetMapping("/articles")
    public ResponseEntity<?> findArticles(@PageableDefault(sort = "id", direction = DESC) Pageable pageable) {
        Page<Article> articles = articleService.findArticles(pageable);

        Page<ArticleResponse> responses = articles.map(articleMapper::toResponse);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<?> findOneArticle(@PathVariable Long articleId) {
        Article article = articleService.findOneArticle(articleId);

        ArticleResponse response = articleMapper.toResponse(article);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/articles/{articleId}")
    public ResponseEntity<?> updateArticle(@AuthenticationPrincipal Long memberId,
                                           @PathVariable Long articleId,
                                           @RequestBody ArticleRequest dto) {
        Article updatedArticle = articleService.updateArticle(memberId, articleId, dto);

        ArticleResponse response = articleMapper.toResponse(updatedArticle);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<?> deleteArticle(@AuthenticationPrincipal Long memberId,
                                           @PathVariable Long articleId) {
        articleService.deleteArticle(memberId, articleId);

        return ResponseEntity.noContent().build();
    }

}
