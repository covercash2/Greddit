package org.spacehq.reddit.util.http

import groovy.transform.ToString

/**
 * An HTTP request.
 */
@ToString(includeNames = true, includeFields = true, includePackage = false)
class HttpRequest {
    String location
    String method
    Map parameters = [:]
    Map uploads = [:]
}