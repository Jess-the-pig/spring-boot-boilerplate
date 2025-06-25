package henrotaym.env.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import henrotaym.env.entities.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer> {}
