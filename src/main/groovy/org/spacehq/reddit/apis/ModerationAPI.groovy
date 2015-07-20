package org.spacehq.reddit.apis

import org.spacehq.reddit.data.constants.DistinguishType
import org.spacehq.reddit.data.json.Listing
import org.spacehq.reddit.util.http.HttpRequest
import org.spacehq.reddit.util.http.HttpSession
import org.spacehq.reddit.util.http.ResponseType

/**
 * Reddit moderation API methods.
 */
class ModerationAPI {
    private final HttpSession session

    /**
     * Creates a new ModerationAPI instance.
     * @param session HttpSession to use.
     */
    ModerationAPI(HttpSession session) {
        this.session = session
    }

    /**
     * Gets a Listing of recent moderation activities.
     * @param query Query to get activities with. Required: subreddit; Optional: after, before, ct, limit, mod, showAll, type
     * @param callback Callback to pass the Listing result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void log(Map query, Closure callback, Closure errorHandler = null) {
        Map q = [subreddit: "", after: null, before: null, ct: 0, limit: 25, mod: null, showAll: false, type: null]
        q << query
        HttpRequest request = new HttpRequest(location: "r/${q.subreddit}/about/log", method: "GET")
        request.parameters.api_type = "json"
        request.parameters.after = q.after
        request.parameters.before = q.before
        request.parameters.count = q.ct
        request.parameters.limit = q.limit
        request.parameters.mod = q.mod?.join(",")
        if(q.showAll) {
            request.parameters.show = "all"
        }

        request.parameters.type = q.type
        this.session.makeRequest(request, {
            callback(new Listing(it))
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Accepts a moderator invite.
     * @param subreddit Subreddit to accept the invite in.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void acceptModeratorInvite(String subreddit, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/accept_moderator_invite", method: "POST")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Approves a link or comment.
     * @param id ID of the link or comment.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void approve(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/approve", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Distinguishes an author.
     * @param id ID of the thing to distinguish the author of.
     * @param how Type of distinguishment to give the author.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void distinguish(String id, DistinguishType how, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/distinguish", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.how = how == DistinguishType.MODERATOR ? "yes" : how == DistinguishType.NOT_DISTINGUISHED ? "no" : how
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Ignores reports from a thing.
     * @param id ID of the thing.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void ignoreReports(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/ignore_reports", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Leaves a subreddit as a contributor.
     * @param id ID of the subreddit.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void leaveContributor(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/leavecontributor", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Leaves a subreddit as a moderator.
     * @param id ID of the subreddit.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void leaveModerator(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/leavemoderator", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Removes a link or comment.
     * @param id ID of the link or comment.
     * @param spam Whether the link or comment was spammed.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void remove(String id, boolean spam, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/remove", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        request.parameters.spam = spam
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Unignores reports from a thing.
     * @param id ID of the thing.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void unignoreReports(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/unignore_reports", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Gets a subreddit's stylesheet.
     * @param subreddit Subreddit to get the stylesheet of.
     * @param callback Callback to pass the String result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void stylesheet(String subreddit, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/stylesheet", method: "GET")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, callback, errorHandler, ResponseType.STRING)
    }
}
