package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.math.BigInteger;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private BigInteger id;

    @JsonProperty("id")
    @Column(name = "api_character_id", nullable = false, unique = true)
    private Long apiCharacterId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String image;

    @Column(name = "episode_count", nullable = false)
    @JsonIgnore // on ignore cet attribut à la sérialisation JSON (pas dans l'API)
    private Integer episodeCount = 0;

    @Transient
    @JsonProperty("episode") // utilisé uniquement pour l'import depuis l'API
    private List<String> episodeUrls;

    // --- Getters/Setters ---

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Long getApiCharacterId() {
        return apiCharacterId;
    }

    public void setApiCharacterId(Long apiCharacterId) {
        this.apiCharacterId = apiCharacterId;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public List<String> getEpisodeUrls() {
        return episodeUrls;
    }

    public void setEpisodeUrls(List<String> episodeUrls) {
        this.episodeUrls = episodeUrls;

        // Calcul automatique du episodeCount à la réception du JSON
        if (episodeUrls != null) {
            this.episodeCount = episodeUrls.size();
        }
    }
}
