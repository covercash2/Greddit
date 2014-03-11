package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import groovy.transform.ToString

/**
 * A user account.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class Account {
	/**
	 * ID of the account.
	 */
	String id
	/**
	 * Full name of the account.
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
	 * Comment karma of the account.
	 */
	int comment_karma
	/**
	 * Whether the account has mail.
	 */
	boolean has_mail
	/**
	 * Whether the account has mod mail.
	 */
	boolean has_mod_mail
	/**
	 * Whether the account has a verified email.
	 */
	boolean has_verified_email
	/**
	 * Whether the account is a friend.
	 */
	boolean is_friend
	/**
	 * Whether the account is gold.
	 */
	boolean is_gold
	/**
	 * Whether the account is a mod.
	 */
	boolean is_mod
	/**
	 * Link karma of the account.
	 */
	int link_karma
	/**
	 * Modhash of the account.
	 */
	String modhash
	/**
	 * Whether the account is over 18 years old.
	 */
	boolean over_18

	/**
	 * Creates a new Account instance.
	 * @param map Map of data.
	 */
	Account(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			this[k] = v
		}
	}
}
