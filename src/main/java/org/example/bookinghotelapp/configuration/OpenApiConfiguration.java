package org.example.bookinghotelapp.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.setName("Stanislav Grigorev");
        contact.setEmail("stas31121994@gmail.com");

        Info info = new Info();
        info.setTitle("Bookings hotel API");
        info.setVersion("1.0.0");
        info.setContact(contact);
        info.setDescription("This is a booking hotel API");

        return new OpenAPI().info(info);
    }
}
