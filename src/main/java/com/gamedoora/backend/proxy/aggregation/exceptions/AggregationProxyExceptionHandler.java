package com.gamedoora.backend.proxy.aggregation.exceptions;

import org.apache.camel.CamelException;
import org.apache.camel.CamelExecutionException;
import org.hibernate.MappingException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.lang.model.UnknownEntityException;
import java.net.ConnectException;
import  feign.FeignException;
import feign.RetryableException;
/**
 * @author aprajshekhar
 */
@ControllerAdvice
public class AggregationProxyExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {RetryableException.class})
  protected ResponseEntity<Object> handleConnectException(ConnectException ex, WebRequest request) {
    AggregationProxyExceptionResponseEntity bodyOfResponse = getBodyOfResponse(ex, HttpStatus.SERVICE_UNAVAILABLE);
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
  }
  @ExceptionHandler(value = {CamelExecutionException.class})
  protected ResponseEntity<Object> handleCamelException(CamelExecutionException ex, WebRequest request) {
    AggregationProxyExceptionResponseEntity bodyOfResponse = getBodyOfResponse(ex, HttpStatus.SERVICE_UNAVAILABLE);
    return handleExceptionInternal(
            ex, bodyOfResponse, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
  }

  private  AggregationProxyExceptionResponseEntity getBodyOfResponse(Exception ex, HttpStatus httpStatus) {
    return AggregationProxyExceptionResponseEntity.builder()
            .status(httpStatus.name())
            .message(ex.getMessage())
            .details(ex.getMessage())
            .build();
  }



}
