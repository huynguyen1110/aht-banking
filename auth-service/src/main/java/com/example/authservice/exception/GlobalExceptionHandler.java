package com.example.authservice.exception;

import com.example.authservice.dto.Error;
import com.example.authservice.utils.Constants;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleMismatchParametersType(MethodArgumentTypeMismatchException exception) {
        Error error = new Error(Constants.ERROR_CODE.INVALID_PARAMETER_TYPE,
                "Wrong in method arguments.",
                "The required type of " +
                        exception.getPropertyName() +
                        " is " +
                        Objects.requireNonNull(exception.getRequiredType()).getSimpleName());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleParameterNotValid(MethodArgumentNotValidException exception) {
        Error error = new Error(
                Constants.ERROR_CODE.PARAMETER_NOT_VALID,
                "Wrong in parameter data.",
                Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleUnknownException(Exception exception) {
        Error error = new Error(
                Constants.ERROR_CODE.UNKNOWN_EXCEPTION,
                null,
                null);

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolation(ConstraintViolationException exception) {
        Error error = new Error(
                Constants.ERROR_CODE.PARAMETER_NOT_VALID,
                "Wrong in parameter data.",
                exception.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> handleConstraintViolation(MissingServletRequestParameterException exception) {
        Error error = new Error(
                Constants.ERROR_CODE.PARAMETER_NOT_VALID,
                "Wrong in parameter data.",
                exception.getParameterName() + " can not be blank.");

        return ResponseEntity.badRequest().body(error);
    }
}
