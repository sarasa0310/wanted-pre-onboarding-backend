package sarasa.wantedinternship.mapper;

import org.mapstruct.Mapper;
import sarasa.wantedinternship.domain.entity.Member;
import sarasa.wantedinternship.dto.SignUpDto;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member toEntity(SignUpDto dto);

}
