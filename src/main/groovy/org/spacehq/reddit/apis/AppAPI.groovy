package org.spacehq.reddit.apis

import org.spacehq.gcommons.http.HttpRequest
import org.spacehq.gcommons.http.HttpSession
import org.spacehq.reddit.data.constants.AppType

/**
 * Reddit app API methods.
 */
class AppAPI {
	private final HttpSession session

	/**
	 * Creates a new AppAPI instance.
	 * @param session HttpSession to use.
	 */
	AppAPI(HttpSession session) {
		this.session = session
	}

	/**
	 * Adds a developer to an app.
	 * @param clientId Client ID of the app.
	 * @param name Name of the developer to add.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void addDeveloper(String clientId, String name, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/adddeveloper", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.client_id = clientId
		request.parameters.name = name
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Deletes an app.
	 * @param clientId Client ID of the app.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void deleteApp(String clientId, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/deleteapp", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.client_id = clientId
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Removes a developer from an app.
	 * @param clientId Client ID of the app.
	 * @param name Name of the developer to remove.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void removeDeveloper(String clientId, String name, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/removedeveloper", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.client_id = clientId
		request.parameters.name = name
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Revokes an app.
	 * @param clientId Client ID of the app.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void revokeApp(String clientId, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/revokeapp", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.client_id = clientId
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Sets the icon of an app.
	 * @param clientId Client ID of the app.
	 * @param icon Icon to set.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void setAppIcon(String clientId, File icon, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/setappicon", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.client_id = clientId
		request.uploads.file = icon
		this.session.makeRequest(request, callback, errorHandler)
	}

	/**
	 * Updates an app's information.
	 * @param aboutUrl About URL of the app.
	 * @param appType The app's type.
	 * @param iconUrl URL of the app's icon.
	 * @param name Name of the app.
	 * @param redirectUri Redirect URI of the app.
	 * @param callback (Optional) Callback to call when the request is complete.
	 * @param errorHandler (Optional) Callback to handle errors.
	 */
	void updateApp(String aboutUrl, AppType appType, String iconUrl, String name, String redirectUri, Closure callback = null, Closure errorHandler = null) {
		HttpRequest request = new HttpRequest(location: "api/updateapp", method: "POST")
		request.parameters.api_type = "json"
		request.parameters.about_url = aboutUrl
		request.parameters.app_type = appType
		request.parameters.icon_url = iconUrl
		request.parameters.name = name
		request.parameters.redirect_uri = redirectUri
		this.session.makeRequest(request, callback, errorHandler)
	}
}
