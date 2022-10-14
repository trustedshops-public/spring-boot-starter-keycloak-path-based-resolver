package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.properties

import io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration.KeycloakPathContextConfigurationHolder
import io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.resolver.KeycloakPathBasedContextResolver
import org.keycloak.adapters.KeycloakDeploymentBuilder
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Configuration

class InvalidContextException(message : String) : RuntimeException(message)

@Configuration
@ConditionalOnBean(KeycloakPathBasedContextResolverConfigurationProperties::class)
open class PropertyKeycloakPathBasedContextResolver(
    private val properties: KeycloakPathBasedContextResolverConfigurationProperties
) : KeycloakPathBasedContextResolver {
    private fun resolveContext(context : String): KeycloakSpringBootProperties? {
        if(properties.contexts.containsKey(context)) {
            return properties.contexts[context]
        }

        throw InvalidContextException("Context '$context' was not defined, but referenced by mapping")
    }

    override fun configure(configuration: KeycloakPathContextConfigurationHolder) {
        properties.mappings
            .map { mapping ->
                mapping.antMatcher.map { Pair(it, resolveContext(mapping.context)) }
            }
            .flatten()
            .forEach {
                configuration.antMatchers(it.first)
                    .useKeycloakDeployment(KeycloakDeploymentBuilder.build(it.second))
            }
    }
}
