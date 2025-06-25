package henrotaym.env.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import henrotaym.env.entities.Driver;
import henrotaym.env.repositories.DriverRepository;

@Service
public class DriverService {
    private static final Logger LOG = LoggerFactory.getLogger(DriverService.class);
    private final DriverRepository driverRepository;
    private final RestTemplate restTemplate;

    public DriverService(DriverRepository driverRepository, RestTemplate restTemplate) {
        this.driverRepository = driverRepository;
        this.restTemplate = restTemplate;
    }

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public void saveAll(List<Driver> drivers) {
        driverRepository.saveAll(drivers);
    }
}
