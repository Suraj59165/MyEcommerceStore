package com.store.exceptions;

import com.store.payloads.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Object>> fieldsExceptions(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        Map<String, Object> res = new HashMap<String, Object>();
        errors.stream().forEach(ObjectError -> {
            String message = ObjectError.getDefaultMessage();
            String fieldError = ((FieldError) ObjectError).getField();
            res.put(fieldError, message);
        });
        return new ResponseEntity<Map<String, Object>>(res, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> responseNotFoundExceptionHandler(ResourceNotFoundException exception) {
        return new ResponseEntity<ApiResponseMessage>(
                ApiResponseMessage.builder().message(exception.getMessage()).success(true).build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> badApiRequest(BadApiRequest exception) {
        return new ResponseEntity<ApiResponseMessage>(
                ApiResponseMessage.builder().message(exception.getMessage()).success(true).build(),
                HttpStatus.NOT_ACCEPTABLE);
    }

}
