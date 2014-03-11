package org.spacehq.reddit.data.json

import org.spacehq.gcommons.util.DataUtil
import groovy.transform.ToString

/**
 * A Reddit multi's description.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class LabeledMultiDescription {
	/**
	 * Body of the description in HTML.
	 */
	String body_html
	/**
	 * Body of the description in Markdown.
	 */
	String body_md

	/**
	 * Creates a new LabeledMultiDescription instance.
	 * @param map Map of data.
	 */
	LabeledMultiDescription(Map map) {
		if(map.data != null && map.data instanceof Map) {
			map = map.data
		}

		DataUtil.fixTypes(this.getClass(), map).each { k, v ->
			this[k] = v
		}
	}
}
