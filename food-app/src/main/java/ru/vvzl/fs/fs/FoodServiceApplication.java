package ru.vvzl.fs.fs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;


@SpringBootApplication
public class FoodServiceApplication {
    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start();
        try {
            SpringApplication.run(FoodServiceApplication.class, args);
            watch.stop();
            System.out.println("FoodService context started at " + (watch.getTotalTimeSeconds()) + " seconds");

        } catch (Exception e) {
            System.out.println("Unable to start application FoodService");
        }

    }

}
