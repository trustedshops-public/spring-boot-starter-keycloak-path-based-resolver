package io.github.trustedshops_public.spring_boot_starter_keycloak_path_based_resolver

import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.ApplicationContextRunner

fun ApplicationContextRunner.withAutoConfig() =
    this.withConfiguration(AutoConfigurations.of(AutoConfiguration::class.java))

fun String.asProperty(value : Any): String = "keycloak-path-based-resolve.$this=$value"
