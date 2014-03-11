package org.spacehq.reddit.apis

import org.spacehq.gcommons.http.HttpRequest
import org.spacehq.gcommons.http.HttpSession
import org.spacehq.gcommons.http.ResponseType
import org.spacehq.reddit.data.constants.UserInfo
import org.spacehq.reddit.data.constants.UserType
import org.spacehq.reddit.data.json.Account
import org.spacehq.reddit.data.json.Listing

/**
 * Reddit user API methods.
 */
class UserAPI {
	private final HttpSession session

	/**
	 * Creates a new UserAPI instance.
	 * @param session HttpSession to use.
	 */
	UserAPI(HttpSession session) {
		this.session = session
	}

	/**
	 * Friends a user.
	 * @param currentUser ID of the current logged in user.
	 * @param user User to friend.
	 * @param note Note to leave when friending.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void friend(String currentUser, String user, String note, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/friend", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.container = currentUser
		request.parameters.name = user
		request.parameters.note = note
		request.parameters.type = "friend"
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Unfriends a user.
	 * @param currentUser ID of the current logged in user.
	 * @param user User to unfriend.
	 * @param id (Optional) Whether the given user is an ID or a name.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void unfriend(String currentUser, String user, boolean id = false, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/unfriend", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.container = currentUser
		request.parameters[id ? "id" : "name"] = user
		request.parameters.type = "friend"
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Sets a user's type in a subreddit.
	 * @param subreddit Subreddit to set the user's type in.
	 * @param user User to set the type of.
	 * @param note Note to leave when setting the user's type.
	 * @param type UserType to set the user to.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void setUserType(String subreddit, String user, String note, UserType type, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/friend", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.r = subreddit
		request.parameters.name = user
		request.parameters.note = note
		request.parameters.type = type
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Unsets a user's type in a subreddit.
	 * @param subreddit Subreddit to set the user's type in.
	 * @param user User to set the type of.
	 * @param id Whether the given user is an ID or a name.
	 * @param type UserType to unset the user from.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void unsetUserType(String subreddit, String user, boolean id = false, UserType type, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/unfriend", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.r = subreddit
		request.parameters[id ? "id" : "name"] = user
		request.parameters.type = type
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Sets the permissions of a user in a subreddit.
	 * @param subreddit Subreddit to set the user's permissions in.
	 * @param user User to set the permissions of.
	 * @param permissions Permissions to set.
	 * @param type UserType of the user.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void setPermissions(String subreddit, String user, String permissions, UserType type, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "r/${subreddit}/api/setpermissions", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.name = user
		request.parameters.permissions = permissions
		request.parameters.type = type
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Checks whether a username is available.
	 * @param username Username to check for.
	 * @param callback Callback to pass the boolean result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void usernameAvailable(String username, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/username_available", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.user = username
		this.session.makeRequest(request, callback, errorHandler, ResponseType.BOOLEAN)
	}


	/**
	 * Gets information about a user.
	 * @param user User to get information about.
	 * @param callback Callback to pass the Account result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void about(String user, Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "user/${user}/about", method: "GET")
		request.parameters.api_type = "json"
		this.session.makeRequest(request, {
			callback(new Account(it))
		}, errorHandler, ResponseType.JSON)
	}

	/**
	 * Gets a Listing of a user's message section.
	 * @param query Query to use to get the listing. Required: user, where; Optional: showGiven, sortBy, time, after, before, ct, limit
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void list(Map query, Closure callback, Closure errorHandler = null) {
		Map q = [user: "", where: UserInfo.OVERVIEW, showGiven: false, sortBy: null, time: null, after: null, before: null, ct: 0, limit: 25]
		q << query
		HttpRequest request = new HttpRequest(location: "user/${q.user}/${q.where.name().toLowerCase()}", method: "GET")
		request.parameters.api_type = "json"
		if(q.showGiven) {
			request.parameters.show = "given"
		}

		request.parameters.sort = q.sortBy
		request.parameters.t = q.time
		request.parameters.after = q.after
		request.parameters.before = q.before
		request.parameters.count = q.ct
		request.parameters.limit = q.limit
		this.session.makeRequest(request, {
			callback(new Listing(it))
		}, errorHandler, ResponseType.JSON)
	}
}
