package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.cache.annotation.CacheResult;

import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@SpringBootApplication
@EnableCaching
public class CachingBootifulWebinarApplication {

    public static interface CityService {
        // @Cacheable("city")
        @CacheResult(cacheName = "city")
        public String getCity();
    }

    @Bean
    public CityService getCity() {
        return new CityService() {
            @Override public String getCity() {
                try {
                    // slow method
                    SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "NYC";
            }
        };
    }

    /*@Bean
    HazelcastInstance getInstance() {
        // return HazelcastClient.newHazelcastClient();
        return Hazelcast.newHazelcastInstance();
    }*/


    @Component
    public static class MyCommandLineRunner implements CommandLineRunner {
        @Autowired
        CityService service;

        @Override public void run(String... args) throws Exception {
            System.out.println("Calling for city");
            long start = nanoTime();
            System.out.println(service.getCity());
            System.out.println(String.format("Took: %s mills", (NANOSECONDS.toMillis(nanoTime() - start))));

            System.out.println("Calling for city");
            start = nanoTime();
            System.out.println(service.getCity());
            System.out.println(String.format("Took: %s mills", (NANOSECONDS.toMillis(nanoTime() - start))));

            System.out.println("Calling for city");
            start = nanoTime();
            System.out.println(service.getCity());
            System.out.println(String.format("Took: %s mills", (NANOSECONDS.toMillis(nanoTime() - start))));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CachingBootifulWebinarApplication.class, args);
    }
}
