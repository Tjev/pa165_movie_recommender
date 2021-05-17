package cz.muni.fi.pa165.rest.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataSourceException extends DataAccessException {

    public DataSourceException(String msg) { super(msg); }

    public DataSourceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

