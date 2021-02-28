package com.nareshnj.lms.exception;

public class SameBookBorrowException extends RuntimeException {
    public SameBookBorrowException(String message) {
        super(message);
    }
}
