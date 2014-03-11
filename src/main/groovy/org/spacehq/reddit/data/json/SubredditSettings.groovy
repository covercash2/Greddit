package org.spacehq.reddit.data.json
import groovy.transform.ToString
import org.spacehq.gcommons.util.DataUtil
import org.spacehq.reddit.data.constants.SubmissionType
import org.spacehq.reddit.data.constants.SubredditFilterStrength
import org.spacehq.reddit.data.constants.SubredditType
import org.spacehq.reddit.data.constants.SubredditWikiMode
/**
 * A Reddit subreddit's settings.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class SubredditSettings {
	/**
	 * Whether the subreddit can be shown in the default set of subreddits.
	 */
	boolean default_set
	/**
	 * ID of the subreddit.
	 */
	String subreddit_id
	/**
	 * Domain of the subreddit.
	 */
	String domain
	/**
	 * Whether to show media in the subreddit.
	 */
	boolean show_media
	/**
	 * The minimum account age to edit the wiki.
	 */
	int wiki_edit_age
	/**
	 * The text to show on the submission page.
	 */
	String submit_text
	/**
	 * The strength of the link spam filter.
	 */
	SubredditFilterStrength spam_links
	/**
	 * The strength of the comment spam filter.
	 */
	SubredditFilterStrength spam_comments
	/**
	 * The strength of the self-post spam filter.
	 */
	SubredditFilterStrength spam_selfposts
	/**
	 * The title of the subreddit.
	 */
	String title
	/**
	 * The wiki mode of the subreddit.
	 */
	SubredditWikiMode wikimode
	/**
	 * Whether the subreddit is marked as NSFW.
	 */
	boolean over_18
	/**
	 * The previous description ID of the subreddit.
	 */
	String prev_description_id
	/**
	 * The description of the subreddit.
	 */
	String description
	/**
	 * Label of the submit link button.
	 */
	String submit_link_label
	/**
	 * Previous public description ID of the subreddit.
	 */
	String prev_public_description_id
	/**
	 * CSS of the subreddit's domain.
	 */
	String domain_css
	/**
	 * Subreddit's domain sidebar.
	 */
	String domain_sidebar
	/**
	 * Label of the submit text button.
	 */
	String submit_text_label
	/**
	 * Language of the subreddit.
	 */
	String language
	/**
	 * Karma required to edit the wiki.
	 */
	int wiki_edit_karma
	/**
	 * Text to show when hovering over the header.
	 */
	String header_hover_text
	/**
	 * Whether the traffic page is public.
	 */
	boolean public_traffic
	/**
	 * Public description of the subreddit.
	 */
	String public_description
	/**
	 * Previous submit text ID of the subreddit.
	 */
	String prev_submit_text_id
	/**
	 * Minimum comment score to hide comments.
	 */
	int comment_score_hide_mins
	/**
	 * Type of the subreddit.
	 */
	SubredditType subreddit_type
	/**
	 * Whether to exclude site-wide bans.
	 */
	boolean exclude_banned_modqueue
	/**
	 * Allowed types of content in the subreddit.
	 */
	SubmissionType content_options

	/**
	 * Creates a new SubredditSettings instance.
	 * @param map Map of data.
	 */
	SubredditSettings(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			this[k] = v
		}
	}
}
