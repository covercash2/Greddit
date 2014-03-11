package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import org.spacehq.reddit.data.constants.SubmissionType
import org.spacehq.reddit.data.constants.SubredditType
import groovy.transform.ToString

/**
 * A Reddit subreddit.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class Subreddit {
	/**
	 * ID of the subreddit.
	 */
	String id
	/**
	 * Full name of the subreddit.
	 */
	String name
	/**
	 * Created time.
	 */
	long created
	/**
	 * Created time in UTC.
	 */
	long created_utc
	/**
	 * Number of accounts active in the subreddit.
	 */
	int accounts_active
	/**
	 * Minimum comment score to hide comments in the subreddit.
	 */
	int comment_score_hide_mins
	/**
	 * Description of the subreddit.
	 */
	String description
	/**
	 * Description of the subreddit in HTML.
	 */
	String description_html
	/**
	 * Display name of the subreddit.
	 */
	String display_name
	/**
	 * Header image of the subreddit.
	 */
	String header_img
	/**
	 * Header size of the subreddit. (width, height)
	 */
	int[] header_size
	/**
	 * Header title of the subreddit.
	 */
	String header_title
	/**
	 * Whether the subreddit is marked as NSFW.
	 */
	boolean over18
	/**
	 * Public description of the subreddit.
	 */
	String public_description
	/**
	 * Whether the traffic page of the subreddit is public.
	 */
	boolean public_traffic
	/**
	 * Number of subscribers to the subreddit.
	 */
	long subscribers
	/**
	 * Submission types allowed in the subreddit.
	 */
	SubmissionType submission_type
	/**
	 * Label of the submit link button.
	 */
	String submit_link_label
	/**
	 * Label of the submit text button.
	 */
	String submit_text_label
	/**
	 * Text to show on the submission page.
	 */
	String submit_text
	/**
	 * Text to show on the submission page in HTML.
	 */
	String submit_text_html
	/**
	 * Type of the subreddit.
	 */
	SubredditType subreddit_type
	/**
	 * Title of the subreddit.
	 */
	String title
	/**
	 * URL of the subreddit.
	 */
	String url
	/**
	 * Whether the current logged in user is banned from the subreddit.
	 */
	boolean user_is_banned
	/**
	 * Whether the current logged in user is a contributor in the subreddit.
	 */
	boolean user_is_contributor
	/**
	 * Whether the current logged in user is a moderator in the subreddit.
	 */
	boolean user_is_moderator
	/**
	 * Whether the current logged in user is a subscriber to the subreddit.
	 */
	boolean user_is_subscriber

	/**
	 * Creates a new Subreddit instance.
	 * @param map Map of data.
	 */
	Subreddit(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			this[k] = v
		}
	}
}
