package uk.gov.homeoffice.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@Configuration
@EnableAutoConfiguration
@Import({AppConfig.class})
public class OpenID {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(OpenID.class, args);
    }
}