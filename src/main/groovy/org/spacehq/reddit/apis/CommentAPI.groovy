package org.spacehq.reddit.apis

import org.spacehq.reddit.data.constants.SubmissionType
import org.spacehq.reddit.data.constants.VoteDirection
import org.spacehq.reddit.data.json.Comment
import org.spacehq.reddit.data.json.Listing
import org.spacehq.reddit.util.http.HttpRequest
import org.spacehq.reddit.util.http.HttpSession
import org.spacehq.reddit.util.http.ResponseType

/**
 * Reddit comment API methods.
 */
class CommentAPI {
    private final HttpSession session

    /**
     * Creates a new CommentAPI instance.
     * @param session HttpSession to use.
     */
    CommentAPI(HttpSession session) {
        this.session = session
    }

    /**
     * Posts a comment.
     * @param text Text in the comment.
     * @param thingId ID of the thing to post the comment on.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void comment(String text, String thingId, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/comment", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.text = text
        request.parameters.thing_id = thingId
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Deletes a comment.
     * @param id ID of the comment.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void del(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/del", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Edits the body of a comment or self post
     * @param text Text to replace the current text with.
     * @param id ID of the thing to edit.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void editUserText(String text, String thingId, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/editusertext", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.text = text
        request.parameters.thing_id = thingId
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Hides a link
     * @param id ID of the link.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void hide(String linkId, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/hide", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = linkId
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Gets a Listing of links by full name or URL.
     * @param subreddit (Optional) Subreddit to search in.
     * @param value Value to search for.
     * @param url Whether the value is a URL or not.
     * @param limit (Optional) The maximum amount of results.
     * @param callback Callback to pass the Listing result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void info(String subreddit = null, String value, boolean url, int limit = 25, Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "${subreddit != null ? "r/${subreddit}/" : ""}api/info", method: "GET")
        request.parameters.api_type = "json"
        request.parameters[url ? "url" : "id"] = value
        request.parameters.limit = limit
        this.session.makeRequest(request, {
            callback(new Listing(it))
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Marks a link as NSFW.
     * @param id ID of the link.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void markNSFW(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/marknsfw", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Retrieves a list of more comment replies.
     * @param query Query to search with. Required: linkId; Optional: children, id, pvHex, sortBy
     * @param callback Callback to pass the List<Comment> result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void moreChildren(Map query, Closure callback, Closure errorHandler = null) {
        Map q = [children: [], id: null, linkId: "", pvHex: null, sortBy: null]
        q << query
        HttpRequest request = new HttpRequest(location: "api/morechildren", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.children = q.children.join(",")
        request.parameters.id = q.id
        request.parameters.link_id = q.linkId
        request.parameters.pv_hex = q.pvHex
        request.parameters.sort = q.sortBy
        this.session.makeRequest(request, {
            callback(it.json.data.things.collect { new Comment(it) })
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Reports a link or comment.
     * @param id ID of the link or comment.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void report(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/report", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Saves a link or comment.
     * @param id ID of the link or comment.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void save(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/save", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Enables or disables inbox replies for a link.
     * @param id ID of the link.
     * @param state Whether to enable or disable inbox replies.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void sendReplies(String id, boolean state, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/sendreplies", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        request.parameters.state = state
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Enables or disables contest mode for a link's comments.
     * @param id ID of the link.
     * @param state Whether to enable or disable contest mode.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void setContestMode(String id, boolean state, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/set_contest_mode", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        request.parameters.state = state
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Sets a self-post as stickied or unstickied.
     * @param id ID of the post.
     * @param state Whether to sticky or unsticky the post.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void setSubredditSticky(String id, boolean state, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/set_subreddit_sticky", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        request.parameters.state = state
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Submits a post to a subreddit.
     * @param params Parameters for creating the post. Required: subreddit, kind, title, content; Optional: then, extension, resubmit, save, sendReplies, iden, captcha
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void submit(Map params, Closure callback = null, Closure errorHandler = null) {
        Map p = [subreddit: "", kind: SubmissionType.SELF, title: "", content: "", then: null, extension: null, resubmit: false, save: false, sendReplies: false, iden: null, captcha: null]
        p << params
        HttpRequest request = new HttpRequest(location: "api/submit", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.captcha = p.captcha
        request.parameters.extension = p.extension
        request.parameters.iden = p.iden
        request.parameters.kind = p.kind
        request.parameters.resubmit = p.resubmit
        request.parameters.save = p.save
        request.parameters.sendreplies = p.sendReplies
        request.parameters.sr = p.subreddit
        request.parameters[p.kind == CommentType.SELF ? "text" : "url"] = p.content
        request.parameters.then = p.then
        request.parameters.title = p.title
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Unhides a link.
     * @param id ID of the link.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void unhide(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/unhide", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Unmarks a link as NSFW.
     * @param id ID of the link.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void unmarkNSFW(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/unmarknsfw", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Unsaves a link or comment.
     * @param id ID of the link or comment.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void unsave(String id, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/unsave", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Casts a vote on a thing.
     * @param id ID of the thing to vote on.
     * @param direction Direction of the vote to cast.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void vote(String id, VoteDirection direction, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/vote", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.id = id
        request.parameters.dir = direction.ordinal() - 1
        this.session.makeRequest(request, callback, errorHandler)
    }
}
