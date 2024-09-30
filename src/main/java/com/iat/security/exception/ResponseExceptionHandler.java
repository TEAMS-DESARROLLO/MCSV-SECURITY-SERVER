package com.iat.security.exception;


import org.springframework.http.*;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleModelNotFoundException(ModelNotFoundException ex, WebRequest request){

        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        //log.info(ex.getCause().getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<CustomErrorResponse> handleRepositoryException(RepositoryException ex, WebRequest request){
        ex.getMessage();
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<CustomErrorResponse> handleServiceException(ServiceException ex, WebRequest request){
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Desde Spring Boot 3
    /*@ExceptionHandler(ModelNotFoundException.class)
    public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex, WebRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Model Not Found Exception");
        problemDetail.setType(URI.create("/not-found"));
        problemDetail.setProperty("extra1", "extra-value");
        problemDetail.setProperty("extra2", 32);
        return problemDetail;
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ErrorResponse handleModelNotFoundException(ModelNotFoundException ex, WebRequest request){
        return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage())
                .title("Model Not Found")
                .type(URI.create(request.getContextPath()))
                .property("extra1", "extra-value")
                .property("extra2", 32)
                .build();
    }*/    

    @SuppressWarnings("null")
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
             HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = ex.getBindingResult().getFieldErrors().stream().map(
            e -> e.getField().concat(":").concat( e.getDefaultMessage().concat("; ") )
        ).collect(Collectors.joining());

        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false)  );
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST) ;
    }



    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
      RuntimeException exception, 
      WebRequest request
    ){
        String msg = exception.getCause().getMessage(). concat(":").concat(request.getDescription(false));
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false)  );
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CustomErrorResponse> handleConflictException(ConflictException ex, WebRequest request) {
    CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(err, HttpStatus.CONFLICT); // HTTP 409 Conflict
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<CustomErrorResponse> handleAuthenticationFailed(AuthenticationFailedException ex, WebRequest request) {
    CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED); // HTTP 409 Conflict
    }
}
