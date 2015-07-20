package org.spacehq.reddit.data.constants

/**
 * The type of a subreddit.
 */
enum SubredditType {
    /**
     * Public subreddit.
     */
    PUBLIC,
    /**
     * Private subreddit.
     */
            PRIVATE,
    /**
     * Restricted subreddit.
     */
            RESTRICTED,
    /**
     * Gold-only subreddit.
     */
            GOLD_RESTRICTED,
    /**
     * Archived subreddit.
     */
            ARCHIVED
}
