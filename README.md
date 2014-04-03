<b><center><h1>Greddit</h></center></b>
==========


<b>About Greddit</b>
--------

Greddit is a Reddit API wrapper written in Groovy. It is a complete implementation of the API with all functions implemented, including OAuth functions.
Greddit uses a request scheduler thread for asynchronous calls and to allow the library to enforce the two-calls-per-second limit (can be disabled).


<b>Examples</b>
--------

Normal Auth:

```groovy
// Create a new client instance with a unique User Agent.
Reddit reddit = new Reddit("bot/1.0 by Author")
// Set a global error handler that prints exceptions.
reddit.globalErrorHandler = {
	it.printStackTrace()
	reddit.dispose()
}

// Login using Reddit's default authentication.
reddit.redditAuth.login("Username", "Password", false)
// Get Reddit user info.
reddit.redditAuth.me({
	println it
	// Dispose of the client when finished.
	reddit.dispose()
})
...
```

OAuth:

```groovy
// Create a new client instance with a unique User Agent.
Reddit reddit = new Reddit("Username", "bot/1.0 by Steveice10")
// Set a global error handler that prints exceptions.
reddit.globalErrorHandler = {
	it.printStackTrace()
	reddit.dispose()
}

// Prints out an Authorization URL for the user to access. "State" is just a random string to ensure that the response is from the right request and not forged.
println reddit.redditOAuth.getAuthorizationURL((compact), clientId, redirectUri, "dssaddadwdcd", OAuthDuration.TEMPORARY, [OAuthScope.IDENTITY])
// Get and process access token.
String code = "code goes here";
reddit.redditOAuth.getAccessToken(consumerKey, consumerSecret, code, redirectUri, {
    // Enable OAuth authentication for further requests.
    reddit.redditOAuth.enableOAuth(it.tokenType, it.accessToken)
})

// Get Reddit user info via OAuth API.
reddit.redditOAuth.me({
	println it
	// Dispose of the client when finished.
	reddit.dispose()
})
...
```

"reddit.dispose()" must be called when you have finished with the client instance as the client spawns a scheduler thread for async operations.
Error handling callbacks will always get a RequestException value.


<b>Building the Source</b>
--------

Greddit uses Maven to manage dependencies. Simply run 'mvn clean install' in the source's directory.
You can also download a build <b>[here](http://build.spacehq.org/browse/GREDDIT-MAIN)</b>.


<b>License</b>
---------

Greddit is licensed under the <b>[MIT license](http://www.opensource.org/licenses/mit-license.html)</b>.
