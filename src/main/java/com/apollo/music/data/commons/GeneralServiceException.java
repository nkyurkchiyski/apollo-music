package com.apollo.music.data.commons;

public class GeneralServiceException extends RuntimeException {
    public GeneralServiceException() {
    }

    public GeneralServiceException(final String message) {
        super(message);
    }

    public GeneralServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GeneralServiceException(final Throwable cause) {
        super(cause);
    }

    public GeneralServiceException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
