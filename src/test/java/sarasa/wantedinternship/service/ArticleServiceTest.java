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
import sarasa.wantedinternship.dto.request.ArticleRequestDto;
import sarasa.wantedinternship.exception.custom.ArticleNotFoundException;
import sarasa.wantedinternship.exception.custom.NoAuthorityException;
import sarasa.wantedinternship.mapper.ArticleMapper;
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

    @Mock private ArticleMapper articleMapper;
    @Mock private ArticleRepository articleRepository;
    @Mock private MemberRepository memberRepository;

    @InjectMocks
    private ArticleService sut;

    private Member member;
    private Member member2;
    private Article article;
    private Article article2;

    @BeforeEach
    void setUpTestData() {
        member = new Member("test@test.com", "test1234");
        member.setId(1L);
        member2 = new Member("test2@test.com", "test1234");
        member2.setId(2L);
        article = new Article("title", "content", member);
        article.setId(1L);
        article2 = new Article("title2", "content2", member2);
        article2.setId(2L);
    }

    @Test
    @DisplayName("게시글 생성 정상 동작 테스트")
    void shouldReturn_SavedArticleId_OnCreation() {
        // given
        given(memberRepository.getReferenceById(anyLong()))
                .willReturn(member);
        given(articleRepository.save(any(Article.class)))
                .willReturn(article);

        // when
        Article newArticle = new Article();
        Long savedArticleId = sut.createArticle(member.getId(), newArticle);

        // then
        assertThat(savedArticleId).isNotNull();
        assertThat(savedArticleId).isEqualTo(article.getId());

        verify(memberRepository).getReferenceById(member.getId());
        verify(articleRepository).save(newArticle);
    }

    @Test
    @DisplayName("게시글 목록 조회 테스트")
    void findArticles() {
        // given
        Pageable pageable = Pageable.unpaged();

        Page<Article> expected =
                new PageImpl<>(List.of(article, article2));

        given(articleRepository.findAll(any(Pageable.class)))
                .willReturn(expected);

        // when
        Page<Article> actual = sut.findArticles(pageable);

        // then
        assertThat(actual).isEqualTo(expected);
        assertThat(actual).hasSameSizeAs(expected);

        verify(articleRepository).findAll(pageable);
    }

    @Test
    @DisplayName("존재하는 게시글을 찾을 때, 정상적으로 게시글을 반환하는지 테스트")
    void findOneArticle() {
        // given
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article));

        // when
        Article findArticle = sut.findOneArticle(article.getId());

        // then
        assertThat(findArticle).isNotNull();
        assertThat(findArticle).isEqualTo(article);
        assertThat(findArticle.getId()).isEqualTo(1L);

        verify(articleRepository).findById(article.getId());
    }

    @Test
    @DisplayName("존재하지 않는 게시글을 찾을 때, 예외를 던지는지 테스트")
    void findNotExistingArticle_thenThrow_ArticleNotFoundException() {
        // given
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> sut.findOneArticle(3L))
                .isInstanceOf(ArticleNotFoundException.class)
                .hasMessage("존재하지 않는 게시글입니다.");

        verify(articleRepository).findById(3L);
    }

    @Test
    @DisplayName("게시글 수정 테스트 - 정상 케이스(게시글 작성자가 수정 시도)")
    void updateArticle() {
        // given
        ArticleRequestDto requestDto = new ArticleRequestDto("updated_title", "updated_content");

        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article));
        doAnswer(invocation -> {
            ArticleRequestDto dto = invocation.getArgument(0);
            Article article = invocation.getArgument(1);
            article.setTitle(dto.title());
            article.setContent(dto.content());
            return null;
        }).when(articleMapper).updateFromDto(requestDto, article);

        Article expected = new Article("updated_title", "updated_content", member);

        // when
        Article actual = sut.updateArticle(member.getId(), article.getId(), requestDto);

        // then
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getContent()).isEqualTo(expected.getContent());

        verify(articleRepository).findById(member.getId());
        verify(articleMapper).updateFromDto(requestDto, article);
    }

    @Test
    @DisplayName("게시글 수정 테스트 - 비정상 케이스(게시글 작성자가 아닌 사용자가 수정 시도)")
    void givenNotSameAuthor_WhenUpdateArticle_ThenThrowNoAuthorityException() {
        // given
        ArticleRequestDto requestDto = new ArticleRequestDto("updated_title", "updated_content");

        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article));

        // when & then
        assertThatThrownBy(() -> sut.updateArticle(member2.getId(), article.getId(), requestDto))
                .isInstanceOf(NoAuthorityException.class)
                .hasMessage("게시글 작성자만 수정 및 삭제가 가능합니다.");

        verify(articleRepository).findById(member.getId());
        verifyNoInteractions(articleMapper);
    }

    @Test
    @DisplayName("게시글 정상 삭제 테스트")
    void deleteArticle() {
        // given
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(article));

        // when
        sut.deleteArticle(member.getId(), article.getId());

        // then
        verify(articleRepository).findById(article.getId());
        verify(articleRepository).delete(article);
    }

}