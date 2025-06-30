package henrotaym.env.repositories;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import henrotaym.env.entities.Character;

public interface CharacterRepository extends JpaRepository<Character, BigInteger> {
    public Optional<Character> findByApiCharacterId(Long apiCharacterId);
}
