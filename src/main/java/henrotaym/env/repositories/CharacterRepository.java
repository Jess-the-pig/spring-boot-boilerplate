package henrotaym.env.repositories;

import com.querydsl.core.types.Predicate;

import henrotaym.env.entities.Character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.math.BigInteger;
import java.util.Optional;

public interface CharacterRepository
        extends JpaRepository<Character, BigInteger>, QuerydslPredicateExecutor<Character> {
    Optional<Character> findOne(Predicate predicate);

    boolean exists(Predicate predicate);
}
