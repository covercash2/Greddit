package org.spacehq.reddit.apis

import org.spacehq.reddit.data.constants.FlairPosition
import org.spacehq.reddit.data.constants.FlairType
import org.spacehq.reddit.data.json.FlairSelector
import org.spacehq.reddit.data.json.Listing
import org.spacehq.reddit.util.http.HttpRequest
import org.spacehq.reddit.util.http.HttpSession
import org.spacehq.reddit.util.http.ResponseType

/**
 * Reddit flair API methods.
 */
class FlairAPI {
    private final HttpSession session

    /**
     * Creates a new FlairAPI instance.
     * @param session HttpSession to use.
     */
    FlairAPI(HttpSession session) {
        this.session = session
    }

    /**
     * Clears a subreddit's flair templates.
     * @param subreddit Subreddit to clear the templates of.
     * @param type Type of flair to clear.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void clearFlairTemplates(String subreddit, FlairType type, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/deleteflair", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.flair_type = type.name()
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Deletes a user's subreddit flair.
     * @param subreddit Subreddit to delete the flair in.
     * @param user User to delete the flair of.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void deleteFlair(String subreddit, String user, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/deleteflair", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.name = user
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Deletes a flair template.
     * @param subreddit Subreddit to delete the flair template in.
     * @param templateId ID of the template to delete.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void deleteFlairTemplate(String subreddit, String templateId, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/deleteflairtemplate", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.flair_template_id = templateId
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Creates a flair.
     * @param subreddit Subreddit to create the flair in.
     * @param cssClass CSS class to use for the flair.
     * @param link Link to apply to the flair,
     * @param user User to apply the flair to.
     * @param text Text to put in the flair.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void flair(String subreddit, String cssClass, String link, String user, String text, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/flair", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.css_class = cssClass
        request.parameters.link = link
        request.parameters.name = user
        request.parameters.text = text
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Configures a subreddit's flairs.
     * @param subreddit Subreddit to delete the flair template in.
     * @param flairEnabled Whether flairs are enabled.
     * @param flairPosition Position of flairs.
     * @param flairSelfAssignEnabled Whether flair self assigning is enabled.
     * @param linkFlairPosition Position of link flairs.
     * @param linkFlairSelfAssignEnabled Whether link flair self assigning is enabled.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void flairConfig(String subreddit, boolean flairEnabled, FlairPosition flairPosition, boolean flairSelfAssignEnabled, FlairPosition linkFlairPosition, boolean linkFlairSelfAssignEnabled, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/flairconfig", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.flair_enabled = flairEnabled
        request.parameters.flair_position = flairPosition
        request.parameters.flair_self_assign_enabled = flairSelfAssignEnabled
        request.parameters.link_flair_position = linkFlairPosition
        request.parameters.link_flair_self_assign_enabled = linkFlairSelfAssignEnabled
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Sets a flair's CSV.
     * @param subreddit Subreddit to modify the flair on.
     * @param flairCsv Flair CSV to set.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void flairCsv(String subreddit, String flairCsv, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/flaircsv", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.flair_csv = flairCsv
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Gets a Listing of flairs.
     * @param query Query to list flairs with. Required: subreddit; Optional: after, before, ct, limit, user, showAll
     * @param callback Callback to pass the Listing result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void flairList(Map query, Closure callback, Closure errorHandler = null) {
        Map q = [subreddit: "", after: null, before: null, ct: 0, limit: 25, user: "", showAll: false]
        q << query
        HttpRequest request = new HttpRequest(location: "r/${q.subreddit}/api/flairlist", method: "GET")
        request.parameters.api_type = "json"
        request.parameters.after = after
        request.parameters.before = before
        request.parameters.count = q.ct
        request.parameters.limit = q.limit
        request.parameters.name = q.user
        if(q.showAll) {
            request.parameters.show = "all"
        }

        this.session.makeRequest(request, {
            callback(new Listing(it))
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Gets a flair selector for a user or link.
     * @param subreddit Subreddit to get the flair selector in.
     * @param value Link or user to get a selector for.
     * @param type FlairType to get a selector for.
     * @param callback Callback to pass the FlairSelector result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void flairSelector(String subreddit, String value, FlairType type, Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/flairselector", method: "POST")
        request.parameters.api_type = "json"
        request.parameters[type == FlairType.LINK_FLAIR ? "link" : "name"] = value
        this.session.makeRequest(request, {
            callback(new FlairSelector(it))
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Creates a flair template.
     * @param subreddit Subreddit to create the flair template in.
     * @param cssClass CSS class of the flair template.
     * @param flairTemplateId ID of the flair template.
     * @param type FlairType of the flair template.
     * @param text Text of the flair template.
     * @param textEditable Whether the flair template's text is editable.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void flairTemplate(String subreddit, String cssClass, String flairTemplateId, FlairType type, String text, boolean textEditable, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/flairtemplate", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.css_class = cssClass
        request.parameters.flair_template_id = flairTemplateId
        request.parameters.flair_type = type.name()
        request.parameters.text = text
        request.parameters.text_editable = textEditable
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Selects a flair.
     * @param subreddit Subreddit to select a flair in.
     * @param flairTemplateId ID of the flair template.
     * @param value Link or user to select a flair for.
     * @param type Type of flair to select.
     * @param text Text of the flair.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void selectFlair(String subreddit, String flairTemplateId, String value, FlairType type, String text, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/selectflair", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.flair_template_id = flairTemplateId
        request.parameters[type == FlairType.LINK_FLAIR ? "link" : "name"] = value
        request.parameters.text = text
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Sets whether flairs are enabled.
     * @param subreddit Subreddit to set whether flairs are enabled in.
     * @param flairEnabled Whether flairs should be enabled.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void setFlairEnabled(String subreddit, boolean flairEnabled, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/setflairenabled", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.flair_enabled = flairEnabled
        this.session.makeRequest(request, callback, errorHandler)
    }
}
