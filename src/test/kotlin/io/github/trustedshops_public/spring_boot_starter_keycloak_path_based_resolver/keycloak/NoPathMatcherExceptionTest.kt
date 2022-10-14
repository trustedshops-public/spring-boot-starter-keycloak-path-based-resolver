package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver.keycloak

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NoPathMatcherExceptionTest {
    @Test
    fun `Verify that error message can be built`() {
        assertEquals(
            NoPathMatcherException(DummyFacadeRequest("/test")).message,
            "No path configured for request path '/test'"
        )
    }
}
