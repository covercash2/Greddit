package org.spacehq.reddit.data.json

import groovy.transform.ToString
import org.spacehq.reddit.util.DataUtil

/**
 * A Reddit listing.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class Listing {
    /**
     * The ID of the element before the listing.
     */
    String before
    /**
     * The ID of the element after tje listing.
     */
    String after
    /**
     * The modhash of the listing.
     */
    String modhash
    /**
     * The children of the listing.
     */
    List children

    /**
     * Creates a new Listing instance.
     * @param map Map of data.
     */
    Listing(Map map) {
        if(map.data != null && map.data instanceof Map) {
            map = map.data
        }

        DataUtil.fixTypes(this.getClass(), map).each { k, v ->
            if(k == "children") {
                this[k] = v.collect {
                    if(it.kind == null) {
                        new WikiRevision(it)
                    } else if(it.kind == "Listing") {
                        new Listing(it)
                    } else if(it.kind == "LabeledMulti") {
                        new LabeledMulti(it)
                    } else if(it.kind == "LabeledMultiDescription") {
                        new LabeledMultiDescription(it)
                    } else if(it.kind == "more") {
                        new More(it)
                    } else if(it.kind == "modaction") {
                        new ModAction(it)
                    } else if(it.kind == "subreddit_settings") {
                        new SubredditSettings(it)
                    } else if(it.kind == "wikipage") {
                        new WikiPage(it)
                    } else if(it.kind == "wikipagelisting") {
                        new WikiPageListing(it)
                    } else if(it.kind == "wikipagesettings") {
                        new WikiPageSettings(it)
                    } else if(it.kind == "t1") {
                        if((it.data && it.data.was_comment) || it.was_comment) {
                            new Message(it)
                        } else {
                            new Comment(it)
                        }
                    } else if(it.kind == "t2") {
                        new Account(it)
                    } else if(it.kind == "t3") {
                        new Link(it)
                    } else if(it.kind == "t4") {
                        new Message(it)
                    } else if(it.kind == "t5") {
                        new Subreddit(it)
                    }
                }
            } else {
                this[k] = v
            }
        }
    }
}
