package com.intive.shopme.configuration.error;

import com.intive.shopme.exception.AlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toMap;

@ControllerAdvice
public class ErrorHandlingConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlingConfiguration.class);

    private static final String VALIDATION_DESCRIPTION_JSON_KEY = "message";
    private static final String CONSTRAINTS_JSON_KEY = "errors";
    private static final String ERROR_ID_JSON_KEY = "id";

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity
                .unprocessableEntity()
                .body(Map.of(CONSTRAINTS_JSON_KEY, prepareConstraintsDescriptions(exception.getConstraintViolations())));
    }

    private static Map<String, String> prepareConstraintsDescriptions(Set<ConstraintViolation<?>> violations) {
        return violations
                .stream()
                .collect(toMap(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage));
    }

    @ExceptionHandler(value = {DataRetrievalFailureException.class})
    @ResponseBody
    public ResponseEntity handleDataRetrievalFailureException(DataRetrievalFailureException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(createResponseBody(exception.getMessage()));
    }

    @ExceptionHandler(value = {AlreadyExistException.class})
    @ResponseBody
    public ResponseEntity handleAlreadyExistException(AlreadyExistException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(createResponseBody(exception.getMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createResponseBody(exception.getRootCause().getMessage()));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseBody
    public ResponseEntity handleRuntimeException(RuntimeException exception) {
        final var id = UUID.randomUUID();
        LOGGER.error("ID: {}", id, exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        ERROR_ID_JSON_KEY, id,
                        VALIDATION_DESCRIPTION_JSON_KEY, exception.getMessage()));
    }

    private static Map<String, String> createResponseBody(String message) {
        return Map.of(VALIDATION_DESCRIPTION_JSON_KEY, message);
    }
}
