package org.glad2121.dataset.resource;

import org.glad2121.dataset.DataSetException;

/**
 * 
 * 
 * @author GLAD!!
 */
public class ResourceNotFoundException extends DataSetException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
