package org.spacehq.reddit.util.http

/**
 * The type of data to parse a response to when making a request.
 */
public enum ResponseType {
    /**
     * JSON data.
     */
    JSON,
    /**
     * Boolean data.
     */
            BOOLEAN,
    /**
     * String data.
     */
            STRING,
    /**
     * Byte array data.
     */
            BYTE_ARRAY
}
