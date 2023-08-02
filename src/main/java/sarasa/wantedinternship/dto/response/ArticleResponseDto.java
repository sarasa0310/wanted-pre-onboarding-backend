package sarasa.wantedinternship.dto.response;

// todo: 사용자 이름 추가

public record ArticleResponseDto(
        Long articleId,
        String title,
        String content
) {
}
