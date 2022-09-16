package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.keycloak

import com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.withAutoConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.mock.web.MockHttpServletRequest

class PathBasedKeycloakConfigResolverTest {
    private val contextRunner = ApplicationContextRunner()
        .withAutoConfig()

    @Test
    fun `Null value for request should throw an IllegalArgumentException`() {
        contextRunner.run {
            assertThrows<IllegalArgumentException> {
                it.getBean(PathBasedKeycloakConfigResolver::class.java).resolve(null)
            }
        }
    }

    @Test
    fun `No registered patch matches should throw an NoPathMatcherException`() {
        contextRunner.run {
            assertThrows<NoPathMatcherException> {
                it.getBean(PathBasedKeycloakConfigResolver::class.java).resolve(DummyFacadeRequest("/test"))
            }
        }
    }
}
