spring-boot-starter-keycloak-path-based-resolver
===

[![CircleCI Build Status](https://circleci.com/gh/trustedshops-public/spring-boot-starter-keycloak-path-based-resolver.svg?style=shield "CircleCI Build Status")](https://circleci.com/gh/trustedshops-public/spring-boot-starter-keycloak-path-based-resolver)
[![GitHub License](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://github.com/trustedshops-public/spring-boot-starter-keycloak-path-based-resolver/blob/main/LICENSE)

Spring Boot Starter making it easy to use multiple Keycloak contexts for different parts of your service.

## Usage

### 1. Add to your dependencies

> As the starter adds **no dependencies** for [Spring Boot Security Starter](https://spring.io/guides/gs/securing-web/#initial) or [Keycloak Spring Boot Starters](https://www.keycloak.org/docs/latest/securing_apps/#_spring_boot_adapter)
> make sure to include them separately in your project.

<!-- TODO: Add note about maven central or github packages, based on decision where to host the artifacts -->

```xml
<dependency>
    <groupId>io.github.trustedshops-public</groupId>
    <artifactId>spring-boot-starter-keycloak-path-based-resolver</artifactId>
    <version>${spring-boot-starter.keycloak-path-based-resolver.version}</version>
</dependency>
```

### 2. Configure

The starter supports to ways of configuring:

- using code - some kind of DSL
- using spring properties

We strongly recommend using properties since it is less effort and more predictable.

#### 2.1 Configure with config properties

##### YAML

```yaml
keycloak:
  path-based-resolve:
    contexts:
      b2b:
        auth-server-url: https://my-auth-server.url/auth
        realm: b2b
        resource: b2b-billing-api
        public-client: false
        bearer-only: true
      b2c:
        auth-server-url: https://my-auth-server.url/auth
        realm: b2c
        resource: b2c-billing-api
        public-client: false
        bearer-only: true
    mappings:
      - ant-matcher:
          - /b2b/**
        context: b2b
      - ant-matcher:
          - /b2c/**
        context: b2c
```

##### Properties

```properties
keycloak.path-based-resolve.contexts.b2b.auth-server-url=https://my-auth-server.url/auth
keycloak.path-based-resolve.contexts.b2b.realm=b2b
keycloak.path-based-resolve.contexts.b2b.resource=b2b-billing-api
keycloak.path-based-resolve.contexts.b2b.public-client=false
keycloak.path-based-resolve.contexts.b2b.bearer-only=true
keycloak.path-based-resolve.contexts.b2c.auth-server-url=https://my-auth-server.url/auth
keycloak.path-based-resolve.contexts.b2c.realm=b2c
keycloak.path-based-resolve.contexts.b2c.resource=b2c-billing-api
keycloak.path-based-resolve.contexts.b2c.public-client=false
keycloak.path-based-resolve.contexts.b2c.bearer-only=true
keycloak.path-based-resolve.mappings.0.ant-matcher=[/b2b/**]
keycloak.path-based-resolve.mappings.0.context=b2b
keycloak.path-based-resolve.mappings.1.ant-matcher=[/b2c/**]
keycloak.path-based-resolve.mappings.1.context=b2c
```

#### 2.2 Configure with code

##### Configure with Kotlin

````kotlin
class KeycloakPathBasedContextResolverImpl : KeycloakPathBasedContextResolver {
    private fun createKeycloakConfig(realm: String, client: String): KeycloakDeployment {
        val config = AdapterConfig()
        config.authServerUrl = "https://my-auth-sever.url"
        config.realm = realm
        config.resource = client
        config.isBearerOnly = true
        config.isPublicClient = false
        return KeycloakDeploymentBuilder.build(config)
    }

    override fun configure(configuration: KeycloakPathContextConfigurationHolder) {
        configuration
            .antMatchers("/b2b/**")
            .useKeycloakDeployment(createKeycloakConfig("b2b", "b2b-billing-api"))
            .antMatchers("/b2c/**")
            .useKeycloakDeployment(createKeycloakConfig("b2c", "b2c-billing-api"))
    }
}
````

##### Configure with Java

```java
class KeycloakPathBasedContextResolverImpl implements KeycloakPathBasedContextResolver {
    private KeycloakDeployment createKeycloakConfig(String realm, String client) {
        AdapterConfig config = new AdapterConfig();
        config.setAuthServerUrl("https://my-auth-sever.url");
        config.setRealm(realm);
        config.setResource(client);
        config.setPublicClient(false);
        config.setBearerOnly(true);
        return KeycloakDeploymentBuilder.build(config);
    }

    @Override
    public void configure(KeycloakPathContextConfigurationHolder configuration) {
        configuration
                .antMatchers("/b2b/**")
                .useKeycloakDeployment(createKeycloakConfig("b2b", "b2b-billing-api"))
                .antMatchers("/b2c/**")
                .useKeycloakDeployment(createKeycloakConfig("b2c", "b2c-billing-api"));
    }
}
```



## Notes about implementation

- Ant matchers are sorted by length
    - longest matcher is checked first
    - first matcher that gives a positive result wins
- DSL / Property files can in theory be used together but is strongly discouraged and not officially supported
- Contains no additional automatic dependencies avoiding version locks in most cases
