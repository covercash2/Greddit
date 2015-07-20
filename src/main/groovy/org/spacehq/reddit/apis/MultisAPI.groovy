package org.spacehq.reddit.apis

import groovy.json.JsonBuilder
import org.spacehq.reddit.data.constants.MultiVisibility
import org.spacehq.reddit.data.json.LabeledMulti
import org.spacehq.reddit.data.json.LabeledMultiDescription
import org.spacehq.reddit.data.json.LabeledMultiSubreddit
import org.spacehq.reddit.util.http.HttpRequest
import org.spacehq.reddit.util.http.HttpSession
import org.spacehq.reddit.util.http.ResponseType

/**
 * Reddit multi API methods.
 */
class MultisAPI {
    private final HttpSession session

    /**
     * Creates a new MultisAPI instance.
     * @param session HttpSession to use.
     */
    MultisAPI(HttpSession session) {
        this.session = session
    }

    /**
     * Gets a list of multis belonging to the logged in user.
     * @param callback Callback to pass the List<LabeledMulti> result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void mine(Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/mine", method: "GET")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, {
            callback(it.collect { new LabeledMulti(it) })
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Deletes a multi.
     * @param multipath Path of the multi.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void delete(String multipath, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/${multipath}", method: "DELETE")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Gets the info of a multi.
     * @param multipath Path of the multi.
     * @param callback Callback to pass the LabeledMulti result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void multi(String multipath, Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/${multipath}", method: "GET")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, {
            callback(new LabeledMulti(it))
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Creates a multi.
     * @param multipath Path of the multi.
     * @param subredditList List of subreddits to include in the multi.
     * @param visibilityValue Visibility of the multi.
     * @param callback (Optional) Callback to pass the LabeledMulti result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void create(String multipath, List subredditList, MultiVisibility visibilityValue, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/${multipath}", method: "POST")
        request.parameters.api_type = "json"
        JsonBuilder builder = new JsonBuilder()
        builder {
            subreddits(subredditList.collect { [name: it] })
            visibility(visibilityValue.name().toLowerCase())
        }

        request.parameters.model = builder.toString()
        this.session.makeRequest(request, {
            if(callback != null) {
                callback(new LabeledMulti(it))
            }
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Creates or updates a multi.
     * @param multipath Path of the multi.
     * @param subredditList List of subreddits to include in the multi.
     * @param visibilityValue Visibility of the multi.
     * @param callback (Optional) Callback to pass the LabeledMulti result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void createOrUpdate(String multipath, List subredditList, MultiVisibility visibilityValue, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/${multipath}", method: "PUT")
        request.parameters.api_type = "json"
        JsonBuilder builder = new JsonBuilder()
        builder {
            subreddits(subredditList.collect { [name: it] })
            visibility(visibilityValue.name().toLowerCase())
        }

        request.parameters.model = builder.toString()
        this.session.makeRequest(request, {
            if(callback != null) {
                callback(new LabeledMulti(it))
            }
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Copies a multi.
     * @param from Path of the multi to copy.
     * @param to Path of the new multi.
     * @param callback (Optional) Callback to pass the LabeledMulti result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void copy(String from, String to, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/copy", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.from = from
        request.parameters.to = to
        this.session.makeRequest(request, {
            if(callback != null) {
                callback(new LabeledMulti(it))
            }
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Gets the description of a multi.
     * @param multipath Path of the multi.
     * @param callback Callback to pass the LabeledMultiDescription result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void description(String multipath, Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/${multipath}/description", method: "GET")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, {
            callback(new LabeledMultiDescription(it))
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Sets the description of a multi.
     * @param multipath Path of the multi.
     * @param description New description of the multi.
     * @param callback (Optional) Callback to pass the LabeledMultiDescription result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void setDescription(String multipath, String description, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/${multipath}/description", method: "PUT")
        request.parameters.api_type = "json"
        JsonBuilder builder = new JsonBuilder()
        builder {
            body_md(description)
        }

        request.parameters.model = builder.toString()
        this.session.makeRequest(request, {
            if(callback != null) {
                callback(new LabeledMultiDescription(it))
            }
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Removes a subreddit from a multi.
     * @param multipath Path of the multi.
     * @param subreddit Subreddit to remove.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void removeSubreddit(String multipath, String subreddit, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/${multipath}/r/${subreddit}", method: "DELETE")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Gets info about a subreddit in a multi.
     * @param multipath Path of the multi.
     * @param subreddit Subreddit to get info about.
     * @param callback Callback to pass the LabeledMultiSubreddit result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void getSubreddit(String multipath, String subreddit, Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/${multipath}/r/${subreddit}", method: "GET")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, {
            callback(new LabeledMultiSubreddit(it))
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Adds a subreddit to a multi.
     * @param multipath Path of the multi.
     * @param subreddit Subreddit to add.
     * @param callback (Optional) Callback to call when the request is complete.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void addSubreddit(String multipath, String subreddit, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/${multipath}/r/${subreddit}", method: "PUT")
        request.parameters.api_type = "json"
        JsonBuilder builder = new JsonBuilder()
        builder {
            name(subreddit)
        }

        request.parameters.model = builder.toString()
        this.session.makeRequest(request, callback, errorHandler)
    }

    /**
     * Renames a multi.
     * @param from Path of the multi to rename.
     * @param to New path of the multi.
     * @param callback (Optional) Callback to pass the LabeledMulti result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void rename(String from, String to, Closure callback = null, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/multi/rename", method: "POST")
        request.parameters.api_type = "json"
        request.parameters.from = from
        request.parameters.to = to
        this.session.makeRequest(request, {
            if(callback != null) {
                callback(new LabeledMulti(it))
            }
        }, errorHandler, ResponseType.JSON)
    }
}
