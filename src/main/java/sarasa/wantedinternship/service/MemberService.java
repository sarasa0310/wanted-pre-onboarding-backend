package sarasa.wantedinternship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sarasa.wantedinternship.domain.entity.Member;
import sarasa.wantedinternship.exception.custom.MemberAlreadyExistsException;
import sarasa.wantedinternship.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signUp(Member member) {
        validateAlreadyExistsMember(member);

        member.encryptPassword(passwordEncoder, member.getPassword());

        return memberRepository.save(member).getId();
    }

    private void validateAlreadyExistsMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new MemberAlreadyExistsException("이미 가입된 회원입니다.");
        }
    }

}
