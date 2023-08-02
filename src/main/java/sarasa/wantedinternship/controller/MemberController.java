package sarasa.wantedinternship.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sarasa.wantedinternship.domain.entity.Member;
import sarasa.wantedinternship.dto.SignUpDto;
import sarasa.wantedinternship.mapper.MemberMapper;
import sarasa.wantedinternship.repository.MemberRepository;

import java.net.URI;

@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;

    @PostMapping("/members")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDto dto) {
        Member member = memberMapper.toEntity(dto);

        Member savedMember = memberRepository.save(member);

        return ResponseEntity.created(
                URI.create("/members/" + savedMember.getId()))
                .body(savedMember);
    }

}
