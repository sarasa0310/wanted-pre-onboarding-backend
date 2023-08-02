package sarasa.wantedinternship.mapper;

import org.mapstruct.*;
import sarasa.wantedinternship.domain.entity.Article;
import sarasa.wantedinternship.dto.request.ArticleRequestDto;
import sarasa.wantedinternship.dto.response.ArticleResponseDto;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toArticle(ArticleRequestDto dto);

    @Mapping(source = "id", target = "articleId")
    ArticleResponseDto toResponse(Article article);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ArticleRequestDto dto, @MappingTarget Article entity);

}
