package org.spacehq.reddit.apis

import org.spacehq.gcommons.http.HttpRequest
import org.spacehq.gcommons.http.HttpSession
import org.spacehq.gcommons.http.ResponseType
import org.spacehq.reddit.data.json.Account

/**
 * Reddit auth API methods.
 */
class RedditAuthAPI {
	private final HttpSession session

	/**
	 * Creates a new RedditAuthAPI instance.
	 * @param session Session to use.
	 */
	RedditAuthAPI(HttpSession session) {
		this.session = session
	}

	/**
	 * Registers a new user.
	 * @param username Username of the user.
	 * @param captcha Captcha string to create the user with.
	 * @param iden Iden value of the captcha string.
	 * @param password Password of the user.
	 * @param email (Optional) Email address of the user.
	 * @param remember Whether or not to keep the user logged in. (Cookie stays valid)
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to pass errors to.
	 */
	void register(String username, String captcha, String iden, String password, String email = null, boolean remember, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/login", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.captcha = captcha
		if(email != null) {
			request.parameters.email = email
		}

		request.parameters.iden = iden
		request.parameters.passwd = password
		request.parameters.passwd2 = password
		request.parameters.rem = remember
		request.parameters.user = username
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Logs in.
	 * @param username Username of the user.
	 * @param password Password of the user.
	 * @param remember Whether or not to keep the user logged in. (Cookie stays valid)
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to pass errors to.
	 */
	void login(String username, String password, boolean remember, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/login", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.passwd = password
		request.parameters.rem = remember
		request.parameters.user = username
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Clears the user's login sessions.
	 * @param password Password of the user.
	 * @param dest The destination URL.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to pass errors to.
	 */
	void clearSessions(String password, String dest, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/clear_sessions", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.curpass = password
		request.parameters.dest = dest
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Updates a user's account details.
	 * @param password Password of the user.
	 * @param dest The destination URL.
	 * @param email Email address of the user.
	 * @param newpass New password of the user.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to pass errors to.
	 */
	void update(String password, String dest, String email, String newpass, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/clear_sessions", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.curpass = password
		request.parameters.dest = dest
		request.parameters.email = email
		request.parameters.newpass = newpass
		request.parameters.verify = true
		request.parameters.verpass = newpass
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Deletes a user.
	 * @param username Username of the user.
	 * @param password Password of the user.
	 * @param reason (Optional) Reason for deleting the user.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to pass errors to.
	 */
	void deleteUser(String username, String password, String reason = null, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/delete_user", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.confirm = true
		if(reason != null) {
			request.parameters.delete_message = reason
		}

		request.parameters.passwd = password
		request.parameters.user = username
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Gets the Account information of the user.
	 * @param callback Callback to pass the Account result to.
	 * @param errorHandler (Optional) Callback to pass errors to.
	 */
	void me(Closure callback, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/me", method: "GET")
		request.parameters.api_type = "json"
		this.session.makeRequest(request, {
			callback(new Account(it))
		}, errorHandler, ResponseType.JSON)
	}
}
