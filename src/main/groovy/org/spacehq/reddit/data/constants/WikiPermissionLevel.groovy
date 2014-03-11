package org.spacehq.reddit.data.constants

/**
 * The permission level of a wiki.
 */
enum WikiPermissionLevel {
	/**
	 * Based on subreddit permissions.
	 */
	SUBREDDIT_PERMISSIONS,
	/**
	 * Approved contributors.
	 */
	APPROVED_CONTRIBUTORS,
	/**
	 * Moderators.
	 */
	MODS
}
