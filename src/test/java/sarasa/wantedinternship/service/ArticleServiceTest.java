package sarasa.wantedinternship.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sarasa.wantedinternship.domain.entity.Article;
import sarasa.wantedinternship.domain.entity.Member;
import sarasa.wantedinternship.dto.request.ArticleRequest;
import sarasa.wantedinternship.exception.custom.ArticleNotFoundException;
import sarasa.wantedinternship.exception.custom.NoAuthorityException;
import sarasa.wantedinternship.repository.ArticleRepository;
import sarasa.wantedinternship.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ArticleService 단위 테스트")
class ArticleServiceTest {

    @Mock private ArticleRepository articleRepository;
    @Mock private MemberRepository memberRepository;

    @InjectMocks
    private ArticleService sut;

    private Member member1;
    private Member member2;
    private Article article1;
    private Article article2;

    @BeforeEach
    void setUpTestData() {
        member1 = new Member("test@test.com", "test1234");
        member1.setId(1L);
        member2 = new Member("test2@test.com", "test1234");
        member2.setId(2L);
        article1 = new Article("title", "content", member1);
        article1.setId(1L);
        article2 = new Article("title2", "content2", member2);
        article2.setId(2L);
    }

    @Test
    @DisplayName("게시글 생성 테스트 - 회원 식별자를 통해 게시글을 작성한 회원을 찾아서 넣어준 뒤, " +
            "게시글을 저장하고 생성된 게시글의 식별자를 반환한다. ")
    void shouldReturnSavedArticleIdOnCreation() {
        // given
        given(memberRepository.getReferenceById(anyLong()))
                .willReturn(member1);
        given(articleRepository.save(any(Article.class)))
                .willReturn(article1);

        Article newArticle = new Article();

        // when
        Long savedArticleId = sut.createArticle(member1.getId(), newArticle);

        // then
        assertThat(savedArticleId).isNotNull();
        assertThat(savedArticleId).isEqualTo(1L);
        assertThat(savedArticleId).isEqualTo(article1.getId());
    }

    @Test
    @DisplayName("게시글 목록 조회 테스트 - 컨트롤러에서 @PageableDefault 애너테이션이 적용된 Pageable을 넘겨받아, " +
            "페이지네이션이 적용된 게시글 목록을 반환한다.")
    void findArticlesWithPagination() {
        // given
        Pageable pageable = Pageable.unpaged();

        Page<Article> expected = new PageImpl<>(List.of(article1, article2));

        given(articleRepository.findAll(any(Pageable.class)))
                .willReturn(expected);

        // when
        Page<Article> actual = sut.findArticles(pageable);

        // then
        assertThat(actual).isEqualTo(expected);
        assertThat(actual).hasSameSizeAs(expected);
    }

    @Test
    @DisplayName("단일 게시글 조회 테스트 - 존재하는 게시글의 식별자인 경우 게시글을 반환한다.")
    void findOneExistingArticle() {
        // given
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article1));

        // when
        Article findArticle = sut.findOneArticle(article1.getId());

        // then
        assertThat(findArticle).isNotNull();
        assertThat(findArticle).isEqualTo(article1);
        assertThat(findArticle.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("단일 게시글 조회 테스트 - 존재하지 않는 게시글의 식별자를 받은 경우 예외를 던진다.")
    void findNotExistingArticleThenThrowArticleNotFoundException() {
        // given
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> sut.findOneArticle(3L))
                .isInstanceOf(ArticleNotFoundException.class)
                .hasMessage("존재하지 않는 게시글입니다.");
    }

    @Test
    @DisplayName("게시글 수정 테스트 - 정상 케이스(게시글 작성자가 수정 시도)")
    void updateArticleWithSameMemberId() {
        // given
        ArticleRequest requestDto = new ArticleRequest("updated_title", "updated_content");

        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article1));

        Article expected = new Article("updated_title", "updated_content", member1);

        // when
        Article actual = sut.updateArticle(member1.getId(), article1.getId(), requestDto);

        // then
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @Test
    @DisplayName("게시글 수정 테스트 - 비정상 케이스 시 NoAuthorityException을 던진다(게시글 작성자가 아닌 사용자가 수정 시도).")
    void updateArticleWithNotSameMemberIdThenThrowNoAuthorityException() {
        // given
        ArticleRequest requestDto = new ArticleRequest("updated_title", "updated_content");

        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article1));

        // when & then
        assertThatThrownBy(() -> sut.updateArticle(member2.getId(), article1.getId(), requestDto))
                .isInstanceOf(NoAuthorityException.class)
                .hasMessage("게시글 작성자만 수정 및 삭제가 가능합니다.");
    }

    @Test
    @DisplayName("게시글 삭제 테스트 - 정상 케이스(게시글 작성자가 삭제 시도)")
    void deleteArticleWithSameMemberId() {
        // given
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article1));

        // when
        sut.deleteArticle(member1.getId(), article1.getId());

        // then
        verify(articleRepository).findById(article1.getId());
        verify(articleRepository).delete(article1);
    }

    @Test
    @DisplayName("게시글 삭제 테스트 - 비정상 케이스 시 NoAuthorityException을 던진다(게시글 작성자가 아닌 사용자가 삭제 시도).")
    void deleteArticleWithNotSameMemberId() {
        // given
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article1));

        // when & then
        assertThatThrownBy(() -> sut.deleteArticle(member2.getId(), article1.getId()))
                .isInstanceOf(NoAuthorityException.class)
                .hasMessage("게시글 작성자만 수정 및 삭제가 가능합니다.");
    }

}