package org.spacehq.reddit.data.json

import groovy.transform.ToString

/**
 * A Reddit wiki page listing.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class WikiPageListing {
	/**
	 * List of pages.
	 */
	List pages

	/**
	 * Creates a new WikiPageListing instance.
	 * @param data Data (Map with data list inside or data list).
	 */
	WikiPageListing(def data) {
		if(data instanceof List) {
			this.pages = data
		} else {
			this.pages = data.data
		}
	}
}
