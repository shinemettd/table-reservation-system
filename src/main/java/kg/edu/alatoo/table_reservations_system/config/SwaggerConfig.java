package kg.edu.alatoo.table_reservations_system.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer customConfig() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
                        .forEach(operation -> operation.getResponses().values().forEach(response ->
                                response.setContent(null))));
    }

}
