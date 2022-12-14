package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration

import io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.resolver.KeycloakPathBasedContextResolver
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
@ConditionalOnBean(KeycloakPathBasedContextResolver::class)
open class KeycloakPathContextConfiguration(resolver: List<KeycloakPathBasedContextResolver>) {
    private val configuration = KeycloakPathContextConfigurationHolder()

    init {
        resolver.forEach { it.configure(configuration) }
    }

    @Bean
    fun keycloakPathContextConfigurationHolder() = configuration
}

@Component
@ConditionalOnMissingBean(value = [KeycloakPathBasedContextResolver::class])
open class KeycloakPathContextConfigurationFallback {
    @Bean
    fun keycloakPathContextConfigurationHolder() = KeycloakPathContextConfigurationHolder()
}
