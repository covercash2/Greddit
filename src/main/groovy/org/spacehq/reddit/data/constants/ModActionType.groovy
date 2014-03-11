package org.spacehq.reddit.data.constants

/**
 * The type of action a moderator has performed.
 */
enum ModActionType {
	/**
	 * Banning a user.
	 */
	BANUSER,
	/**
	 * Unbanning a user.
	 */
	UNBANUSER,
	/**
	 * Removing a link.
	 */
	REMOVELINK,
	/**
	 * Approving a link.
	 */
	APPROVELINK,
	/**
	 * Removing a comment.
	 */
	REMOVECOMMENT,
	/**
	 * Approving a comment.
	 */
	APPROVECOMMENT,
	/**
	 * Adding a moderator.
	 */
	ADDMODERATOR,
	/**
	 * Inviting a moderator.
	 */
	INVITEMODERATOR,
	/**
	 * Uninviting a moderator.
	 */
	UNINVITEMODERATOR,
	/**
	 * Accepting a moderator invite.
	 */
	ACCEPTMODERATORINVITE,
	/**
	 * Removing a moderator.
	 */
	REMOVEMODERATOR,
	/**
	 * Adding a contributor.
	 */
	ADDCONTRIBUTOR,
	/**
	 * Removing a contributor.
	 */
	REMOVECONTRIBUTOR,
	/**
	 * Editing settings.
	 */
	EDITSETTINGS,
	/**
	 * Editing a flair.
	 */
	EDITFLAIR,
	/**
	 * Distinguishing a user.
	 */
	DISTINGUISH,
	/**
	 * Marking something as NSFW.
	 */
	MARKNSFW,
	/**
	 * Banning a user from the wiki.
	 */
	WIKIBANNED,
	/**
	 * Setting a user as a wiki contributor.
	 */
	WIKICONTRIBUTOR,
	/**
	 * Unbanning a user from the wiki.
	 */
	WIKIUNBANNED,
	/**
	 * Listing a wiki page.
	 */
	WIKIPAGELISTED,
	/**
	 * Removing a wiki contributor.
	 */
	REMOVEWIKICONTRIBUTOR,
	/**
	 * Revising a wiki page.
	 */
	WIKIREVISE,
	/**
	 * Setting the wiki permission level.
	 */
	WIKIPERMLEVEL,
	/**
	 * Setting a post to ignore reports.
	 */
	IGNOREREPORTS,
	/**
	 * Setting a post to no longer ignore reports.
	 */
	UNIGNOREREPORTS,
	/**
	 * Setting a user's permissions.
	 */
	SETPERMISSIONS,
	/**
	 * Stickying a post.
	 */
	STICKY,
	/**
	 * Unstickying a post.
	 */
	UNSTICKY
}
