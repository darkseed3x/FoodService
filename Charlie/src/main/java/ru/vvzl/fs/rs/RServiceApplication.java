package ru.vvzl.fs.rs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

@SpringBootApplication
public class RServiceApplication {
    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start();
        try {
            SpringApplication.run(RServiceApplication.class ,args);
            watch.stop();
            System.out.println("RService context started at " + (watch.getTotalTimeSeconds()) + " seconds");

        }catch (Exception e){
            System.out.println("Unable to start application RService");
        }

    }
}
