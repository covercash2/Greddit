package org.spacehq.reddit.apis

import org.spacehq.gcommons.http.HttpRequest
import org.spacehq.gcommons.http.HttpSession
import org.spacehq.gcommons.http.ResponseType
import org.spacehq.reddit.data.constants.WikiEditorAction
import org.spacehq.reddit.data.constants.WikiPermissionLevel
import org.spacehq.reddit.data.json.*

/**
 * Reddit wiki API methods.
 */
class WikiAPI {
	private final HttpSession session

	/**
	 * Creates a new WikiAPI instance.
	 * @param session HttpSession to use.
	 */
	WikiAPI(HttpSession session) {
		this.session = session
	}

	/**
	 * Modifies a wiki editor.
	 * @param subreddit Subreddit to modify the editor in.
	 * @param action Action to perform with the editor.
	 * @param page Page to modify the editor in.
	 * @param username Username of the editor.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void modifyEditor(String subreddit, WikiEditorAction action, String page, String username, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/wiki/alloweditor/${action.name().toLowerCase()}", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.page = page
		request.parameters.username = username
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Edits a wiki page.
	 * @param subreddit Subreddit to edit the page in.
	 * @param content New contents of the wiki page.
	 * @param page Page to edit.
	 * @param prev Previous text in the wiki page.
	 * @param reason Reason for editing the wiki page.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void edit(String subreddit, String content, String page, String prev, String reason, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/wiki/edit", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.content = content
		request.parameters.page = page
		request.parameters.previous = prev
		request.parameters.reason = reason
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Hides a wiki page revision.
	 * @param subreddit Subreddit to hide the revision in.
	 * @param page Page to hide a revision in.
	 * @param revision Revision to hide.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void hide(String subreddit, String page, String revision, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/wiki/hide", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.page = page
		request.parameters.revision = revision
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Reverts a wiki page revision.
	 * @param subreddit Subreddit to revert the revision in.
	 * @param page Page to revert a revision in.
	 * @param revision Revision to revert.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void revert(String subreddit, String page, String revision, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/wiki/revert", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.page = page
		request.parameters.revision = revision
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Gets a Listing of discussions on a wiki page.
	 * @param query Query to use to get discussions. Required: subreddit, page; Optional: after, before, ct, limit, showAll
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void discussions(Map query, Closure callback, Closure errorHandler = null) {
		Map q = [subreddit: "", page: "", after: null, before: null, ct: 0, limit: 25, showAll: false]
		q << query
		HttpRequest request = new HttpRequest(location: "r/${q.subreddit}/wiki/discussions/${q.page}", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.after = q.after
		request.parameters.before = q.before
		request.parameters.count = q.ct
		request.parameters.limit = q.limit
		request.parameters.page = q.page
		if(q.showAll) {
			request.parameters.show = "all"
		}

		this.session.makeRequest(request, {
			callback(new Listing(it))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Gets a listing of wiki pages in a subreddit.
	 * @param subreddit Subreddit to get the wiki pages of.
	 * @param callback Callback to pass the WikiPageListing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void pages(String subreddit, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/wiki/pages", method: "GET")
		request.parameters.api_type = "json"
		this.session.makeRequest(request, {
			callback(new WikiPageListing(it))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Gets a Listing of recent wiki page revisions in a subreddit.
	 * @param query Query to get revisions with. Required: subreddit; Optional: after, before, ct, limit, showAll
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void revisions(Map query, Closure callback, Closure errorHandler = null) {
		Map q = [subreddit: "", after: null, before: null, ct: 0, limit: 25, showAll: false]
		q << query
		HttpRequest request = new HttpRequest(location: "r/${q.subreddit}/wiki/revisions", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.after = q.after
		request.parameters.before = q.before
		request.parameters.count = q.ct
		request.parameters.limit = q.limit
		if(q.showAll) {
			request.parameters.show = "all"
		}

		this.session.makeRequest(request, {
			callback(new Listing(it, WikiRevision.class))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Gets the settings of a subreddit's wiki page .
	 * @param subreddit Subreddit to get the wiki page settings of.
	 * @param page Page to get the settings of.
	 * @param callback Callback to pass the WikiPageSettings result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void getSettings(String subreddit, String page, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/wiki/settings/${page}", method: "GET")
		request.parameters.api_type = "json"
		this.session.makeRequest(request, {
			callback(new WikiPageSettings(it))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Updates the settings of a wiki page.
	 * @param subreddit Subreddit of the wiki page to update the settings of.
	 * @param page Page to update the settings of.
	 * @param listed Whether the wiki page is listed.
	 * @param permlevel Permission level of the wiki page.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void updateSettings(String subreddit, String page, boolean listed, WikiPermissionLevel permlevel, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/wiki/settings/${page}", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.listed = listed
		request.parameters.permlevel = permlevel.ordinal()
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Gets the contents of a wiki page.
	 * @param subreddit Subreddit of the wiki page.
	 * @param page Page to get the contents of.
	 * @param v (Optional) The revision to get the contents of, or the first revision of the diff if a second is specified.
	 * @param v2 (Optional) The second revision to compare to in the diff.
	 * @param callback Callback to pass the WikiPage result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void page(String subreddit, String page, String v = null, String v2 = null, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/wiki/${page}", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.v = v
		request.parameters.v2 = v2
		this.session.makeRequest(request, {
			callback(new WikiPage(it))
		}, errorHandler, ResponseType.JSON)
	}
}
