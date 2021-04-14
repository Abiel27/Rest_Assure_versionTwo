package pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Country {

    private String country_id;
    private String country_name;
    private int region_id;

}
