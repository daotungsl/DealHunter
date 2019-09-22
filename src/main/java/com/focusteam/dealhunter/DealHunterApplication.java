package com.focusteam.dealhunter;

import com.focusteam.dealhunter.util.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class DealHunterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealHunterApplication.class, args);
    }

}
