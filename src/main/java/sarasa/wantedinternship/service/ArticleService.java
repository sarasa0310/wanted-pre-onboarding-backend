package sarasa.wantedinternship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sarasa.wantedinternship.domain.entity.Article;
import sarasa.wantedinternship.dto.request.ArticleRequest;
import sarasa.wantedinternship.exception.custom.ArticleNotFoundException;
import sarasa.wantedinternship.exception.custom.NoAuthorityException;
import sarasa.wantedinternship.repository.ArticleRepository;
import sarasa.wantedinternship.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @CacheEvict(value = "article", allEntries = true)
    public Long createArticle(Long memberId, Article article) {
        article.setMember(memberRepository.getReferenceById(memberId));

        return articleRepository.save(article).getId();
    }

    @Cacheable("article")
    @Transactional(readOnly = true)
    public Page<Article> findArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Cacheable("article")
    @Transactional(readOnly = true)
    public Article findOneArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("존재하지 않는 게시글입니다."));
    }

    @CacheEvict(value = "article", allEntries = true)
    public Article updateArticle(Long memberId, Long articleId,
                                 ArticleRequest dto) {
        Article article = findOneArticle(articleId);

        validateMemberIdForUpdateAndDelete(memberId, article);

        Optional.ofNullable(dto.title())
                .ifPresent(article::setTitle);
        Optional.ofNullable(dto.content())
                .ifPresent(article::setContent);

        return article;
    }

    @CacheEvict(value = "article", allEntries = true)
    public void deleteArticle(Long memberId, Long articleId) {
        Article article = findOneArticle(articleId);

        validateMemberIdForUpdateAndDelete(memberId, article);

        articleRepository.delete(article);
    }

    private void validateMemberIdForUpdateAndDelete(Long memberId, Article article) {
        if (!article.getMember().getId().equals(memberId)) {
            throw new NoAuthorityException("게시글 작성자만 수정 및 삭제가 가능합니다.");
        }
    }

}
