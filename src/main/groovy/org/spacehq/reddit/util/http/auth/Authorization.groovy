package org.spacehq.reddit.util.http.auth

/**
 * HTTP Authorization data.
 */
interface Authorization {
    /**
     * Gets the 'Authorization' header value of this data.
     * @return The data's header value.
     */
    String getHeaderValue()
}