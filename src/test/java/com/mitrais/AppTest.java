package com.mitrais;

import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;

/**
 * Unit test for simple App.
 */
@SpringBootApplication
public class AppTest extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppTest.class, args);
    }

    @Bean
    @Primary
    Clock clock() {
        return Mockito.mock(Clock.class);
    }

}