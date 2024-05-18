package com.example.demo;

import java.net.URI;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
// @Order(-2)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ProblemDetail handleNotFound(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("User not found");
        problemDetail.setType(URI.create("https://example.com/problems/user-not-found"));
        problemDetail.setProperty("errors", List.of(ex.getMessage()));
        log.error("error", ex);
        return problemDetail;
    }


    // public GlobalExceptionHandler(ErrorAttributes errorAttributes, Resources resources, ApplicationContext applicationContext) {
    // super(errorAttributes, resources, applicationContext);
    // //TODO Auto-generated constructor stub
    // }

    // @Override
    // protected RouterFunction<ServerResponse> getRoutingFunction(
    // ErrorAttributes errorAttributes) {

    // return RouterFunctions.route(
    // RequestPredicates.all(), this::renderErrorResponse);
    // }

    // private Mono<ServerResponse> renderErrorResponse(
    // ServerRequest request) {

    // Map<String, Object> errorPropertiesMap = getErrorAttributes(request,
    // ErrorAttributeOptions.defaults());

    // return ServerResponse.status(HttpStatus.BAD_REQUEST)
    // .contentType(MediaType.APPLICATION_JSON)
    // .body(BodyInserters.fromValue(errorPropertiesMap));
    // }

}
