package com.example.backend.controller;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import com.example.backend.model.User;
import com.example.backend.repository.SqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

    @Component
    public class MySqlController implements CommandLineRunner {

        LocalDate localDate = LocalDate.now(); // Example LocalDate
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        @Autowired
        SqlRepository eRepo;

        @Override
        public void run(String... args) throws Exception {

            eRepo.saveAll(Arrays.asList(

                    new User("Yannik", "schwarz.yannik@online.de", "testtoken", "testrefrshtoken", date))

            );

            System.out.println("----------All Data saved into Database----------------------");
        }

    }
