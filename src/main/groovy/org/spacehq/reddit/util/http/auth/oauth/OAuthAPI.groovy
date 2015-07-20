package org.spacehq.reddit.util.http.auth.oauth
/**
 * An API class for interfacing with OAuth API endpoints.
 */
class OAuthAPI {
    private final org.spacehq.reddit.util.http.HttpSession session
    private final String authEndpoint
    private final String tokenEndpoint
    private final String unauthenticatedBaseURL
    private final String authenticatedBaseURL

    /**
     * Creates a new OAuthAPI instance with its own HttpSession.
     * @param baseUrl Base URL to use with the HttpSession.
     * @param authEndpoint OAuth 'authenticate' endpoint path.
     * @param tokenEndpoint OAuth 'access_token' endpoint path.
     * @param authenticatedBaseURL (Optional) Base URL to switch to when authenticated.
     */
    OAuthAPI(String baseUrl, String authEndpoint, String tokenEndpoint, String authenticatedBaseURL = baseUrl) {
        this(new org.spacehq.reddit.util.http.HttpSession(baseUrl: baseUrl), authEndpoint, tokenEndpoint, authenticatedBaseURL)
    }

    /**
     * Creates a new OAuthAPI instance with an existing HttpSession.
     * @param session HttpSession to use.
     * @param authEndpoint OAuth 'authenticate' endpoint path.
     * @param tokenEndpoint OAuth 'access_token' endpoint path.
     * @param authenticatedBaseURL (Optional) Base URL to switch to when authenticated.
     */
    OAuthAPI(org.spacehq.reddit.util.http.HttpSession session, String authEndpoint, String tokenEndpoint, String authenticatedBaseURL = null) {
        this.session = session
        this.authEndpoint = authEndpoint
        this.tokenEndpoint = tokenEndpoint
        this.authenticatedBaseURL = authenticatedBaseURL
        if(this.authenticatedBaseURL != null) {
            this.unauthenticatedBaseURL = this.session.baseUrl
        }
    }

    /**
     * Builds an authorization URL to have a user visit.
     * @param clientId Client ID of the request.
     * @param redirectUri URI to redirect to when the user authorizes the request.
     * @param state A random value used to make sure a response is from the correct request.
     * @param scope Scope to request access for.
     * @param extra Extra data required by specific OAuth providers in the format "parameter: value".
     * @return The built authorization URL.
     */
    String getAuthorizationURL(String clientId, String redirectUri, String state, List scope, Map extra = [:]) {
        return "https://${this.unauthenticatedBaseURL != null ? this.unauthenticatedBaseURL : this.session.baseUrl}/${this.authEndpoint}?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}&state=${state}&scope=${scope.join(",")}${extra.size() > 0 ? "&" : ""}${extra.collect { k, v -> "${k}=${v}" }.join('&')}"
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
        this.disableOAuth()
        org.spacehq.reddit.util.http.HttpRequest request = new org.spacehq.reddit.util.http.HttpRequest(location: this.tokenEndpoint, method: "POST")
        this.session.authorization = new org.spacehq.reddit.util.http.auth.BasicAuthorization(consumerKey, consumerSecret)
        request.parameters.code = code
        request.parameters.client_id = consumerKey
        request.parameters.client_secret = consumerSecret
        request.parameters.redirect_uri = redirectUri
        request.parameters.grant_type = "authorization_code"
        this.session.makeRequest(request, {
            callback(new AccessTokenData(it))
        }, errorHandler, org.spacehq.reddit.util.http.ResponseType.JSON)
    }

    /**
     * Refreshes an access token.
     * @param consumerKey Consumer Key to authorize with.
     * @param consumerSecret Consumer Secret to authorize with.
     * @param refreshToken Refresh token of the access token to refresh.
     * @param callback Callback to pass the new access token to.
     * @param errorHandler (Optional) Callback to handle any errors.
     */
    void refreshAccessToken(String consumerKey, String consumerSecret, String refreshToken, Closure callback, Closure errorHandler = null) {
        this.disableOAuth()
        org.spacehq.reddit.util.http.HttpRequest request = new org.spacehq.reddit.util.http.HttpRequest(location: this.tokenEndpoint, method: "POST")
        this.session.authorization = new org.spacehq.reddit.util.http.auth.BasicAuthorization(consumerKey, consumerSecret)
        request.parameters.refresh_token = refreshToken
        request.parameters.client_id = consumerKey
        request.parameters.client_secret = consumerSecret
        request.parameters.grant_type = "refresh_token"
        this.session.makeRequest(request, {
            callback(new AccessTokenData(it))
        }, errorHandler, org.spacehq.reddit.util.http.ResponseType.JSON)
    }

    /**
     * Enables OAuth authorization on this instance's HttpSession.
     * @param type Type of OAuth token to use.
     * @param token OAuth token to use.
     */
    void enableOAuth(String type, String token) {
        this.session.authorization = new org.spacehq.reddit.util.http.auth.OAuthAuthorization(type, token)
        if(this.authenticatedBaseURL != null) {
            this.session.baseUrl = this.authenticatedBaseURL
        }
    }

    /**
     * Disables OAuth authorization on this instance's HttpSession.
     */
    void disableOAuth() {
        this.session.authorization = null
        if(this.authenticatedBaseURL != null) {
            this.session.baseUrl = this.unauthenticatedBaseURL
        }
    }
}
