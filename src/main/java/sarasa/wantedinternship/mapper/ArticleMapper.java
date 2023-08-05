package sarasa.wantedinternship.mapper;

import org.mapstruct.*;
import sarasa.wantedinternship.domain.entity.Article;
import sarasa.wantedinternship.dto.request.ArticleRequest;
import sarasa.wantedinternship.dto.response.ArticleResponse;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toArticle(ArticleRequest dto);

    @Mapping(source = "id", target = "articleId")
    ArticleResponse toResponse(Article article);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ArticleRequest dto, @MappingTarget Article entity);

}
