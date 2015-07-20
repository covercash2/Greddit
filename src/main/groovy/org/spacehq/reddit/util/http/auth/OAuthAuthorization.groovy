package org.spacehq.reddit.util.http.auth

/**
 * OAuth authorization data.
 */
class OAuthAuthorization implements Authorization {
    /**
     * The 'Authorization' header value.
     */
    final String headerValue

    /**
     * Creates a new OAuthAuthorization instance.
     * @param tokenType Type of token to authenticate with.
     * @param token Token to authenticate with.
     */
    OAuthAuthorization(String tokenType, String token) {
        this.headerValue = "${tokenType} ${token}"
    }
}
