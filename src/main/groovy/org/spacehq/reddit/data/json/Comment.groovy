package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import org.spacehq.reddit.data.constants.DistinguishType
import groovy.transform.ToString

/**
 * A Reddit comment.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class Comment {
	/**
	 * ID of the comment.
	 */
	String id
	/**
	 * Full name of the comment.
	 */
	String name
	/**
	 * Upvotes of the comment.
	 */
	int ups
	/**
	 * Downvotes of the comment.
	 */
	int downs
	/**
	 * Whether the current logged in user likes the comment.
	 */
	boolean likes
	/**
	 * Created time.
	 */
	long created
	/**
	 * Created time in UTC.
	 */
	long created_utc
	/**
	 * User who approved the comment.
	 */
	String approved_by
	/**
	 * Author of the comment,
	 */
	String author
	/**
	 * CSS class of the author's flair.
	 */
	String author_flair_css_class
	/**
	 * Text of the author's flair.
	 */
	String author_flair_text
	/**
	 * User who removed the comment.
	 */
	String banned_by
	/**
	 * Body of the comment.
	 */
	String body
	/**
	 * Body of the comment in HTML.
	 */
	String body_html
	/**
	 * When the comment was edited.
	 */
	long edited
	/**
	 * Number of times the comment was gilded.
	 */
	int gilded
	/**
	 * Author of the parent link.
	 */
	String link_author
	/**
	 * ID of the parent link.
	 */
	String link_id
	/**
	 * Title of the parent link.
	 */
	String link_title
	/**
	 * Number of times the comment has been reported.
	 */
	int num_reports
	/**
	 * ID of the comment's parent.
	 */
	String parent_id
	/**
	 * Whether the current logged in user has saved the comment.
	 */
	boolean saved
	/**
	 * Whether the comment's score is hidden.
	 */
	boolean score_hidden
	/**
	 * Subreddit of the comment.
	 */
	String subreddit
	/**
	 * ID of the comment's subreddit.
	 */
	String subreddit_id
	/**
	 * The type of distinguishment this comment has received.
	 */
	DistinguishType distinguished
	/**
	 * Replies to this comment.
	 */
	Listing replies

	/**
	 * Creates a new Comment instance.
	 * @param map Map of data.
	 */
	Comment(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			if(k == "distinguished" && v == null) {
				this[k] = DistinguishType.NOT_DISTINGUISHED
			} else {
				this[k] = v
			}
		}
	}
}
