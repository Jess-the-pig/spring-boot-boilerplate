package henrotaym.env.entities;

import jakarta.persistence.Id;

public record Driver(
        @Id Integer id,
        String driverId,
        String name,
        String surname,
        String nationality,
        String birthday,
        Integer number,
        String shortname,
        String url) {}
