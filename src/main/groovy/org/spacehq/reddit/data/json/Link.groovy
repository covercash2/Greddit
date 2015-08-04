package org.spacehq.reddit.data.json

import groovy.transform.ToString
import org.spacehq.reddit.data.constants.DistinguishType
import org.spacehq.reddit.util.DataUtil

/**
 * A Reddit link.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
public class Link {
    /**
     * ID of the link.
     */
    String id
    /**
     * Full name of the link.
     */
    String name
    /**
     * Upvotes of the link.
     */
    int ups
    /**
     * Downvotes of the link.
     */
    int downs
    /**
     * Whether the current logged in user likes the link.
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
     * Whether the current logged in user has clicked the link.
     */
    boolean clicked
    /**
     * Whether the current logged in user has visited the link.
     */
    boolean visited
    /**
     * The domain of the link.
     */
    String domain
    /**
     * Whether the link is hidden.
     */
    boolean hidden
    /**
     * Whether the link is archived
     */
    boolean archived
    /**
     * Whether the link is a self-post.
     */
    boolean is_self
    /**
     * CSS class of the link flair.
     */
    String link_flair_css_class
    /**
     * Text of the link flair.
     */
    String link_flair_text
    /**
     * Media data of the link.
     */
    Map media
    /**
     * Embedded media data of the link.
     */
    Map media_embed
    /**
     * Secure media of the link.
     */
    Map secure_media
    /**
     * Embedded secure media of the link.
     */
    Map secure_media_embed
    /**
     * Number of comments on the link.
     */
    int num_comments
    /**
     * Number of times the link has been reported.
     */
    int num_reports
    /**
     * Whether the link is marked as NSFW.
     */
    boolean over_18
    /**
     * Permanent link to the link.
     */
    String permalink
    /**
     * Whether the current logged in user has saved the link.
     */
    boolean saved
    /**
     * Score of the link.
     */
    int score
    /**
     * Self text of the link.
     */
    String selftext
    /**
     * Self text of the link in HTML.
     */
    String selftext_html
    /**
     * Subreddit of the link.
     */
    String subreddit
    /**
     * ID of the link's subreddit.
     */
    String subreddit_id
    /**
     * Thumbnail of the link.
     */
    String thumbnail
    /**
     * Title of the link.
     */
    String title
    /**
     * URL of the link.
     */
    String url
    /**
     * User who removed the link.
     */
    String banned_by
    /**
     * User who approved the link.
     */
    String approved_by
    /**
     * When the link was last edited.
     */
    long edited
    /**
     * The type of distinguishment the link has.
     */
    DistinguishType distinguished
    /**
     * Whether the link is stickied.
     */
    boolean stickied
    /**
     * from ?
     */
    String from
    /**
     * from id ?
     */
    String from_id
    /**
     * from kind ?
     */
    String from_kind
    /**
     * gilded
     */
    boolean gilded
    /**
     * mod reports ?
     */
    List mod_reports
    /**
     * post_hint
     */
    String post_hint
    /**
     * preview ?
     */
    Map preview
    /**
     * removal reason
     */
    String removal_reason
    /**
     * report reasons
     */
    List report_reasons
    /**
     * suggested sort
     */
    String suggested_sort
    /**
     * user reports
     */
    List user_reports
    /**
     * hide score ?
     */
    boolean hide_score

    /**
     * Creates a new Link instance.
     * @param map Map of data.
     */
    Link(Map map) {
        if(map.data != null && map.data instanceof Map) {
            map = map.data
        }

        DataUtil.fixTypes(this.getClass(), map).each { k, v ->
            this[k] = v
        }
    }
}
