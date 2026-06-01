package com.acme.kampo.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class KampoPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(KampoPlatformApplication.class, args);
    }

}
