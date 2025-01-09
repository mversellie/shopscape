package pro.informatiq.shopscape.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpMethod
import org.springframework.security.web.util.matcher.RequestMatcher

class PreRequestMatcher : RequestMatcher {
    override fun matches(request: HttpServletRequest?): Boolean {
        return !isWritable(request)
    }

    private fun isWritable(request: HttpServletRequest?) : Boolean {
        return request?.method.equals(HttpMethod.PUT.toString(),true) || request?.method.equals(HttpMethod.POST.toString(),true)
    }
}