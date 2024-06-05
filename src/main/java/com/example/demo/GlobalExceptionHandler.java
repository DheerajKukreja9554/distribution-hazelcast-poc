package com.example.demo;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Flux;

@Slf4j
@RestControllerAdvice
// @Order(-2)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // @ExceptionHandler(Exception.class)
    // protected ProblemDetail handleNotFound(Exception ex) {
    // ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    // problemDetail.setTitle("User not found");
    // problemDetail.setType(URI.create("https://example.com/problems/user-not-found"));
    // problemDetail.setProperty("errors", List.of(ex.getMessage()));
    // log.error("error", ex);
    // return problemDetail;
    // }


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

    @Data
    static class Base {

        String uuid;

        public void generateUUID() {
            this.uuid = UUID.randomUUID().toString();
        }
    }
    @Data
    static class Something extends Base {
        int i;

        Something(int i) {
            this.i = i;
        }

    }

    public static void main(String[] args) {
        List<Something> sample = Flux.range(1, 10)
                .map(i -> new Something(i))
                .collectList()
                .block();

        sample.stream()
                .peek(Base::generateUUID)
                .toList()
                .forEach(v -> System.err.println(v.getUuid()));
    }
}
