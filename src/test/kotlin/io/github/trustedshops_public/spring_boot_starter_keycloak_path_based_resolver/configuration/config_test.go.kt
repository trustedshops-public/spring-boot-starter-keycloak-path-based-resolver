package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.keycloak.adapters.KeycloakDeploymentBuilder
import org.keycloak.representations.adapters.config.AdapterConfig
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals

class ConfigTest {
    private val dummy1Deployment = KeycloakDeploymentBuilder.build(object : AdapterConfig() {
        init {
            realm = "shadow"
            resource = "dummy1"
            authServerUrl = "https://dummy1.deployment"
        }
    })
    private val dummy2Deployment = KeycloakDeploymentBuilder.build(object : AdapterConfig() {
        init {
            realm = "shadow"
            resource = "dummy2"
            authServerUrl = "https://dummy2.deployment"
        }
    })

    @Test
    fun `Verify ant matchers are set in mapping correctly`() {
        val holder = KeycloakPathContextConfigurationHolder()
            .antMatchers("/b2c/**")
            .useKeycloakDeployment(dummy1Deployment)
            .antMatchers("/b2b/**")
            .useKeycloakDeployment(dummy2Deployment)

        assertEquals(holder.mapping["/b2c/**"], dummy1Deployment)
        assertEquals(holder.mapping["/b2b/**"], dummy2Deployment)
    }

    @Test
    fun `Verify duplicate pattern registration is not possible`() {
        assertThrows<IllegalArgumentException> {
            KeycloakPathContextConfigurationHolder()
                .antMatchers("/**")
                .useKeycloakDeployment(dummy1Deployment)
                .antMatchers("/**")
                .useKeycloakDeployment(dummy2Deployment)
        }
    }
}
