package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import groovy.transform.ToString

/**
 * A Reddit stylesheet.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class Stylesheet {
	/**
	 * Images belonging to the stylesheet.
	 */
	List images
	/**
	 * ID of the subreddit that the stylesheet belongs to.
	 */
	String subreddit_id
	/**
	 * Previous stylesheet data.
	 */
	String prevstyle
	/**
	 * Stylesheet data.
	 */
	String stylesheet

	/**
	 * Creates a new Stylesheet instance.
	 * @param map Map of data.
	 */
	Stylesheet(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			if(k == "images") {
				this[k] = v.collect { new Image(it) }
			} else {
				this[k] = v
			}
		}
	}
}
