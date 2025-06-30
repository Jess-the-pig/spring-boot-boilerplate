package henrotaym.env.database.factories;

import henrotaym.env.entities.Character;

import net.datafaker.Faker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class CharacterFactory extends EntityFactory<Character> {

    public CharacterFactory(Faker faker, JpaRepository<Character, BigInteger> repository) {
        super(faker, repository);
    }

    @Override
    public Character entity() {
        return new Character();
    }

    @Override
    public void attributes(Character character) {
        character.setApiCharacterId(faker.number().numberBetween(1L, 20L));
        character.setName(faker.rickAndMorty().character());
        character.setStatus("ALIVE");
        character.setImage(
                "https://rickandmortyapi.com/api/character/avatar/"
                        + faker.number().numberBetween(1, 100)
                        + ".jpeg");
        character.setEpisodeCount(0);
    }

    public Character createWithApiCHaracterId(Long apiCharacterId) {
        return this.create(character -> character.setApiCharacterId(apiCharacterId));
    }
}
