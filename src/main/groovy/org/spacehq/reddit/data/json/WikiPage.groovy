package org.spacehq.reddit.data.json

import groovy.transform.ToString
import org.spacehq.reddit.util.DataUtil

/**
 * A Reddit wiki page.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class WikiPage {
    /**
     * Whether the wiki page can be revised.
     */
    boolean may_revise
    /**
     * The date of the wiki page's revision.
     */
    long revision_date
    /**
     * The account that revised the wiki page.
     */
    Account revision_by
    /**
     * The content of the wiki page in HTML.
     */
    String content_html
    /**
     * The content of the wiki page in Markdown.
     */
    String content_md

    /**
     * Creates a new WikiPage instance.
     * @param map Map of data.
     */
    WikiPage(Map map) {
        if(map.data != null && map.data instanceof Map) {
            map = map.data
        }

        DataUtil.fixTypes(this.getClass(), map).each { k, v ->
            this[k] = v
        }
    }
}
