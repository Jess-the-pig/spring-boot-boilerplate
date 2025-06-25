package henrotaym.env.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import henrotaym.env.entities.Episode;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EpisodeListResponse {
    @JsonProperty("results")
    private List<Episode> episodes;

    // Getters et setters
    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
