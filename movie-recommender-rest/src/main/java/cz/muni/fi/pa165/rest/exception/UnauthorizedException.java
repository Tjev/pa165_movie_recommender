package cz.muni.fi.pa165.rest.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends DataAccessException {

    public UnauthorizedException(String msg) { super(msg); }

    public UnauthorizedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
