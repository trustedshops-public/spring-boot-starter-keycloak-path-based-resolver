package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.keycloak

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.springframework.context.annotation.Configuration

@Configuration
open class PathBasedKeycloakConfigResolver : KeycloakSpringBootConfigResolver() {
}
