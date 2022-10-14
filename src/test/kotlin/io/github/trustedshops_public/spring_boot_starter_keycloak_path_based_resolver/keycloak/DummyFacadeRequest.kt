package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.keycloak

import org.keycloak.adapters.spi.AuthenticationError
import org.keycloak.adapters.spi.HttpFacade
import org.keycloak.adapters.spi.LogoutError
import java.io.InputStream

class DummyFacadeRequest(val path: String) : HttpFacade.Request {
    override fun getMethod() = "GET"
    override fun getURI() = "http://mock/$path"

    override fun getRelativePath() = path

    override fun isSecure() = false

    override fun getFirstParam(p0: String?) = null

    override fun getQueryParamValue(p0: String?) = null

    override fun getCookie(p0: String?) = null

    override fun getHeader(p0: String?) = null

    override fun getHeaders(p0: String?) = mutableListOf<String>()

    override fun getInputStream() = null

    override fun getInputStream(p0: Boolean) = null

    override fun getRemoteAddr() = "0.0.0.0"

    override fun setError(p0: AuthenticationError?) {
    }

    override fun setError(p0: LogoutError?) {
    }
}
