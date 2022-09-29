package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.keycloak

import com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration.KeycloakPathContextConfigurationHolder
import org.keycloak.adapters.KeycloakDeployment
import org.keycloak.adapters.spi.HttpFacade
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.springframework.context.annotation.Configuration
import org.springframework.util.AntPathMatcher

@Configuration
open class PathBasedKeycloakConfigResolver(
    private val keycloakPathContextConfigurationHolder: KeycloakPathContextConfigurationHolder
) : KeycloakSpringBootConfigResolver() {

    private val antPathMatcher = AntPathMatcher()

    override fun resolve(request: HttpFacade.Request?): KeycloakDeployment {
        if (request == null) {
            throw IllegalArgumentException("request")
        }

        val requestPath = request.relativePath

        return keycloakPathContextConfigurationHolder.mapping.firstNotNullOfOrNull {
            return@firstNotNullOfOrNull when {
                antPathMatcher.match(it.key, requestPath) -> it.value
                else -> null
            }
        } ?: throw NoPathMatcherException(request)
    }
}
