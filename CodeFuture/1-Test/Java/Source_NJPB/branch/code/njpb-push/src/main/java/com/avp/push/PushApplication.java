package com.avp.push;

/**
 * @author saga
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@SpringBootApplication
@EnableTransactionManagement
public class PushApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PushApplication.class, args);
    }

}
