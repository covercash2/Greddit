package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import groovy.transform.ToString

/**
 * A Reddit more comments placeholder.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class More {
	/**
	 * ID of the more instance.
	 */
	String id
	/**
	 * Full name of the more instance.
	 */
	String name
	/**
	 * Count of comments in the more instance.
	 */
	int count
	/**
	 * ID of the more instance's parent.
	 */
	String parent_id
	/**
	 * Children belonging to the more instance.
	 */
	List children

	/**
	 * Creates a new More instance.
	 * @param map Map of data.
	 */
	More(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			this[k] = v
		}
	}
}
