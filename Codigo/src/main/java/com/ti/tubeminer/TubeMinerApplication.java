package com.ti.tubeminer;

import com.ti.tubeminer.github.GitHubService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;


@SpringBootApplication
public class TubeMinerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TubeMinerApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner (
            GitHubService service ) {
        return args -> {
            service.request();
        };

    };
}
