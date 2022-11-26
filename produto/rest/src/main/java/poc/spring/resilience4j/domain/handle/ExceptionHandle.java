package poc.spring.resilience4j.domain.handle;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import poc.spring.resilience4j.domain.exception.CategoriaException;
import poc.spring.resilience4j.domain.exception.CategoriaNotFoundException;
import poc.spring.resilience4j.domain.exception.ProdutoException;
import poc.spring.resilience4j.domain.exception.ProdutoNotFoundException;
import poc.spring.resilience4j.domain.handle.dto.ErroDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExceptionHandle {
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDto> handleValidacao(MethodArgumentNotValidException exception) {
        List<ErroDto> errosDto = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(e -> errosDto.add(new ErroDto(getMensagemErro(e), LocalDateTime.now())));
        return errosDto;
    }
    @ExceptionHandler({ProdutoException.class, CategoriaException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroDto handleUnprocessableEntity(Exception exception) {
        return new ErroDto(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({ProdutoNotFoundException.class, CategoriaNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroDto handleNotFound(Exception exception) {
        return new ErroDto(exception.getMessage(), LocalDateTime.now());
    }
    private String getMensagemErro(FieldError e) {
        return messageSource.getMessage(e, LocaleContextHolder.getLocale());
    }
}
