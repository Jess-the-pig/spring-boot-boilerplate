package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "episodes")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private BigInteger id;

    @JsonProperty("id")
    private Long apiEpisodeId;

    private String name;

    @JsonProperty("episode")
    private String episodeCode;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Long getApiEpisodeId() {
        return apiEpisodeId;
    }

    public void setApiEpisodeId(Long apiEpisodeId) {
        this.apiEpisodeId = apiEpisodeId;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getEpisodeCode() {
        return episodeCode;
    }

    public void setEpisodeCode(@Nullable String episodeCode) {
        this.episodeCode = episodeCode;
    }
}
