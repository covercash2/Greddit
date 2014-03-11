package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import groovy.transform.ToString

/**
 * A Reddit flair selector.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class FlairSelector {
	/**
	 * Flair choices.
	 */
	List choices
	/**
	 * The current flair.
	 */
	Flair current

	/**
	 * Creates a new FlairSelector instance.
	 * @param map Map of data.
	 */
	FlairSelector(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			if(k == "choices") {
				this[k] = v.collect { new Flair(it) }
			} else {
				this[k] = v
			}
		}
	}
}
