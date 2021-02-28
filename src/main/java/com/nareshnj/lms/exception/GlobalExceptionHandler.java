package com.nareshnj.lms.exception;

import com.nareshnj.lms.pojo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LimitExceedException.class)
    public ResponseEntity<Response> limitExceed(LimitExceedException limitExceedException) {
        Response response = new Response();
        response.setStatus("ERROR");
        response.setMessage(limitExceedException.getMessage());
        return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<Response> bookUnavailable(BookUnavailableException bookUnavailableException) {
        Response response = new Response();
        response.setStatus("ERROR");
        response.setMessage(bookUnavailableException.getMessage());
        return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SameBookBorrowException.class)
    public ResponseEntity<Response> sameBookBorrow(SameBookBorrowException sameBookBorrowException) {
        Response response = new Response();
        response.setStatus("ERROR");
        response.setMessage(sameBookBorrowException.getMessage());
        return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
    }


}
