package sarasa.wantedinternship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sarasa.wantedinternship.domain.entity.Article;
import sarasa.wantedinternship.dto.ArticleUpdateDto;
import sarasa.wantedinternship.exception.ArticleNotFoundException;
import sarasa.wantedinternship.exception.NoAuthorityException;
import sarasa.wantedinternship.repository.ArticleRepository;
import sarasa.wantedinternship.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public Long createArticle(Long memberId, Article article) {
        article.setMember(memberRepository.getReferenceById(memberId));

        Article savedArticle = articleRepository.save(article);

        return savedArticle.getId();
    }

    @Transactional(readOnly = true)
    public Page<Article> findArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Article findOneArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("존재하지 않는 게시글입니다."));
    }

    public Article updateArticle(Long memberId, Long articleId,
                                 ArticleUpdateDto dto) {
        Article article = findOneArticle(articleId);

        validateAuthor(memberId, article);

        Optional.ofNullable(dto.title())
                .ifPresent(article::setTitle);
        Optional.ofNullable(dto.content())
                .ifPresent(article::setContent);

        return article;
    }

    public void deleteArticle(Long memberId, Long articleId) {
        Article article = findOneArticle(articleId);

        validateAuthor(memberId, article);

        articleRepository.delete(article);
    }

    private void validateAuthor(Long memberId, Article article) {
        if (!article.getMember().getId().equals(memberId)) {
            throw new NoAuthorityException("게시글 작성자만 수정 및 삭제가 가능합니다.");
        }
    }

}
