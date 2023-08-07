package sarasa.wantedinternship.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sarasa.wantedinternship.domain.entity.Member;
import sarasa.wantedinternship.exception.custom.MemberAlreadyExistsException;
import sarasa.wantedinternship.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@DisplayName("MemberService 단위 테스트")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService sut;

    @Test
    @DisplayName("회원 가입 테스트 - 존재하지 않는 이메일로 회원 가입을 하면, 회원 아이디를 반환한다.")
    void shouldReturnSavedMemberIdOnSignUp() {
        // given
        Member newMember = new Member("test@test.com", "rawPassword");
        String encryptedPassword = "encryptedPassword";

        Member savedMember = new Member("test@test.com", encryptedPassword);
        savedMember.setId(1L);

        given(memberRepository.existsByEmail(anyString()))
                .willReturn(false);
        given(passwordEncoder.encode(anyString()))
                .willReturn(encryptedPassword);
        given(memberRepository.save(any(Member.class)))
                .willReturn(savedMember);

        // when
        Long savedMemberId = sut.signUp(newMember);

        // then
        assertThat(savedMemberId).isNotNull();
        assertThat(savedMemberId).isEqualTo(1L);
        assertThat(newMember.getPassword()).isEqualTo(encryptedPassword);
    }

    @Test
    @DisplayName("회원 가입 예외 테스트 - 동일한 이메일의 회원이 존재할 때 MemberAlreadyExistsException을 던진다.")
    void shouldThrowMemberAlreadyExistsExceptionWhenSameEmailExists() {
        // given
        Member member = new Member("test@test.com", "test1234");

        given(memberRepository.existsByEmail(anyString()))
                .willReturn(true);

        // when & then
        assertThatThrownBy(() -> sut.signUp(member))
                .isInstanceOf(MemberAlreadyExistsException.class)
                .hasMessage("이미 가입된 회원입니다.");
    }

}