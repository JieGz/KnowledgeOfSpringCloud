package com.knowledge.dead.letter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeadLetterApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeadLetterApplication.class, args);
    }
}
