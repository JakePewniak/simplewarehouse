package com.jacek.net.simplewarehouse.controller.exceptionhandling;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jacek.net.simplewarehouse.dtos.errors.ErrorResultDto;

/**
 * @author Jacek Niepsuj
 *
 * This class handles only Exception class but generally we can generate checked exception in more places in code
 * and catch them here with proper methods
 *
 */
@ControllerAdvice
public class CommonExceptionsHandlingController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionsHandlingController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResultDto unhandledExceptions(Exception ex) {
        String uuid = UUID.randomUUID().toString();
        LOGGER.error("Unhandled exception during REST service call. Sending error with UUID={} to the client.", uuid, ex);
        return ErrorResultDto.builder()
                .uuid(uuid)
                .message("Internal server error. Please contact administrator.")
                .build();
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        LOGGER.error("Bad request. Sending error with UUID={} to the client.", uuid, ex);
        return new ResponseEntity<>(ErrorResultDto.builder()
                .uuid(uuid)
                .message("Bad request.")
                .build(), HttpStatus.BAD_REQUEST);
    }

}
