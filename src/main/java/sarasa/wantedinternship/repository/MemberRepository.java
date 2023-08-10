package sarasa.wantedinternship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sarasa.wantedinternship.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

}
