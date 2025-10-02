package com.example.bankcards.exception;



import com.example.bankcards.security.exception.AuthException;
import com.example.bankcards.util.UtilResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultAdvice {

    @ExceptionHandler(CardException.class)
    public ResponseEntity<UtilResponse> handleCardException(CardException e) {
        UtilResponse exceptionResponse = UtilResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UtilException.class)
    public ResponseEntity<UtilResponse> handleUtilException(UtilException e) {
        UtilResponse exceptionResponse = UtilResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<UtilResponse> handleTransactionException(TransactionException e) {
        UtilResponse exceptionResponse = UtilResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UtilResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        UtilResponse exceptionResponse = UtilResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<UtilResponse> handleConstraintViolationException(ConstraintViolationException e) {
        UtilResponse exceptionResponse = UtilResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<UtilResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        UtilResponse exceptionResponse = UtilResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<UtilResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        UtilResponse exceptionResponse = UtilResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<UtilResponse> handleAuthException(AuthException e) {
        UtilResponse exceptionResponse = UtilResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(403));
    }
}
