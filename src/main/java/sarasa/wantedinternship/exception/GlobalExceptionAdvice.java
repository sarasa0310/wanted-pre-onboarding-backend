package sarasa.wantedinternship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sarasa.wantedinternship.dto.response.CustomFieldError;
import sarasa.wantedinternship.dto.response.ExceptionResponseDto;
import sarasa.wantedinternship.exception.custom.ArticleNotFoundException;
import sarasa.wantedinternship.exception.custom.MemberAlreadyExistsException;
import sarasa.wantedinternship.exception.custom.NoAuthorityException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<CustomFieldError> errors =
                fieldErrors.stream()
                        .map(error -> new CustomFieldError(
                                error.getField(),
                                error.getRejectedValue(),
                                error.getDefaultMessage()))
                        .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponseDto handleMemberAlreadyExistsException(
            MemberAlreadyExistsException e) {
        return new ExceptionResponseDto(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponseDto handleArticleNotFoundException(
            ArticleNotFoundException e) {
        return new ExceptionResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponseDto handleNoAuthorityException(
            NoAuthorityException e) {
        return new ExceptionResponseDto(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

}
