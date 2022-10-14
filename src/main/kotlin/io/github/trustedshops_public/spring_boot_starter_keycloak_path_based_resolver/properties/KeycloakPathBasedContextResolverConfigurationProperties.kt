package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.properties

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.properties.Delegates

@Configuration
@ConfigurationProperties("keycloak.path-based-resolve")
@ConditionalOnProperty("keycloak.path-based-resolve.enabled", havingValue = "true")
open class KeycloakPathBasedContextResolverConfigurationProperties {
     /**
      * List of contexts, identified by a unique name
      */
     lateinit var contexts : Map<String, KeycloakSpringBootProperties>

     /**
      * Mappings for securing routes
      */
     lateinit var mappings : List<ContextPathMapping>

     /**
      * Set to true to enable config property evaluation
      */
     var enabled by Delegates.notNull<Boolean>()
}

open class ContextPathMapping {
     /**
      * Ant matchers to secure
      */
     lateinit var antMatcher : List<String>

     /**
      * Context to use
      */
     lateinit var context : String
}
