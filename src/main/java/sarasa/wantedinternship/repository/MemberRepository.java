package sarasa.wantedinternship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sarasa.wantedinternship.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

}
