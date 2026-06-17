package com.acme.kampo.platform.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configures the OpenAPI specification exposed by the Kampo platform.
 *
 * <p>Reads metadata from {@code application.properties} so that title,
 * description and version are never hardcoded here. Server environments
 * (local, staging, production) are declared so Swagger UI lets the user
 * switch target without editing the URL manually.
 *
 * <p>A Bearer/JWT security scheme is included but commented out — uncomment
 * when authentication is added to the platform.
 *
 * <p>Accessible at:
 * <ul>
 *   <li>Swagger UI   — http://localhost:8080/swagger-ui/index.html</li>
 *   <li>OpenAPI JSON — http://localhost:8080/v3/api-docs</li>
 * </ul>
 */
@Configuration
public class OpenApiConfiguration {

    @Value("${spring.application.name}")
    String applicationName;

    @Value("${documentation.application.description}")
    String applicationDescription;

    @Value("${documentation.application.version}")
    String applicationVersion;

    /**
     * Builds the OpenAPI document used by Swagger UI and client generation tools.
     *
     * @return configured OpenAPI descriptor
     */
    @Bean
    public OpenAPI kampoOpenApi() {

        var openApi = new OpenAPI();

        // ── General info ──────────────────────────────────────────────────────
        openApi.info(new Info()
                        .title(this.applicationName)
                        .description(this.applicationDescription)
                        .version(this.applicationVersion)
                        .contact(new Contact()
                                .name("Acme Kampo Support")
                                .email("support@acme-kampo.com")
                                .url("https://acme-kampo.com/support"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Acme Kampo Platform wiki Documentation")
                        .url("https://acme-kampo.wiki.github.io/docs"));

        // ── Server environments ───────────────────────────────────────────────
        openApi.servers(List.of(
                new Server()
                        .url("http://localhost:8080")
                        .description("Local Development Environment"),
                new Server()
                        .url("https://staging-api.acme-kampo.com")
                        .description("Staging Environment"),
                new Server()
                        .url("https://api.acme-kampo.com")
                        .description("Production Environment")
        ));

        // ── Security scheme (uncomment when JWT auth is added) ────────────────
        /*
        final String securitySchemeName = "bearerAuth";

        openApi.addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT Bearer token for API authentication")));
        */

        return openApi;
    }
}
 