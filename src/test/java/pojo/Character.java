package pojo;


//character_id , name , occupation , nickname , appearance

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {

    @JsonProperty("char_id")
    private int character_id ;
    private String name ;
    private List<String> occupation ;
    private String nickname ;
    private List<Integer> appearance ;



}
