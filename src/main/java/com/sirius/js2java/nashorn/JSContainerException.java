package com.sirius.js2java.nashorn;

public class JSContainerException extends RuntimeException {

    public JSContainerException() {
    }

    public JSContainerException(String message) {
        super(message);
    }

    public JSContainerException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSContainerException(Throwable cause) {
        super(cause);
    }

    public JSContainerException(String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
