package org.spacehq.reddit.util.http.auth.oauth

import groovy.transform.ToString

/**
 * Data relating to an access token.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class AccessTokenData {
    /**
     * The access token.
     */
    String access_token
    /**
     * The access token's refresh token. (may be null.)
     */
    String refresh_token
    /**
     * The access token's type.
     */
    String token_type
    /**
     * The amount of time until the token expires.
     */
    long expires_in
    /**
     * The scope of the access token. (may be null.)
     */
    List scope

    /**
     * Creates a new AccessTokenData instance.
     * @param map Map to get data from.
     */
    AccessTokenData(Map map) {
        org.spacehq.reddit.util.DataUtil.fixTypes(this.getClass(), map).each { k, v ->
            this[k] = v
        }
    }
}
