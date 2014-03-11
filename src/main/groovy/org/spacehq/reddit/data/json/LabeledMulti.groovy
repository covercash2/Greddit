package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import groovy.transform.ToString
import org.spacehq.reddit.data.constants.MultiVisibility

/**
 * A Reddit multi.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class LabeledMulti {
	/**
	 * Created time.
	 */
	long created
	/**
	 * Created time in UTC.
	 */
	long created_utc
	/**
	 * Whether the multi can be edited.
	 */
	boolean can_edit
	/**
	 * Name of the multi.
	 */
	String name
	/**
	 * Subreddits in the multi.
	 */
	List subreddits
	/**
	 * Visibility of the multi.
	 */
	MultiVisibility visibility
	/**
	 * Path of the multi.
	 */
	String path

	/**
	 * Creates a new LabeledMulti instance.
	 * @param map Map of data.
	 */
	LabeledMulti(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			if(k == "subreddits") {
				this[k] = v.collect { new LabeledMultiSubreddit(it) }
			} else {
				this[k] = v
			}
		}
	}
}
