package henrotaym.env.services;

import henrotaym.env.entities.Driver;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JsonPlaceholderService {
    private final String TODO_API_URL = "https://f1connectapi.vercel.app";
    private final RestTemplate restTemplate;

    public JsonPlaceholderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Driver> getDrivers() {
        ResponseEntity<List<Driver>> exchange =
                restTemplate.exchange(
                        TODO_API_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Driver>>() {});
        return exchange.getBody();
    }
}
