package com.scaffolding.optimization.api.validator;


import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper> handleValidationException(MethodArgumentNotValidException validException) {

        ResponseWrapper response = new ResponseWrapper();
        response.setSuccessful(false);

        validException.getBindingResult()
                .getFieldErrors()
                .forEach(error -> response.addError(error.getField(), error.getDefaultMessage()));

        response.setMessage("Validation failed");
        return ResponseEntity.badRequest().body(response);
    }

    //for server error handling
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleException(Exception exception) {
        ResponseWrapper response = new ResponseWrapper();
        response.setSuccessful(false);
        response.setMessage("Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    //for null pointer exception handling
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseWrapper> handleNullPointerException(NullPointerException exception) {
        ResponseWrapper response = new ResponseWrapper();
        response.setSuccessful(false);
        response.setMessage("Null pointer exception");
        response.addError("error", "Null pointer exception");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    //for illegal argument exception handling
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper> handleIllegalArgumentException(IllegalArgumentException exception) {
        ResponseWrapper response = new ResponseWrapper();
        response.setSuccessful(false);
        response.setMessage("Illegal argument exception");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
