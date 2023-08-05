package sarasa.wantedinternship.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record SignUpDto(
        @Email(message = "@를 포함하여 이메일을 작성해주세요. ex)example@google.com")
        String email,
        @Size(min = 8, message = "비밀번호는 8자 이상으로 작성해주세요.")
        String password
) {
}
