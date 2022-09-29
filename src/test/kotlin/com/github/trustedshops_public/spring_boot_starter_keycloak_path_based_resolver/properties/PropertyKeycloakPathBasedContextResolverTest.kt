package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.properties

import com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.withAutoConfig
import com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.asProperty
import com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration.KeycloakPathContextConfiguration
import com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration.KeycloakPathContextConfigurationHolder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import java.lang.IllegalStateException
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PropertyKeycloakPathBasedContextResolverTest {
    private val contextRunner = ApplicationContextRunner()
        .withAutoConfig()


    @Test
    fun `Verify it works with valid mappings`() {
        contextRunner
            .withPropertyValues(
                "enabled".asProperty("true"),
                // contexts definition
                "contexts.b2b.auth-server-url".asProperty("https://my-auth-server.url/auth"),
                "contexts.b2b.realm".asProperty("b2b"),
                "contexts.b2b.resource".asProperty("b2b-billing-api"),
                "contexts.b2b.public-client".asProperty("false"),
                "contexts.b2b.bearer-only".asProperty("true"),
                // mappings
                "mappings.0.ant-matcher.0".asProperty("/**"),
                "mappings.0.context".asProperty("b2b")
            ).run {
                assertNotNull( it.getBean(KeycloakPathContextConfiguration::class.java))

                val config = it.getBean(KeycloakPathContextConfigurationHolder::class.java)
                assertTrue { config.mapping.containsKey("/**") }
            }
    }

    @Test
    fun `Verify it fails with duplicated mappings when getting config bean`() {
       assertThrows<IllegalStateException> {
            contextRunner
                .withPropertyValues(
                    "enabled".asProperty("true"),
                    // contexts definition
                    "contexts.b2b.auth-server-url".asProperty("https://my-auth-server.url/auth"),
                    "contexts.b2b.realm".asProperty("b2b"),
                    "contexts.b2b.resource".asProperty("b2b-billing-api"),
                    "contexts.b2b.public-client".asProperty("false"),
                    "contexts.b2b.bearer-only".asProperty("true"),
                    // mappings
                    "mappings.0.ant-matcher.0".asProperty("/**"),
                    "mappings.0.context".asProperty("b2b"),
                    "mappings.1.ant-matcher.0".asProperty("/**"),
                    "mappings.1.context".asProperty("b2b")
                ).run {
                    it.getBean(KeycloakPathContextConfiguration::class.java)
                }
        }
    }

    @Test
    fun `Verify it fails with mapping referencing non-existent context`() {
        assertThrows<IllegalStateException> {
            contextRunner
                .withPropertyValues(
                    "enabled".asProperty("true"),
                    // contexts definition
                    "contexts.b2b.auth-server-url".asProperty("https://my-auth-server.url/auth"),
                    "contexts.b2b.realm".asProperty("b2b"),
                    "contexts.b2b.resource".asProperty("b2b-billing-api"),
                    "contexts.b2b.public-client".asProperty("false"),
                    "contexts.b2b.bearer-only".asProperty("true"),
                    // mappings
                    "mappings.0.ant-matcher.0".asProperty("/**"),
                    "mappings.0.context".asProperty("b2b"),
                    "mappings.1.ant-matcher.0".asProperty("/**"),
                    "mappings.1.context".asProperty("b2c")
                ).run {
                    it.getBean(KeycloakPathContextConfiguration::class.java)
                }
        }
    }
}
