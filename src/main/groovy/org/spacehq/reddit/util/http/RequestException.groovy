package org.spacehq.reddit.util.http

/**
 * Passed to an error callback when an error occurs while making a request.
 */
class RequestException extends Exception {
    private static final long serialVersionUID = 1

    /**
     * Creates a new RequestException instance.
     */
    RequestException() {
        super()
    }

    /**
     * Creates a new RequestException instance.
     * @param message Message of the exception.
     */
    RequestException(String message) {
        super(message)
    }

    /**
     * Creates a new RequestException instance.
     * @param cause Cause of the exception.
     */
    RequestException(Throwable cause) {
        super(cause)
    }

    /**
     * Creates a new RequestException instance.
     * @param message Message of the exception.
     * @param cause Cause of the exception.
     */
    RequestException(String message, Throwable cause) {
        super(message, cause)
    }
}
