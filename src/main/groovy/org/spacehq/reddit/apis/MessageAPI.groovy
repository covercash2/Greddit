package org.spacehq.reddit.apis

import org.spacehq.gcommons.http.HttpRequest
import org.spacehq.gcommons.http.HttpSession
import org.spacehq.gcommons.http.ResponseType
import org.spacehq.reddit.data.constants.MessageType
import org.spacehq.reddit.data.json.Listing
import org.spacehq.reddit.data.json.Message

/**
 * Reddit message API methods.
 */
class MessageAPI {
	private final HttpSession session

	/**
	 * Creates a new MessageAPI instance.
	 * @param session HttpSession to use.
	 */
	MessageAPI(HttpSession session) {
		this.session = session
	}

	/**
	 * Blocks a user.
	 * @param id ID of the user to block.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void block(String id, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/block", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.id = id
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Composes a message.
	 * @param to User to send the message to.
	 * @param subject Subject of the message.
	 * @param text Text of the message.
	 * @param iden (Optional) Iden value of a captcha.
	 * @param captcha (Optional) Value of a captcha.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void compose(String to, String subject, String text, String iden = null, String captcha = null, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/compose", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.captcha = captcha
		request.parameters.iden = iden
		request.parameters.subject = subject
		request.parameters.text = text
		request.parameters.to = to
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Marks a message as read.
	 * @param id ID of the message.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void readMessage(String id, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/read_message", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.id = id
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Marks a message as unread.
	 * @param id ID of the message.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void unreadMessage(String id, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/unread_message", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.id = id
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Gets a Listing of messages.
	 * @param query Query to get messages with. Required: where; Optional: mark, mid, after, before, ct, limit, showAll
	 * @param callback Callback to pass the Listing result to.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void messages(Map query, Closure callback, Closure errorHandler = null) {
		Map q = [where: MessageType.INBOX, mark: true, mid: null, after: null, before: null, ct: 0, limit: 25, showAll: false]
		q << query
		HttpRequest request = new HttpRequest(location: "message/${q.where.name().toLowerCase()}", method: "GET")
		request.parameters.api_type = "json"
		request.parameters.mark = q.mark
		request.parameters.mid = q.mid
		request.parameters.after = q.after
		request.parameters.before = q.before
		request.parameters.count = q.ct
		request.parameters.limit = q.limit
		if(q.showAll) {
			request.parameters.show = "all"
		}

		this.session.makeRequest(request, {
			callback(new Listing(it, Message.class))
		}, errorHandler, ResponseType.JSON)
	}
}
