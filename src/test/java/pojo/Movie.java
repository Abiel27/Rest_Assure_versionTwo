package pojo;

//title Str, year int , released str ,language

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    @JsonProperty("Title")
    private String title ;
    @JsonProperty("Year")
    private int year ;
    @JsonProperty("Released")
    private String released ;
    @JsonProperty("Language")
    private String language ;

    @JsonProperty("Ratings")
    private List<Rating> allRatings ;

}
