package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.keycloak

import org.keycloak.adapters.KeycloakDeployment
import org.keycloak.adapters.spi.HttpFacade
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.springframework.context.annotation.Configuration
import java.lang.IllegalArgumentException

@Configuration
open class PathBasedKeycloakConfigResolver : KeycloakSpringBootConfigResolver() {
    override fun resolve(request: HttpFacade.Request?): KeycloakDeployment {
        if(request == null) {
            throw IllegalArgumentException("request")
        }

        // TODO Implement

        throw NoPathMatcherException(request)
    }
}
