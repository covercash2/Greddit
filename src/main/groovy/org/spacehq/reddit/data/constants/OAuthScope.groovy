package org.spacehq.reddit.data.constants

/**
 * The scope of an OAuth token.
 */
enum OAuthScope {
    /**
     * Editing.
     */
    EDIT,
    /**
     * Modifying flairs.
     */
            FLAIR,
    /**
     * Viewing history.
     */
            HISTORY,
    /**
     * Viewing identity.
     */
            IDENTITY,
    /**
     * Configuring options.
     */
            MODCONFIG,
    /**
     * Configuring flairs.
     */
            MODFLAIR,
    /**
     * Viewing moderator logs.
     */
            MODLOG,
    /**
     * Moderating posts.
     */
            MODPOSTS,
    /**
     * Moderating wikis.
     */
            MODWIKI,
    /**
     * Viewing private messages.
     */
            PRIVATEMESSAGES,
    /**
     * Reading Reddit info.
     */
            READ,
    /**
     * Reporting posts.
     */
            REPORT,
    /**
     * Saving posts.
     */
            SAVE,
    /**
     * Submitting posts.
     */
            SUBMIT,
    /**
     * Subscribing to subreddits.
     */
            SUBSCRIBE,
    /**
     * Voting.
     */
            VOTE,
    /**
     * Editing wikis.
     */
            WIKIEDIT,
    /**
     * Reading wikis.
     */
            WIKIREAD
}
