package it.epicode.W7_D5_BE_project.exceptions;



import it.epicode.W7_D5_BE_project.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomizedExceptionHandler {

    private ApiError buildError(String message) {
        ApiError error = new ApiError();
        error.setMessage(message);
        error.setDataErrore(LocalDateTime.now());
        return error;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundExceptionHandler(NotFoundException e) {
        return buildError(e.getMessage());
    }


    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationExceptionHandler(ValidationException e){
        return buildError(e.getMessage());
    }

    @ExceptionHandler(PrenotazioneGiaEsistenteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError prenotazioneGiaEsistenteExceptionHandler(PrenotazioneGiaEsistenteException e){
        return buildError(e.getMessage());
    }

    @ExceptionHandler(PostiEsauritiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError postiEsauritiExceptionHandler(PostiEsauritiException e){
        return buildError(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError accessDeniedHandler(AccessDeniedException e){
        return buildError(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError unauthorizedExceptionHandler(UnauthorizedException e){
        return buildError(e.getMessage());
    }


}
