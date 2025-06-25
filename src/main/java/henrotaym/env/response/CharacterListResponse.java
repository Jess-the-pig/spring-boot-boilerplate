package henrotaym.env.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import henrotaym.env.entities.Character;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterListResponse {
    @JsonProperty("results")
    private List<Character> characters;

    // Getters et setters
    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
