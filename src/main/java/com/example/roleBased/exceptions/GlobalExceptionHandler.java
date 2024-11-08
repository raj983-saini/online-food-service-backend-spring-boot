//package com.example.roleBased.exceptions;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ProblemDetail;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AccountStatusException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.resource.NoResourceFoundException;
//
//@RestControllerAdvice
//@Component("globalExceptionHandler2")
//public class GlobalExceptionHandler {
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
//        return new ResponseEntity<>("Access Denied: " + ex.getMessage(), HttpStatus.FORBIDDEN);
//    }
//    @ExceptionHandler(Exception.class)
//    public ProblemDetail handleSecurityException(Exception exception) {
//        ProblemDetail errorDetail = null;
//
//        exception.printStackTrace();
//
//        if (exception instanceof BadCredentialsException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
//            errorDetail.setProperty("description", "The username or password is incorrect");
//
//            return errorDetail;
//        }
//
//        if (exception instanceof AccountStatusException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "The account is locked");
//        }
//
//        if (exception instanceof AccessDeniedException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "You are not authorized to access this resource");
//        }
//
//        if (exception instanceof SignatureException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "The JWT signature is invalid");
//        }
//
//        if (exception instanceof ExpiredJwtException) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//            errorDetail.setProperty("description", "The JWT token has expired");
//        }
//
//        if (errorDetail == null) {
//            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
//            errorDetail.setProperty("description", "Unknown internal server error.");
//        }
//
//
//
//
//        return errorDetail;
//    }
//    @ExceptionHandler(NoResourceFoundException.class)
//    public ResponseEntity<Object> handleNoResourceFound(NoResourceFoundException ex) {
//        // Return a custom response for missing resources
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("Resource not found: " + ex.getMessage());
//    }
//}
