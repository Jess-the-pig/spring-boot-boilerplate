package henrotaym.env.repositories;

import henrotaym.env.entities.Character;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CharacterRepository extends JpaRepository<Character, BigInteger> {}
