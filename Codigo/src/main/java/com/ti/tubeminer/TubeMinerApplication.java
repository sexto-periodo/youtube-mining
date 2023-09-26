package com.ti.tubeminer;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.ti.tubeminer.github.GitHubService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.security.GeneralSecurityException;



@SpringBootApplication
public class TubeMinerApplication {

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        SpringApplication.run(TubeMinerApplication.class, args);
    }
}
