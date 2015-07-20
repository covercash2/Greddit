package org.spacehq.reddit.apis

import org.spacehq.reddit.data.constants.OAuthDuration
import org.spacehq.reddit.data.json.Account
import org.spacehq.reddit.util.http.HttpRequest
import org.spacehq.reddit.util.http.HttpSession
import org.spacehq.reddit.util.http.ResponseType
import org.spacehq.reddit.util.http.auth.oauth.OAuthAPI

/**
 * Reddit OAuth API methods.
 */
class RedditOAuthAPI {
    private final OAuthAPI oauth
    private final HttpSession session

    /**
     * Creates a new RedditOAuthAPI instance.
     * @param session Session to use.
     */
    RedditOAuthAPI(HttpSession session) {
        this.oauth = new OAuthAPI(session, "api/v1/authorize", "api/v1/access_token", "oauth.reddit.com")
        this.session = session
    }

    /**
     * Builds an authorization URL to have a user visit.
     * @param compact (Optional) Whether or not to use the compact mobile authorization URL.
     * @param clientId Client ID of the request.
     * @param redirectUri URI to redirect to when the user authorizes the request.
     * @param state A random value used to make sure a response is from the correct request.
     * @param duration Duration that the resulting access token should last for.
     * @param scope Scope to request access for.
     * @return The built authorization URL.
     */
    String getAuthorizationURL(boolean compact = false, String clientId, String redirectUri, String state, OAuthDuration duration, List scope) {
        String url = this.oauth.getAuthorizationURL(clientId, redirectUri, state, scope*.name().toLowerCase(), [duration: duration.name().toLowerCase()])
        if(compact) {
            url = url.replace("api/v1/authorize", "api/v1/authorize.compact")
        }

        return url
    }

    /**
     * Gets the access token of an authorization code.
     * @param consumerKey Consumer Key to authorize with.
     * @param consumerSecret Consumer Secret to authorize with.
     * @param code Authorization code to get the token of.
     * @param redirectUri Redirect URI of the authorization request.
     * @param callback Callback to pass the access token to.
     * @param errorHandler (Optional) Callback to handle any errors.
     */
    void getAccessToken(String consumerKey, String consumerSecret, String code, String redirectUri, Closure callback, Closure errorHandler = null) {
        this.oauth.getAccessToken(consumerKey, consumerSecret, code, redirectUri, callback, errorHandler)
    }

    /**
     * Refreshes an access token.
     * @param consumerKey Consumer Key to authorize with.
     * @param consumerSecret Consumer Secret to authorize with.
     * @param refreshToken Refresh token of the access token to refresh.
     * @param callback Callback to pass the new access token to.
     * @param errorHandler (Optional) Callback to handle any errors.
     */
    void refreshAccessToken(String consumerKey, String consumerSecret, String refreshToken, String redirectUri, Closure callback, Closure errorHandler = null) {
        this.oauth.refreshAccessToken(consumerKey, consumerSecret, refreshToken, callback, errorHandler)
    }

    /**
     * Enables OAuth authorization on this instance's HttpSession.
     * @param type Type of OAuth token to use.
     * @param token OAuth token to use.
     */
    void enableOAuth(String type, String token) {
        this.oauth.enableOAuth(type, token)
    }

    /**
     * Gets the account information of the user.
     * @param callback Callback to pass the Account result to.
     * @param errorHandler (Optional) Callback to handle any errors.
     */
    void me(Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/v1/me", method: "GET")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, {
            callback(new Account(it))
        }, errorHandler, ResponseType.JSON)
    }
}
