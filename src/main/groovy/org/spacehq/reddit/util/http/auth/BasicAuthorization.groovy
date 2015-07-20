package org.spacehq.reddit.util.http.auth

/**
 * HTTP Basic Authorization data.
 */
class BasicAuthorization implements Authorization {
    /**
     * The 'Authorization' header value.
     */
    final String headerValue

    /**
     * Creates a new BasicAuthorization data instance.
     * @param username Username to authorize with.
     * @param password Password to authorize with.
     */
    BasicAuthorization(String username, String password) {
        String encrypted = "${username}:${password}".getBytes("UTF-8").encodeBase64().toString()
        this.headerValue = "Basic ${encrypted}"
    }
}
