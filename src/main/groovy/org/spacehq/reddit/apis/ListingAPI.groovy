package org.spacehq.reddit.apis

import org.spacehq.gcommons.http.HttpRequest
import org.spacehq.gcommons.http.HttpSession
import org.spacehq.gcommons.http.ResponseType
import org.spacehq.reddit.data.constants.SortMode
import org.spacehq.reddit.data.json.Listing

/**
 * Reddit listing API methods.
 */
class ListingAPI {
	private final HttpSession session

	/**
	 * Creates a new ListingAPI instance.
	 * @param session HttpSession to use.
	 */
	ListingAPI(HttpSession session) {
		this.session = session
	}

	/**
	 * Gets a Listing of links by fullname.
	 * @param names Names to get the links of.
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void byId(List names, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "by_id/${names.join(",")}", method: "GET")
		request.parameters.api_type = "json"
		this.session.makeRequest(request, {
			callback(new Listing(it))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Gets a Listing of comments for an article.
	 * @param query Query to get comments with. Required: article; Optional: subreddit, comment, context, depth, limit, sortBy
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void comments(Map query, Closure callback, Closure errorHandler = null) {
		Map q = [subreddit: null, article: "", comment: null, context: null, depth: null, limit: null, sortBy: null]
		q << query
		HttpRequest request = new HttpRequest(location: "${q.subreddit != null ? "r/${q.subreddit}/" : ""}comments/${q.article}", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.comment = q.comment
		request.parameters.context = q.context
		request.parameters.depth = q.depth
		request.parameters.limit = q.limit
		request.parameters.sort = q.sortBy
		this.session.makeRequest(request, {
			callback(new Listing(it))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Gets a sorted Listing of articles.
	 * @param query Query to get articles with. Required: sortBy; Optional: subreddit, after, before, ct, limit, showAll, time
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void list(Map query, Closure callback, Closure errorHandler = null) {
		Map q = [subreddit: null, sortBy: SortMode.HOT, after: null, before: null, ct: 0, limit: 25, showAll: false, time: null]
		q << query
		HttpRequest request = new HttpRequest(location: "${q.subreddit != null ? "r/${q.subreddit}/" : ""}${q.sortBy.name().toLowerCase()}", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.after = q.after
		request.parameters.before = q.before
		request.parameters.count = q.ct
		request.parameters.limit = q.limit
		if(q.showAll) {
			request.parameters.show = "all"
		}

		request.parameters.t = q.time
		this.session.makeRequest(request, {
			callback(new Listing(it))
		}, errorHandler, ResponseType.JSON)
	}
}
