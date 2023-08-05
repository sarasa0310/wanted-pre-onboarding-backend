package sarasa.wantedinternship.dto.response;

public record CustomFieldError(
        String field,
        Object rejectedValue,
        String reason
) {
}
