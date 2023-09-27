package com.ti.tubeminer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.security.GeneralSecurityException;


//@EnableJpaRepositories(
//        entityManagerFactoryRef = "entityManagerFactory",
//        transactionManagerRef = "transactionManager",
//        basePackages =  "com.ti" )
//@EnableTransactionManagement
//@ComponentScan(basePackages =  "com.ti")
@SpringBootApplication
public class TubeMinerApplication {

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        SpringApplication.run(TubeMinerApplication.class, args);
    }
}
