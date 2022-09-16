package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration

/**
 * Resolver to register path matchers to given deployments.
 *
 * It may be implemented multiple times, but cant handle collisions and will throw an error if you try
 * to register the same pattern twice.
 */
interface KeycloakPathBasedContextResolver {
    fun configure(configuration : KeycloakPathContextConfigurationHolder)
}
