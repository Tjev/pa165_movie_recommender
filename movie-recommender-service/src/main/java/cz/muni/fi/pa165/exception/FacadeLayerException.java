package cz.muni.fi.pa165.exception;

import org.springframework.dao.DataAccessException;

public class FacadeLayerException extends DataAccessException {

    public FacadeLayerException(String msg) { super(msg); }

    public FacadeLayerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
