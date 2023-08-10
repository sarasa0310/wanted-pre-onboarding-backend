package sarasa.wantedinternship.mapper;

import org.mapstruct.Mapper;
import sarasa.wantedinternship.domain.entity.Member;
import sarasa.wantedinternship.dto.request.EmailPasswordDto;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member toMember(EmailPasswordDto dto);

}
