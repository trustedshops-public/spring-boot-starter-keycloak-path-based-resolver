package com.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver

import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.context.annotation.UserConfigurations
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import kotlin.test.assertNotNull

@EnableKeycloakPathBasedResolver
open class ImportedEnable

class AutoConfigurationTest {
    private val contextRunner = ApplicationContextRunner()

    @Test
    fun `Verify autoconfig works when the class is autoconfigured by Spring Boot`() {
        contextRunner
            .withConfiguration(AutoConfigurations.of(AutoConfiguration::class.java))
            .run {
                assertNotNull(it.getBean(AutoConfiguration::class.java))
            }
    }

    @Test
    fun `Verify autoconfig works when the annotation is used`() {
        contextRunner
            .withConfiguration(UserConfigurations.of(ImportedEnable::class.java))
            .run {
                assertNotNull(it.getBean(AutoConfiguration::class.java))
            }
    }
}
