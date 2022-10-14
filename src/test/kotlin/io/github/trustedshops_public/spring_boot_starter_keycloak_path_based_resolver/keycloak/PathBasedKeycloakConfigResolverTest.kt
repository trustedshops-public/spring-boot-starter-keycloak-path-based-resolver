package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.keycloak

import io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.configuration.KeycloakPathContextConfigurationHolder
import io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.withAutoConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.keycloak.adapters.KeycloakDeployment
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.mock.web.MockHttpServletRequest
import kotlin.test.assertNotNull

class PathBasedKeycloakConfigResolverTest {
    private val contextRunner = ApplicationContextRunner()
        .withAutoConfig()

    @Test
    fun `Verify null value for request should throw an IllegalArgumentException`() {
        contextRunner.run {
            assertThrows<IllegalArgumentException> {
                it.getBean(PathBasedKeycloakConfigResolver::class.java).resolve(null)
            }
        }
    }

    @Test
    fun `Verify no registered patch matches should throw an NoPathMatcherException`() {
        contextRunner.run {
            assertThrows<NoPathMatcherException> {
                it.getBean(PathBasedKeycloakConfigResolver::class.java).resolve(DummyFacadeRequest("/test"))
            }
        }
    }

    @Test
    fun `Verify registered path is matched`() {
        contextRunner.run {
            val config = it.getBean(KeycloakPathContextConfigurationHolder::class.java)
            config.mapping.put("/**", KeycloakDeployment())
            val context = it.getBean(PathBasedKeycloakConfigResolver::class.java).resolve(DummyFacadeRequest("/test"))
            assertNotNull(context)
        }
    }
}
