package cz.muni.fi.pa165.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="The resource already exists")
public class DataSourceException extends DataAccessException {

    public DataSourceException(String msg) { super(msg); }

    public DataSourceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

