package com.hm.backend.handler;

import com.hm.backend.dto.ErrorResponse;
import com.hm.backend.exception.BadCredentialsException;
import com.hm.backend.exception.InvalidTokenException;
import com.hm.backend.exception.UserAlreadyExistsException;
import com.hm.backend.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAuthExceptionHandler {

//    @ExceptionHandler(InvalidRequestException.class)
//    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException e){
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(
//                        ErrorResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getMessage()).build()
//                );
//    }

    @ExceptionHandler(UserAlreadyExistsException.class)
   public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getMessage()).build()
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getMessage()).build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getMessage()).build()
                );
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getMessage()).build()
                );
    }
}
