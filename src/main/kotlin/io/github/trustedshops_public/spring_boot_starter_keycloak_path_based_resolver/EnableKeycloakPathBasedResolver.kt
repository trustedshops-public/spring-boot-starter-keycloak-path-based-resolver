package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver

import org.springframework.context.annotation.Import

/**
 * Manually enable keycloak-path-based-resolver starter if autoconfiguration is not enabled
 * or you explicitly disabled the starter.
 */
@Import(AutoConfiguration::class)
annotation class EnableKeycloakPathBasedResolver
