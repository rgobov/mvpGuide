package gobov.roma.mvpguide.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotFoundSomedException extends RuntimeException {
    public NotFoundSomedException(String routeNotFound) {
        super(routeNotFound);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class notFoundException extends RuntimeException {
        public notFoundException(String message) {
            super(message);
        }
    }
}
