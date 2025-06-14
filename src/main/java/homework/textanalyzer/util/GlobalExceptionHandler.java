package homework.textanalyzer.util;

import homework.textanalyzer.exception.TextLengthExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler for the TextAnalyzer application.
 * Annotation @ControllerAdvice provides centralized exception handling across all @Controller classes, ensuring consistent error responses.
 */
@ControllerAdvice // Makes this class a global handler for exceptions
public class GlobalExceptionHandler {

    /**
     * Handles cases where required request parameters are missing from the URL.
     * Returns an HTTP 400 Bad Request status with a descriptive message.
     * @param ex The MissingServletRequestParameterException.
     * @return ResponseEntity containing the error message.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String error = ex.getParameterName() + " parameter is missing.";
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles cases where a request parameter cannot be converted to its expected type
     * (e.g., passing "invalid" for an AnalysisType enum).
     * Returns an HTTP 400 Bad Request status with details about the type mismatch.
     * @param ex The MethodArgumentTypeMismatchException.
     * @return ResponseEntity containing the error message.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = String.format("Parameter '%s' has an invalid value: '%s'. Expected type: %s",
                ex.getName(), ex.getValue(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * A general exception handler for `@Valid` related issues.
     * Returns an HTTP 400 Bad Request for validation failures.
     * @param ex The MethodArgumentNotValidException.
     * @return ResponseEntity containing a generic validation error message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String error = "Validation error: " + ex.getMessage();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catches and handles the custom TextLengthExceededException.
     * The HTTP status (413 Payload Too Large) is already defined by the @ResponseStatus annotation on the TextLengthExceededException class itself.
     * @param ex The TextLengthExceededException.
     * @return ResponseEntity containing the exception's message in the response body.
     */
    @ExceptionHandler(TextLengthExceededException.class)
    // No @ResponseStatus needed here because it's defined on the exception class itself.
    public ResponseEntity<String> handleTextLengthExceeded(TextLengthExceededException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.PAYLOAD_TOO_LARGE);
    }

}
