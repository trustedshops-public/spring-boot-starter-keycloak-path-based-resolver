package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.dsl

import io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.resolver.KeycloakPathBasedContextResolver

/**
 * Resolver to register path matchers to given deployments using code.
 *
 * It may be implemented multiple times, but cant handle collisions and will throw an error if you try
 * to register the same pattern twice.
 *
 * Configuration also handles sorting, so that the most specific matcher always is matched first (based on length).
 */
open abstract class DslPropertyKeycloakPathBasedContextResolver : KeycloakPathBasedContextResolver {
}
