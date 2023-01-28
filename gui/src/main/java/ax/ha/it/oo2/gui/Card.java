package ax.ha.it.oo2.gui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Card(String code, String image, String value, String suit) {
    @JsonCreator
    public Card(@JsonProperty("code") String code, @JsonProperty("image") String image,
                @JsonProperty("value") String value, @JsonProperty("suit") String suit) {
        this.code = code;
        this.image = image;
        this.value = value;
        this.suit = suit;
    }
}
