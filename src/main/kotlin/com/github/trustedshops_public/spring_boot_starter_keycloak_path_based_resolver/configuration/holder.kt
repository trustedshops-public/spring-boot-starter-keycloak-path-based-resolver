package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration

import org.keycloak.adapters.KeycloakDeployment
import java.lang.IllegalArgumentException

class MatcherConfiguration(
    private val parent: KeycloakPathContextConfigurationHolder,
    private val antPatterns: Array<out String>
) {
    /**
     * Configure keycloak deployment for the given antPatterns
     *
     * @see org.keycloak.adapters.KeycloakDeployment
     */
    fun useKeycloakDeployment(keycloakDeployment: KeycloakDeployment): KeycloakPathContextConfigurationHolder {
        antPatterns.associateWith { keycloakDeployment }.forEach { pattern, deployment ->
            if (parent.mapping.containsKey(pattern)) {
                throw IllegalArgumentException("pattern '${pattern}' can not be assigned twice")
            }
            parent.mapping.put(pattern, deployment)
        }
        parent.mapping.putAll(antPatterns.associateWith { keycloakDeployment })
        return parent
    }
}

/**
 * Configuration allowing to map given ant patterns to keycloak deployments
 */
class KeycloakPathContextConfigurationHolder {
    internal val mapping = mutableMapOf<String, KeycloakDeployment>()

    /**
     * Configure context for given ant path matcher
     *
     * For more information check AntPathMatcher
     * @see org.springframework.util.AntPathMatcher
     */
    fun antMatchers(vararg antPatterns: String) = MatcherConfiguration(this, antPatterns)
}
