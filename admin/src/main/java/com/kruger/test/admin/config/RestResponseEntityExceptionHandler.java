package com.kruger.test.admin.config;

import com.kruger.test.admin.rest.model.Response;
import com.kruger.test.admin.util.execption.BadRequestsException;
import com.kruger.test.admin.util.execption.ObjectNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ObjectNotFoundException.class })
    public ResponseEntity<Response<Object>> handleObjectNotFOund(Exception ex, WebRequest request) {

        return new ResponseEntity<>(new Response<>(false, ex.getMessage()), HttpStatus.OK);
    }

    // @ExceptionHandler({ ErrorRequestsException.class })
    // public ResponseEntity<Response<Object>> handleObjectErrorRequests(Exception
    // ex, WebRequest request) {
    // Response<Object> response = new HashMap<>();
    // response.put("OK", false);
    // response.put("message", ex.getMessage());
    // return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    @ExceptionHandler({ BadRequestsException.class })
    public ResponseEntity<Response<Object>> handleObjectBabRequests(Exception ex, WebRequest request) {

        return new ResponseEntity<>(new Response<>(false, ex.getMessage()), HttpStatus.OK);
    }

    // @ExceptionHandler({ HttpMessageNotReadableException.class })
    // public ResponseEntity<Response<Object>> handleObjectBabRequestsHttp(Exception
    // ex, WebRequest request) {
    // Response<Object> response = new HashMap<>();
    // response.put("OK", false);
    // response.put("message", "No se encontro cuerpo de la solicitud.");
    // return new ResponseEntity<>(response, HttpStatus.OK);
    // }
}