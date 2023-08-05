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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
    @DisplayName("회원 가입 정상 동작 테스트")
    void shouldReturn_SavedMemberId_OnSignUp() {
        // given
        Member member = new Member("test@test.com", "rawPassword");
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
        Long savedMemberId = sut.signUp(member);

        // then
        assertThat(savedMemberId).isNotNull();
        assertThat(savedMemberId).isEqualTo(1L);
        assertThat(member.getPassword()).isEqualTo(encryptedPassword);

        verify(memberRepository).existsByEmail(member.getEmail());
        verify(passwordEncoder).encode("rawPassword");
        verify(memberRepository).save(member);
    }

    @Test
    @DisplayName("동일한 이메일의 회원이 존재할 때 예외를 던지는지 테스트")
    void shouldThrow_MemberAlreadyExistsException_WhenSameEmailExists() {
        // given
        Member member = new Member("test@test.com", "test1234");

        given(memberRepository.existsByEmail(anyString()))
                .willReturn(true);

        // when & then
        assertThatThrownBy(() -> sut.signUp(member))
                .isInstanceOf(MemberAlreadyExistsException.class)
                .hasMessage("이미 가입된 회원입니다.");

        verify(memberRepository).existsByEmail(member.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(memberRepository, never()).save(any(Member.class));
    }

}