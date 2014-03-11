package org.spacehq.reddit.data.json

import groovy.transform.ToString

/**
 * A Reddit subreddit's traffic statistic.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class TrafficStatistic {
	/**
	 * Time of the statistic.
	 */
	long time
	/**
	 * Unique views.
	 */
	int uniques
	/**
	 * Page views.
	 */
	int pageviews
	/**
	 * New subscriptions.
	 */
	int subscriptions

	/**
	 * Creates a new TrafficStatistic instance.
	 * @param data List of data.
	 */
	TrafficStatistic(List data) {
		data.eachWithIndex {obj, i ->
			if(obj == null) {
				obj = -1
			}

			if(i == 0) {
				this.time = obj
			} else if(i == 1) {
				this.uniques = obj
			} else if(i == 2) {
				this.pageviews = obj
			} else if(i == 3) {
				this.subscriptions = obj
			}
		}
	}
}
