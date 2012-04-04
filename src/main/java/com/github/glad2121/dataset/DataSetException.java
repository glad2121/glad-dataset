package com.github.glad2121.dataset;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DataSetException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataSetException() {
    }

    public DataSetException(String message) {
        super(message);
    }

    public DataSetException(Throwable cause) {
        super(cause);
    }

    public DataSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public static RuntimeException wrap(Throwable t) {
        if (t instanceof Error) {
            throw (Error) t;
        }
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        }
        return new DataSetException(t);
    }

}
