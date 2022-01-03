package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Configuracion Swagger para la generacion de la documentacion de la API REST
 * HTML: http://localhost:8080/swagger-ui/
 * JSON: http://localhost:8080/v2/api-docs
 * La version 2.6 de Spring aun no es compatible con Swagger por lo que hay
 * que bajar a la version 2.5.7
 */

@Configuration
public class SwaggerConfig {

    @Bean // Para que sea escaneado por Spring
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    //Metodo que construye el objeto ApiInfo necesario en .apiInfo()
    private ApiInfo apiDetails(){
        return new ApiInfo("Spring Boot Laptop API REST",
                            "library Api rest docs",
                             "1.0",
                              "http://www.prueba-laptops.example",
                              new Contact("Rafa","http://www.rafa-laptops.example","rafa@mail.com"),
                              "MIT",
                                "http://www.license-laptops.example",
                                Collections.emptyList());
    }
}
