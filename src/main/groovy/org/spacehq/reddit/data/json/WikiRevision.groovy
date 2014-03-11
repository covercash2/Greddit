package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import groovy.transform.ToString

/**
 * A Reddit wiki revision.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class WikiRevision {
	/**
	 * ID of the revision.
	 */
	String id
	/**
	 * Timestamp of the revision.
	 */
	long timestamp
	/**
	 * Reason for the revision.
	 */
	String reason
	/**
	 * Page of the revision.
	 */
	String page
	/**
	 * Author of the revision.
	 */
	Account author

	/**
	 * Creates a new WikiRevision instance.
	 * @param map Map of data.
	 */
	WikiRevision(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			this[k] = v
		}
	}
}
