package henrotaym.env.repositories;

import henrotaym.env.entities.Driver;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface DriverRepository extends JpaRepository<BigInteger, Driver> {
    public List<Driver> findAllDrivers();

    public void saveAllDrivers(List<Driver> drivers);
}
