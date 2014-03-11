package org.spacehq.reddit.data.json
import groovy.transform.ToString
import org.spacehq.gcommons.util.DataUtil
import org.spacehq.reddit.data.constants.ModActionType

/**
 * A Reddit mod action.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class ModAction {
	/**
	 * Description of the action.
	 */
	String description
	/**
	 * ID36 of the action's moderator.
	 */
	String mod_id36
	/**
	 * Created time in UTC.
	 */
	long created_utc
	/**
	 * Subreddit of the action.
	 */
	String subreddit
	/**
	 * ID36 of the action's subreddit.
	 */
	String sr_id36
	/**
	 * Details of the action.
	 */
	String details
	/**
	 * Type of action performed,
	 */
	ModActionType action
	/**
	 * Full name of the action's target.
	 */
	String target_fullname
	/**
	 * ID of the action.
	 */
	String id
	/**
	 * Moderator that performed the action.
	 */
	String mod

	/**
	 * Creates a new ModAction instance.
	 * @param map Map of data.
	 */
	ModAction(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			this[k] = v
		}
	}
}
