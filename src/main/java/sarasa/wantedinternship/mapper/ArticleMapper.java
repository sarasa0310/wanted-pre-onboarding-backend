package sarasa.wantedinternship.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sarasa.wantedinternship.domain.entity.Article;
import sarasa.wantedinternship.dto.request.ArticleRequestDto;
import sarasa.wantedinternship.dto.response.ArticleResponseDto;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toEntity(ArticleRequestDto dto);

    @Mapping(source = "id", target = "articleId")
    ArticleResponseDto toDto(Article article);

}
