package org.spacehq.reddit.data.json

import groovy.transform.ToString
import org.spacehq.reddit.util.DataUtil

/**
 * A Reddit subreddit's traffic statistics.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class TrafficStatistics {
    /**
     * Hour-based statistics.
     */
    List hour
    /**
     * Day-based statistics.
     */
    List day
    /**
     * Month-based statistics.
     */
    List month

    /**
     * Creates a new TrafficStatistics instance.
     * @param map Map of data.
     */
    TrafficStatistics(Map map) {
        if(map.data != null && map.data instanceof Map) {
            map = map.data
        }

        DataUtil.fixTypes(this.getClass(), map).each { k, v ->
            if(k == "hour" || k == "day" || k == "month") {
                this[k] = v.collect { new TrafficStatistic(it) }
            } else {
                this[k] = v
            }
        }
    }
}
