package org.spacehq.reddit.data.json

import groovy.transform.ToString
import org.spacehq.reddit.util.DataUtil

/**
 * A Reddit message.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class Message {
    /**
     * ID of the message.
     */
    String id
    /**
     * Full name of the message.
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
     * Author of the message.
     */
    String author
    /**
     * Body of the message.
     */
    String body
    /**
     * Body of the message in HTML.
     */
    String body_html
    /**
     * Context of the message.
     */
    String context
    /**
     * Destination of the message.
     */
    String dest
    /**
     * First message in the chain of messages.
     */
    String first_message
    /**
     * Name of the firt message.
     */
    String first_message_name
    /**
     * Whether the current logged in user likes the message.
     */
    boolean likes
    /**
     * Title of the message's link.
     */
    String link_title
    /**
     * Whether the message is new.
     */
    boolean is_new
    /**
     * ID of the message's parent.
     */
    String parent_id
    /**
     * Replies to the message.
     */
    String replies
    /**
     * Subject of the message.
     */
    String subject
    /**
     * Subreddit of the message.
     */
    String subreddit
    /**
     * Whether the message was of a comment.
     */
    boolean was_comment

    /**
     * Creates a new Message instance.
     * @param map Map of data.
     */
    Message(Map map) {
        if(map.data != null && map.data instanceof Map) {
            map = map.data
        }

        map.is_new = map.new
        map.new = null
        DataUtil.fixTypes(this.getClass(), map).each { k, v ->
            this[k] = v
        }
    }
}
