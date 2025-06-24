package henrotaym.env.http.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import henrotaym.env.entities.Driver;
import henrotaym.env.services.DriverService;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;
    private final RestTemplate restTemplate;

    public DriverController(DriverService driverService, RestTemplate restTemplate) {
        this.driverService = driverService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("path")
    public List<Driver> findAllDrivers() {
        return driverService.findAll();
    }
}
