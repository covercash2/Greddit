package org.spacehq.reddit.apis

import org.spacehq.gcommons.http.HttpRequest
import org.spacehq.gcommons.http.HttpSession
import org.spacehq.gcommons.http.ResponseType
import org.spacehq.reddit.data.constants.*
import org.spacehq.reddit.data.json.*

/**
 * Reddit subreddit API methods.
 */
class SubredditAPI {
	private final HttpSession session

	/**
	 * Creates a new SubredditAPI instance.
	 * @param session HttpSession to use.
	 */
	SubredditAPI(HttpSession session) {
		this.session = session
	}

	/**
	 * Deletes the header of a subreddit,
	 * @param subreddit Subreddit to delete the header of.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void deleteHeader(String subreddit, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/delete_sr_header", method: "POST")
		request.parameters.api_type = "json"
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Deletes an image of a subreddit,
	 * @param subreddit Subreddit to delete the image of.
	 * @param image Image to delete.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void deleteImage(String subreddit, String image, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/delete_sr_image", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.img_name = image
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Gets a list of recommended subreddits for the given subreddits.
	 * @param subreddits Subreddits to get recommended subreddits for.
	 * @param omit Subreddits to omit.
	 * @param callback Callback to pass the List<String> result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void recommended(List subreddits, List omit, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/recommend/sr/${subreddits.join(",")}", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.omit = omit.join(",")
		this.session.makeRequest(request, {
			callback(it.collect { it.sr_name })
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Searches for subreddits with names similar to the given query.
	 * @param query Query to search with.
	 * @param includeOver18 Whether to include subreddits marked NSFW.
	 * @param callback Callback to pass the List<String> result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void searchSubredditNames(String query, boolean includeOver18, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/search_reddit_names", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.include_over_18 = includeOver18
		request.parameters.query = query
		this.session.makeRequest(request, {
			callback(new ArrayList(it.names))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Creates or updates a subreddit.
	 * @param subreddit Subreddit to create or update.
	 * @param create Whether to create the subreddit.
	 * @param allowTop Whether to allow this subreddit to be shown in the default set.
	 * @param cssOnCname Whether to have CSS on cname.
	 * @param sidebar Sidebar text of the subreddit.
	 * @param excludeBannedModqueue Whether to exclude posts by site-wide banned users.
	 * @param headerTitle Header title of the subreddit.
 	 * @param lang Language of the subreddit.
	 * @param linkType Allowed content type of the subreddit.
	 * @param over18 Whether the subreddit is NSFW.
	 * @param publicDescription Public description of the subreddit.
	 * @param publicTraffic Whether the traffic page should be public.
	 * @param showCnameSidebar Whether to show the cname in the sidebar.
	 * @param showMedia Whether to show media.
	 * @param spamComments Comment spam filter setting of the subreddit.
	 * @param spamLinks Link spam filter setting of the subreddit.
	 * @param spamSelfposts Self-post spam filter setting of the subreddit.
	 * @param submitLinkLabel Label of the submit link button.
	 * @param submitText Text to show on the submission page.
	 * @param submitTextLabel Label of the submit text button.
	 * @param title Title of the subreddit.
	 * @param type Type of the subreddit.
	 * @param wikimode Wiki mode of the subreddit.
	 * @param commentScoreHideMins (Optional) Minimum comment score to hide comments.
	 * @param prevDescriptionId (Optional) Previous description ID.
	 * @param prevPublicDescriptionId (Optional) Previous public description ID.
	 * @param prevSubmitTextId (Optional) Previous submit text ID.
	 * @param wikiEditAge (Optional) Account age required to edit the wiki.
	 * @param wikiEditKarma (Optional) Account karma required to edit the wiki.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void subreddit(String subreddit, boolean create, boolean allowTop, boolean cssOnCname, String sidebar, boolean excludeBannedModqueue, String headerTitle, String lang, SubmissionType linkType, boolean over18, String publicDescription, boolean publicTraffic, boolean showCnameSidebar, boolean showMedia, SubredditFilterStrength spamComments, SubredditFilterStrength spamLinks, SubredditFilterStrength spamSelfposts, String submitLinkLabel, String submitText, String submitTextLabel, String title, SubredditType type, SubredditWikiMode wikimode, int commentScoreHideMins = 0, String prevDescriptionId = null, String prevPublicDescriptionId = null, String prevSubmitTextId = null, int wikiEditAge = 0, int wikiEditKarma = 0, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/site_admin", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.name = subreddit
		if(!create) {
			request.parameters.sr = subreddit
		}

		request.parameters.allow_top = allowTop
		request.parameters.comment_score_hide_mins = commentScoreHideMins
		request.parameters.css_on_cname = cssOnCname
		request.parameters.description = sidebar
		request.parameters.exclude_banned_modqueue = excludeBannedModqueue
		request.parameters."header-title" = headerTitle
		request.parameters.lang = lang
		request.parameters.link_type = linkType
		request.parameters.over_18 = over18
		request.parameters.prev_description_id = prevDescriptionId
		request.parameters.prev_public_description_id = prevPublicDescriptionId
		request.parameters.prev_submit_text_id = prevSubmitTextId
		request.parameters.public_description = publicDescription
		request.parameters.public_traffic = publicTraffic
		request.parameters.show_cname_sidebar = showCnameSidebar
		request.parameters.show_media = showMedia
		request.parameters.spam_comments = spamComments
		request.parameters.spam_links = spamLinks
		request.parameters.spam_selfposts = spamSelfposts
		request.parameters.submit_link_label = submitLinkLabel
		request.parameters.submit_text = submitText
		request.parameters.submit_text_label = submitTextLabel
		request.parameters.title = title
		request.parameters.type = type
		request.parameters.wiki_edit_age = wikiEditAge
		request.parameters.wiki_edit_karma = wikiEditKarma
		request.parameters.wikimode = wikimode
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Gets the submission text of a subreddit.
	 * @param subreddit Subreddit to get the submission text of.
	 * @param callback Callback to pass the String result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void submitText(String subreddit, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/submit_text", method: "GET")
		request.parameters.api_type = "json"
		this.session.makeRequest(request, callback, errorHandler, ResponseType.STRING)
	}

	/**
	 * Sets the stylesheet of a subreddit.
	 * @param subreddit Subreddit to set the stylesheet of.
	 * @param contents Contents of the stylesheet.
	 * @param prevstyle (Optional) Previous stylesheet revision ID.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void subredditStylesheet(String subreddit, String contents, String prevstyle = null, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/subreddit_stylesheet", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.op = "save"
		request.parameters.prevstyle = prevstyle
		request.parameters.stylesheet_contents = contents
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Gets a list of subreddits by topic.
	 * @param query Query to search topics with.
	 * @param callback Callback to pass the List<String> result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void subredditsByTopic(String query, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/subreddits_by_topic", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.query = query
		this.session.makeRequest(request, {
			callback(it.collect { it.name })
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Edits the current user's subscription to a subreddit.
	 * @param subreddit Subreddit to (un)subscribe to.
	 * @param action Subscribe action to perform.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void subscribe(String subreddit, SubscribeAction action, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/subscribe", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.action = action
		request.parameters.sr = subreddit
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Uploads a new subreddit logo.
	 * @param subreddit Subreddit to upload the logo of.
	 * @param image Image to upload.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void uploadSubredditLogo(String subreddit, File image, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/upload_sr_img", method: "POST")
		request.parameters.api_type = "json"
		request.uploads.file = image
		request.parameters.header = 1
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Uploads a stylesheet image to a subreddit.
	 * @param subreddit Subreddit to upload the image to.
	 * @param name Name of the image.
	 * @param image Image to upload.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void uploadStylesheetImage(String subreddit, String name, File image, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/upload_sr_img", method: "POST")
		request.parameters.api_type = "json"
		request.uploads.file = image
		request.parameters.header = 0
		request.parameters.name = name
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Gets general information about a subreddit.
	 * @param subreddit Subreddit to get information about.
	 * @param callback Callback to pass the Subreddit result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void about(String subreddit, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/about", method: "GET")
		request.parameters.api_type = "json"
		this.session.makeRequest(request, {
			callback(new Subreddit(it))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Gets specific information about a subreddit.
	 * @param subreddit Subreddit to get information about.
	 * @param type Type of information to get.
	 * @param callback Callback to pass the result to. (SubredditSettings, Stylesheet, TrafficStatistics, or Subreddit)
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void info(String subreddit, SubredditInfoType type, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/about/edit", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.location = type == SubredditInfoType.SETTINGS ? "edit" : type
		this.session.makeRequest(request, {
			switch(type) {
				case SubredditInfoType.SETTINGS:
					callback(new SubredditSettings(it))
					break
				case SubredditInfoType.STYLESHEET:
					callback(new Stylesheet(it))
					break
				case SubredditInfoType.TRAFFIC:
					callback(new TrafficStatistics(it))
					break
				case SubredditInfoType.ABOUT:
					callback(new Subreddit(it))
					break
			}
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Gets a Listing of subreddits relating to the current user.
	 * @param query Query to get subreddits with. Required: where; Optional: after, before, ct, limit, showAll
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void mine(Map query, Closure callback, Closure errorHandler = null) {
		Map q = [where: SubredditRole.SUBSCRIBER, after: null, before: null, ct: 0, limit: 25, showAll: false]
		q << query
		HttpRequest request = new HttpRequest(location: "subreddits/mine/${q.where.name().toLowerCase()}", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.after = q.after
		request.parameters.before = q.before
		request.parameters.count = q.ct
		request.parameters.limit = q.limit
		if(q.showAll) {
			request.parameters.show = "all"
		}

		this.session.makeRequest(request, {
			callback(new Listing(it))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Searches for a Listing of subreddits.
	 * @param query Query to search for subreddits with. Required: query; Optional: after, before, ct, limit, showAll
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void search(Map query, Closure callback, Closure errorHandler = null) {
		Map q = [query: "", after: null, before: null, ct: 0, limit: 25, showAll: false]
		q << query
		HttpRequest request = new HttpRequest(location: "subreddits/search", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.after = q.after
		request.parameters.before = q.before
		request.parameters.count = q.ct
		request.parameters.limit = q.limit
		request.parameters.q = q.query
		if(q.showAll) {
			request.parameters.show = "all"
		}

		this.session.makeRequest(request, {
			callback(new Listing(it))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Gets a sorted Listing of subreddits.
	 * @param query Query to get subreddits with. Required: where; Optional: after, before, ct, limit, showAll
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void list(Map query, Closure callback, Closure errorHandler = null) {
		Map q = [where: SubredditSearchType.NEW, after: null, before: null, ct: 0, limit: 25, showAll: false]
		q << query
		HttpRequest request = new HttpRequest(location: "subreddits/${q.where.name().toLowerCase()}", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.after = q.after
		request.parameters.before = q.before
		request.parameters.count = q.ct
		request.parameters.limit = q.limit
		if(q.showAll) {
			request.parameters.show = "all"
		}

		this.session.makeRequest(request, {
			callback(new Listing(it))
		}, errorHandler, ResponseType.JSON)
	}
}
