package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
                   Student mariam = new Student(
                            "Mariam",
                            "mariam.jamal@gmail.com",
                            LocalDate.of(2000, 1, 8),
                            23
                    );
            Student alex = new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(1999, 9, 8),
                    24
            );
            repository.saveAll(List.of(mariam, alex));


        };
    }
}
