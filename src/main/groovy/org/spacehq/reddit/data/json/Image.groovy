package org.spacehq.reddit.data.json

import org.spacehq.reddit.util.DataUtil

/**
 * A Reddit image.
 */
class Image {
    /**
     * URL of the image.
     */
    String url
    /**
     * Link to the image.
     */
    String link
    /**
     * Name of the image.
     */
    String name

    /**
     * Creates a new Image instance.
     * @param map Map of data.
     */
    Image(Map map) {
        if(map.data != null && map.data instanceof Map) {
            map = map.data
        }

        DataUtil.fixTypes(this.getClass(), map).each { k, v ->
            this[k] = v
        }
    }
}
