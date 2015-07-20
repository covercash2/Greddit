package org.spacehq.reddit.data.constants

/**
 * The type of a user.
 */
enum UserType {
    /**
     * Moderator.
     */
    MODERATOR,
    /**
     * Invited moderator.
     */
            MODERATOR_INVITE,
    /**
     * Contributor.
     */
            CONTRIBUTOR,
    /**
     * Banned.
     */
            BANNED,
    /**
     * Banned from the wiki.
     */
            WIKIBANNED,
    /**
     * Wiki contributor.
     */
            WIKICONTRIBUTOR
}
