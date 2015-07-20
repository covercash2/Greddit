package org.spacehq.reddit.data.json

import groovy.transform.ToString
import org.spacehq.reddit.data.constants.FlairPosition
import org.spacehq.reddit.util.DataUtil

/**
 * A Reddit flair.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class Flair {
    /**
     * Position of the flair.
     */
    FlairPosition flair_position
    /**
     * Template ID of the flair.
     */
    String flair_template_id
    /**
     * Text of the flair.
     */
    String flair_text
    /**
     * Whether the flair's text is editable.
     */
    boolean flair_text_editable
    /**
     * CSS class of the flair.
     */
    String flair_css_class

    /**
     * Creates a new Flair instance.
     * @param map Map of data.
     */
    Flair(Map map) {
        if(map.data != null && map.data instanceof Map) {
            map = map.data
        }

        DataUtil.fixTypes(this.getClass(), map).each { k, v ->
            this[k] = v
        }
    }
}
