package org.spacehq.reddit

import org.spacehq.gcommons.http.HttpSession
import org.spacehq.gcommons.http.RequestException
import org.spacehq.reddit.apis.*

/**
 * The base of a Reddit client.
 */
class Reddit {
	private final HttpSession session

	/**
	 * Reddit's standard authentication API.
	 */
	final RedditAuthAPI redditAuth
	/**
	 * Reddit's OAuth authentication API.
	 */
	final RedditOAuthAPI redditOAuth

	/**
	 * Reddit's app API.
	 */
	final AppAPI app
	/**
	 * Reddit's captcha API.
	 */
	final CaptchaAPI captcha
	/**
	 * Reddit's comment API.
	 */
	final CommentAPI comment
	/**
	 * Reddit's flair API.
	 */
	final FlairAPI flair
	/**
	 * Reddit's listing API.
	 */
	final ListingAPI listing
	/**
	 * Reddit's message API.
	 */
	final MessageAPI message
	/**
	 * Reddit's moderation API.
	 */
	final ModerationAPI moderation
	/**
	 * Reddit's multis (multiple subreddits in one) API.
	 */
	final MultisAPI multis
	/**
	 * Reddit's search API.
	 */
	final SearchAPI search
	/**
	 * Reddit's subreddit API.
	 */
	final SubredditAPI subreddit
	/**
	 * Reddit's user API.
	 */
	final UserAPI user
	/**
	 * Reddit's wiki API.
	 */
	final WikiAPI wiki

	/**
	 * Creates a new Reddit client instance.
	 * @param userAgent User Agent of the client. Must be unique as per Reddit API rules.
	 */
	Reddit(String userAgent) {
		this.session = new HttpSession()
		this.session.baseUrl = "ssl.reddit.com"
		this.session.properties."User-Agent" = userAgent
		this.session.initialJsonHandler = { session, json ->
			if(json instanceof Map) {
				if(json.fields != null && json.explanation != null && json.reason != null) {
					throw new RequestException("${json.fields}', '${json.explanation}', '${json.reason}")
				}

				if(json.json != null) {
					if(json.json.errors.size() > 0) {
						throw new RequestException("${json.json.errors}")
					}

					if(json.json.data != null) {
						if(json.json.data.modhash != null) {
							session.properties."X-Modhash" = json.json.data.modhash
						}

						if(json.json.data.cookie != null) {
							session.properties."Cookie" = "reddit_session=${json.json.data.cookie}"
						}
					}
				}
			}
		}

		this.ignoreCallLimit = false

		this.redditAuth = new RedditAuthAPI(this.session)
		this.redditOAuth = new RedditOAuthAPI(this.session)

		this.app = new AppAPI(this.session)
		this.captcha = new CaptchaAPI(this.session)
		this.comment = new CommentAPI(this.session)
		this.flair = new FlairAPI(this.session)
		this.listing = new ListingAPI(this.session)
		this.message = new MessageAPI(this.session)
		this.moderation = new ModerationAPI(this.session)
		this.multis = new MultisAPI(this.session)
		this.search = new SearchAPI(this.session)
		this.subreddit = new SubredditAPI(this.session)
		this.user = new UserAPI(this.session)
		this.wiki = new WikiAPI(this.session)
	}

	/**
	 * Sets the global error handler callback to call when a call-specific error handler isn't specified.
	 * @param handler Error handler to set.
	 */
	void setGlobalErrorHandler(Closure handler) {
		this.session.errorHandler = handler
	}

	/**
	 * Sets whether the client should ignore the API two-seconds-per-call limit in its code.
	 * @param ignore Whether to ignore the API limit.
	 */
	void setIgnoreCallLimit(boolean ignore) {
		this.session.millisBetweenCalls = ignore ? 0 : 2 * 1000
	}

	/**
	 * Disposes of the Reddit API client instance, closing the scheduler thread.
	 */
	void dispose() {
		this.session.stop()
	}
}
