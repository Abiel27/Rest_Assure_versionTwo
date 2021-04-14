package pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Dog{
    private String breed ;
    private String color ;
    private int age;
    private Owner owner ;
}
