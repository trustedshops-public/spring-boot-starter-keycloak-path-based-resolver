package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration

import com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.KeycloakPathBasedResolverAutoConfiguration
import org.junit.jupiter.api.Test
import org.keycloak.adapters.KeycloakDeployment
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.context.annotation.UserConfigurations
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class KeycloakPathBasedContextResolverDummy1 : KeycloakPathBasedContextResolver {
    override fun configure(configuration: KeycloakPathContextConfigurationHolder) {
        configuration.antMatchers("/dummy1/**")
            .useKeycloakDeployment(KeycloakDeployment())
    }
}

class KeycloakPathBasedContextResolverDummy2 : KeycloakPathBasedContextResolver {
    override fun configure(configuration: KeycloakPathContextConfigurationHolder) {
        configuration.antMatchers("/dummy2/**")
            .useKeycloakDeployment(KeycloakDeployment())
    }
}

class KeycloakPathContextConfigurationTest {
    private val contextRunner = ApplicationContextRunner()

    @Test
    fun `Verify configuration works with one configurer`() {
        contextRunner
            .withConfiguration(AutoConfigurations.of(KeycloakPathBasedResolverAutoConfiguration::class.java))
            .withConfiguration(UserConfigurations.of(KeycloakPathBasedContextResolverDummy1::class.java))
            .run {
                assertNotNull( it.getBean(KeycloakPathContextConfiguration::class.java))

                val config = it.getBean(KeycloakPathContextConfigurationHolder::class.java)
                assertTrue { config.mapping.containsKey("/dummy1/**") }
            }
    }

    @Test
    fun `Verify configuration works with multiple configurer`() {
        contextRunner
            .withConfiguration(AutoConfigurations.of(KeycloakPathBasedResolverAutoConfiguration::class.java))
            .withConfiguration(
                UserConfigurations.of(
                    KeycloakPathBasedContextResolverDummy1::class.java,
                    KeycloakPathBasedContextResolverDummy2::class.java
                )
            )
            .run {
                assertNotNull(it.getBean(KeycloakPathContextConfiguration::class.java))

                val config = it.getBean(KeycloakPathContextConfigurationHolder::class.java)
                assertTrue { config.mapping.containsKey("/dummy1/**") }
                assertTrue { config.mapping.containsKey("/dummy2/**") }
            }
    }
}
