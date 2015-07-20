package org.spacehq.reddit.data.json

import groovy.transform.ToString
import org.spacehq.reddit.data.constants.WikiPermissionLevel
import org.spacehq.reddit.util.DataUtil

/**
 * A Reddit wiki page's settings.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class WikiPageSettings {
    /**
     * Editors of the wiki page.
     */
    List editors
    /**
     * Whether the wiki page is listed.
     */
    boolean listed
    /**
     * The permission level required to edit the wiki page.
     */
    WikiPermissionLevel permlevel

    /**
     * Creates a new WikiPageSettings instance.
     * @param map Map of data.
     */
    WikiPageSettings(Map map) {
        if(map.data != null && map.data instanceof Map) {
            map = map.data
        }

        DataUtil.fixTypes(this.getClass(), map).each { k, v ->
            if(k == "editors") {
                this[k] = v.collect { new Account(it) }
            } else {
                this[k] = v
            }
        }
    }
}
