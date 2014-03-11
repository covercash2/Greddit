package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import groovy.transform.ToString

/**
 * A Reddit multi's subreddit.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class LabeledMultiSubreddit {
	/**
	 * Name of the subreddit.
	 */
	String name

	/**
	 * Creates a new LabeledMultiSubreddit instance.
	 * @param map Map of data.
	 */
	LabeledMultiSubreddit(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			this[k] = v
		}
	}
}
