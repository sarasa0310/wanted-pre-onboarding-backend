package sarasa.wantedinternship.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sarasa.wantedinternship.domain.entity.Member;
import sarasa.wantedinternship.dto.request.SignUpDto;
import sarasa.wantedinternship.mapper.MemberMapper;
import sarasa.wantedinternship.service.MemberService;

import java.net.URI;

@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDto dto) {
        Member member = memberMapper.toMember(dto);

        Long savedMemberId = memberService.signUp(member);

        return ResponseEntity.created(
                URI.create("/members/" + savedMemberId))
                .build();
    }

}
