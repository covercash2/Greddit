package org.spacehq.reddit.apis

import org.spacehq.reddit.data.json.Listing
import org.spacehq.reddit.util.http.HttpRequest
import org.spacehq.reddit.util.http.HttpSession
import org.spacehq.reddit.util.http.ResponseType

/**
 * Reddit search API methods.
 */
class SearchAPI {
    private final HttpSession session

    /**
     * Creates a new SearchAPI instance.
     * @param session Session to use.
     */
    SearchAPI(HttpSession session) {
        this.session = session
    }

    /**
     * Performs a search.
     * @param query Query to search with. (required: query, optional: subreddit, after, before, ct, limit, restrict, showAll, sortBy, syntax, time)
     * @param callback Callback to pass the Listing result to.
     * @param errorHandler (Optional) Callback to pass errors to.
     */
    void search(Map query, Closure callback, Closure errorHandler = null) {
        Map q = [subreddit: null, query: "", after: null, before: null, ct: 0, limit: 25, restrict: false, showAll: false, sortBy: null, syntax: null, time: null]
        q << query
        HttpRequest request = new HttpRequest(location: "${q.subreddit != null ? "r/${q.subreddit}/" : ""}search", method: "GET")
        request.parameters.api_type = "json"
        request.parameters.after = q.after
        request.parameters.before = q.before
        request.parameters.count = q.ct
        request.parameters.limit = q.limit
        request.parameters.q = q.query
        request.parameters.restrict_sr = q.restrict
        if(q.showAll) {
            request.parameters.show = "all"
        }

        request.parameters.sort = q.sortBy
        request.parameters.syntax = q.syntax
        request.parameters.t = q.time
        this.session.makeRequest(request, {
            callback(new Listing(it))
        }, errorHandler, ResponseType.JSON)
    }
}
