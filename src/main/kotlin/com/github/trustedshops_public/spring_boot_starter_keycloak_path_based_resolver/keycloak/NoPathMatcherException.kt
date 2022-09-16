package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.keycloak

import org.keycloak.adapters.spi.HttpFacade

class NoPathMatcherException(private val request : HttpFacade.Request) : Exception() {
    override val message: String?
        get() = "No path configured for request path '${request.relativePath}'"
}
