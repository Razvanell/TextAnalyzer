package homework.textanalyzer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to be thrown when the input text for analysis exceeds the allowed maximum length.
 * Automatically mapped to HTTP 413 Payload Too Large by Spring's @ResponseStatus.
 */
@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
public class TextLengthExceededException extends RuntimeException {

    /**
     * Constructs a new TextLengthExceededException with the specified detail message.
     * @param message The detail message.
     */
    public TextLengthExceededException(String message) {
        super(message);
    }
}
