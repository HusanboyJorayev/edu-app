package org.example.eduapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Logistic APIs", // Optional: Qo'shimcha ma'lumot berish uchun
                version = "2.0", // OpenAPI versiyasi noto'g'ri emas, faqat ma'lumot sifatida
                description = "API Documentation"
        ),
        servers = {
                @Server(url = "http://localhost:9876", description = "Local Server")
                //@Server(url = "https://api.logistx.uz", description = "Prod Server")
        },
        security = @SecurityRequirement(name = "bearerAuth") // Apply globally
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT", // Optional: Defines the format (JWT)
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization"
)
public class SwaggerConfig {

}
