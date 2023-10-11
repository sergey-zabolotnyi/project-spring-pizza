package de.telran.pizza.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Swagger Pizza Tel-Ran App",
                description = "Application for testing Swagger framework",
                version = "1.0.0",
                contact = @Contact(
                        name = "Sergey Zabolotnyi",
                        email = "cvz@ukr.net",
                        url = "https://tel-ran.de"
                )
        )
)
public class SwaggerConfig {

}
