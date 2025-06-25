package henrotaym.env.repositories;

import henrotaym.env.entities.Episode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface EpisodeRepository extends JpaRepository<Episode, BigInteger> {}
