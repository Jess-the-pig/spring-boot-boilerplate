package henrotaym.env;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import henrotaym.env.entities.Driver;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverListResponse {
    private List<Driver> drivers;

    // Getters et setters
    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
