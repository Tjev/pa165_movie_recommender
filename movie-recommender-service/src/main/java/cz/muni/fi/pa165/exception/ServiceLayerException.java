package cz.muni.fi.pa165.exception;

import org.springframework.dao.DataAccessException;

public class ServiceLayerException extends DataAccessException {

    public ServiceLayerException(String msg) {
        super(msg);
    }

    public ServiceLayerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
