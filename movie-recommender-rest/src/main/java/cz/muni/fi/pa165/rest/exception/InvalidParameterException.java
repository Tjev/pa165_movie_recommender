package cz.muni.fi.pa165.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InvalidParameterException extends DataAccessException {

    public InvalidParameterException(String msg) { super(msg); }

    public InvalidParameterException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

