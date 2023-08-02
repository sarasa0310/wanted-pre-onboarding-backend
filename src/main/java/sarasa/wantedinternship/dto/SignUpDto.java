package sarasa.wantedinternship.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record SignUpDto(
        @Email String email,
        @Size(min = 8) String password
) {
}