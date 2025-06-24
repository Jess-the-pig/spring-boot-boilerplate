package henrotaym.env;

import henrotaym.env.entities.Driver;
import henrotaym.env.services.DriverService;
import henrotaym.env.services.JsonPlaceholderService;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@Slf4j
public class EnvApplication {

    private static final Logger log = LoggerFactory.getLogger(EnvApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EnvApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner commandLineRunner(
            JsonPlaceholderService placeHolderService, DriverService driverService) {
        return args -> {
            List<Driver> drivers = placeHolderService.getDrivers();
            driverService.saveAll(drivers);
            log.info("Saved {} todos in the database", drivers.size());
        };
    }
}
