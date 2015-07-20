package org.spacehq.reddit.apis

import org.spacehq.reddit.util.http.HttpRequest
import org.spacehq.reddit.util.http.HttpSession
import org.spacehq.reddit.util.http.ResponseType

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * Reddit captcha API methods.
 */
class CaptchaAPI {
    private final HttpSession session

    /**
     * Creates a new CaptchaAPI instance.
     * @param session Session to use.
     */
    CaptchaAPI(HttpSession session) {
        this.session = session
    }

    /**
     * Checks whether a captcha is needed for certain requests.
     * @param callback Callback to pass the boolean result to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void needsCaptcha(Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/new_captcha", method: "POST")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, callback, errorHandler, ResponseType.BOOLEAN)
    }

    /**
     * Generates a new captcha iden string.
     * @param callback Callback to pass the captcha iden string to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void newCaptcha(Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "api/new_captcha", method: "POST")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, {
            callback(it.json.data.iden)
        }, errorHandler, ResponseType.JSON)
    }

    /**
     * Gets the captcha image of an iden value.
     * @param iden Iden value of the captcha.
     * @param callback Callback to pass the captcha BufferedImage to.
     * @param errorHandler (Optional) Callback to handle errors.
     */
    void captcha(String iden, Closure callback, Closure errorHandler = null) {
        HttpRequest request = new HttpRequest(location: "captcha/${iden}", method: "GET")
        request.parameters.api_type = "json"
        this.session.makeRequest(request, {
            ByteArrayInputStream input = new ByteArrayInputStream(it)
            BufferedImage img = ImageIO.read(input)
            input.close()
            callback(img)
        }, errorHandler, ResponseType.BYTE_ARRAY)
    }
}
