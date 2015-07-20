package org.spacehq.reddit.util.http

import groovy.json.JsonSlurper

/**
 * An HTTP session.
 */
class HttpSession {
    private
    final org.spacehq.reddit.util.http.RequestScheduler scheduler = new org.spacehq.reddit.util.http.RequestScheduler()

    /**
     * The session's request properties.
     */
    Map properties = [:]
    /**
     * The session's default error handler callback.
     */
    Closure errorHandler = {
        it.printStackTrace()
        this.stop()
    }
    /**
     * The session's initial handler of JSON responses. (check for errors, etc.)
     */
    Closure initialJsonHandler
    /**
     * The session's authorization data.
     */
    org.spacehq.reddit.util.http.auth.Authorization authorization
    /**
     * The session's base URL.
     */
    String baseUrl

    /**
     * Sets the minimum amount of milliseconds between request calls.
     * @param millisBetweenCalls Milliseconds between calls.
     * @return This HttpSession.
     */
    HttpSession setMillisBetweenCalls(long millisBetweenCalls) {
        this.scheduler.millisBetweenCalls = millisBetweenCalls
        return this
    }

    /**
     * Stops the session's request scheduler.
     */
    void stop() {
        this.scheduler.running = false
    }

    /**
     * Makes a request.
     * @param request Request to make.
     * @param callback Callback to call when the request is completed.
     * @param errorHandler (Optional) Error handler callback.
     * @param type (Optional) Data type to parse the response to and pass to the callback.
     */
    void makeRequest(org.spacehq.reddit.util.http.HttpRequest request, Closure callback, Closure errorHandler = null, org.spacehq.reddit.util.http.ResponseType type = null) {
        try {
            this.scheduler.queue.add(this.&doRequest.curry(null, request, callback, errorHandler, type))
        } catch(org.spacehq.reddit.util.http.RequestException e) {
            errorHandler(e)
        } catch(Throwable t) {
            errorHandler(new org.spacehq.reddit.util.http.RequestException("Failed to make HTTP request.", t))
        }
    }

    private void doRequest(String url, org.spacehq.reddit.util.http.HttpRequest request, Closure callback, Closure errorHandler, org.spacehq.reddit.util.http.ResponseType type) {
        URLConnection conn = null
        InputStream input = null
        OutputStream out = null
        try {
            if(errorHandler == null) {
                errorHandler = this.errorHandler
            }

            if(url == null) {
                url = "https://${this.baseUrl}/${request.location.startsWith("/") ? request.location.substring(1) : request.location}.json"
            }

            if(!url.contains("?") && request.method != "POST" && request.method != "PUT") {
                StringBuilder build = new StringBuilder()
                boolean first = true
                request.parameters.each { k, v ->
                    if(v != null) {
                        if(!first) {
                            build.append("&")
                        } else {
                            first = false
                        }

                        if(v instanceof Enum) {
                            v = v.name().toLowerCase()
                        }

                        build.append("${k}=${v}")
                    }
                }

                url = "${url}?${build.toString()}"
            }

            conn = new URL(url).openConnection()
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).requestMethod = request.method
            }

            this.properties.each { k, v ->
                conn.setRequestProperty(k, v)
            }

            if(this.authorization != null) {
                conn.setRequestProperty("Authorization", this.authorization.headerValue)
            }

            if((request.parameters.size() > 0 || request.uploads.size() > 0) && (request.method == "POST" || request.method == "PUT")) {
                String boundary = null
                if(request.uploads.size() > 0) {
                    Random random = new Random()
                    boundary = random.nextInt() + "---------------------------" + random.nextInt()
                    conn.setRequestProperty("Content-Type", "multipart/form-data boundary=${boundary}")
                }

                conn.doOutput = true
                out = conn.outputStream
                out.withWriter({ writer ->
                    if(request.parameters.size() > 0) {
                        boolean first = true
                        request.parameters.each { k, v ->
                            if(v != null) {
                                if(v instanceof Enum) {
                                    v = v.name().toLowerCase()
                                }

                                if(request.uploads.size() > 0) {
                                    writer.write("--${boundary}\r\n")
                                    writer.write("Content-Disposition: form-data name=\"${k}\"\r\n")
                                    writer.write("Content-Type: text/plain\r\n\r\n")
                                    writer.write(v.toString())
                                    writer.write("\r\n")
                                } else {
                                    if(!first) {
                                        writer.write("&")
                                    } else {
                                        first = false
                                    }

                                    writer.write("${k}=${v.toString()}")
                                }
                            }
                        }
                    }

                    if(request.uploads.size() > 0) {
                        request.uploads.each { k, v ->
                            if(v != null) {
                                writer.write("--${boundary}\r\n")
                                writer.write("Content-Disposition: form-data name=\"${k}\" filename=\"${v.name}\"\r\n")
                                writer.write("Content-Type: application/octet-stream\r\n\r\n")
                                writer.flush()
                                out.write(v.readBytes())
                                writer.write("\r\n")
                            }
                        }

                        writer.write("--${boundary}--\r\n")
                    }
                })

                out.close()
            }

            if(conn.getHeaderField("Location") != null) {
                this.doRequest(conn.getHeaderField("Location"), request, callback, errorHandler, type)
                return
            }

            input = conn.inputStream
            byte[] resp = input.bytes
            if(resp != null && resp.length > 0) {
                String respString = new String(resp, "UTF-8").trim()
                def json = null
                try {
                    json = new JsonSlurper().parseText(respString)
                } catch(Throwable t) {
                    if(type == org.spacehq.reddit.util.http.ResponseType.JSON) {
                        errorHandler(new org.spacehq.reddit.util.http.RequestException("Failed to parse response data.", t))
                        return
                    }
                }

                if(json != null && json instanceof Map) {
                    if(json.error != null) {
                        errorHandler(new org.spacehq.reddit.util.http.RequestException("${json.error}"))
                        return
                    }

                    if(this.initialJsonHandler != null) {
                        try {
                            this.initialJsonHandler(this, json)
                        } catch(org.spacehq.reddit.util.http.RequestException e) {
                            errorHandler(e)
                            return
                        } catch(Throwable t) {
                            errorHandler(new org.spacehq.reddit.util.http.RequestException("Failed to perform initial handling of JSON response.", t))
                            return
                        }
                    }
                }

                if(callback != null) {
                    if(type == null) {
                        callback()
                    } else {
                        if(type == org.spacehq.reddit.util.http.ResponseType.JSON) {
                            callback(json)
                        } else if(type == org.spacehq.reddit.util.http.ResponseType.BOOLEAN) {
                            callback(Boolean.parseBoolean(respString))
                        } else if(type == org.spacehq.reddit.util.http.ResponseType.STRING) {
                            callback(respString)
                        } else if(type == org.spacehq.reddit.util.http.ResponseType.BYTE_ARRAY) {
                            callback(resp)
                        }
                    }
                }
            } else {
                if(callback != null) {
                    callback()
                }
            }
        } catch(org.spacehq.reddit.util.http.RequestException e) {
            errorHandler(e)
        } catch(Throwable t) {
            errorHandler(new org.spacehq.reddit.util.http.RequestException("Failed to make HTTP request.", t))
        } finally {
            try {
                if(input != null) {
                    input.close()
                }

                if(out != null) {
                    out.close()
                }

                if(conn != null && conn instanceof HttpURLConnection) {
                    ((HttpURLConnection) conn).disconnect()
                }
            } catch(Throwable t) {
            }
        }
    }
}
