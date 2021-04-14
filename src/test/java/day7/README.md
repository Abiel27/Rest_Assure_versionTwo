# NOTE Day 7 

## Serialization

Java Object to Json (or any other type of text|byteStream)
   ```java
       Spartan sp1 = new Spartan("B21" , "Male",123123123) ; 
   ```
   We provided this object in `POST` request body and expect it to be converted to json string as below
```json
{
    "name": "B21",
    "gender": "Male",
    "phone": 123123123
}
```

## De-Serialization 
Json(text | byteStream) to Java
    
```json
    {
        "id": 8001,
        "name": "Karley",
        "gender": "Male",
        "phone": 1616992336
    }
```
We created POJO class that with 4 fields matches exactly as the json fields 

Easy way to create POJO is using [**Project Lombok**](https://projectlombok.org/features/all)
* Added dependency
```xml
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.18</version>
    </dependency>
```
* Make sure the `Lombok` plugin is installed in IntelliJ.

Lombok use series of annotation to remove repetitive boiler-plate codes. 
```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SpartanPOJO {

    private int id;
    private String name;
    private String gender;
    private long phone;
}
```

Two ways we tried to save json result as POJO 
* using `Response` object `as(Class Type)` method if no json path is needed to get the json object. 
    ```java
    Response response = given()
                        .pathParam("id", 8001).
                        when()
                        .get("/spartans/{id}") ;

    SpartanPOJO sp = response.as(SpartanPOJO.class) ;
    ```
* using `JsonPath` object `getObject("json path",Class Type)` if json path is needed to get to the json object.
    ```java
    Response response =
            given()
                    .log().uri()
                    .queryParam("nameContains", "a")
                    .queryParam("gender", "Male").
            when()
                    .get("/spartans/search") ; //.prettyPeek() ;

    // response.as will not work here because we need to provide
    // path to get to the json objet we want  content[0]
    JsonPath jp = response.jsonPath() ;
    SpartanPOJO sp = jp.getObject("content[0]", SpartanPOJO.class );
    ```
  or in one shot in method chain
    ```java
    SpartanPOJO sp1 =  given()
                            .log().uri()
                            .queryParam("nameContains", "a")
                            .queryParam("gender", "Male").
                        when()
                            .get("/spartans/search")
                            .jsonPath()
                            .getObject("content[0]", SpartanPOJO.class );
    ```

  We can also use `getList` method of `JsonPath` object to get the json array into `List<POJO>`
    ```java
    List<SpartanPOJO> lst =  given()
                                .queryParam("nameContains", "a")
                                .queryParam("gender","Male")
                            .when()
                                .get("/spartans/search")
                                .jsonPath()
                                .getList("content", SpartanPOJO.class) ;
    ```
  Here is the example with HR ORDS API 
   ```java
    // Save all countries as List<POJO>
    List<Country> allCountries = get("/countries").jsonPath()
                                .getList("items", Country.class) ;
   ```
  Here is the [full practice examples](HR_ORDS_API_DeserializationTest.java)

Just in case here is the link for HR ORDS Collection
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/55ab4b3f5ab134475f6a?action=collection%2Fimport)

## Useful Jackson Library annotations 

* @JsonIgnoreProperties(ignoreUnknown=true)
```java
// we just want to represent the Employee data with these fields and ignore any other fields
@Getter @Setter
@ToString
// we want to ignore any json field that does not match below pojo class fields
@JsonIgnoreProperties(ignoreUnknown = true)

public class Employee {

    private int employee_id;
    private String first_name;
    private String last_name;
    private int salary;
    private int department_id;

}
```
* @JsonProperties("Your Json Field Name goes here")
This is the json object 
```json
{
    "department_id": 10,
    "department_name": "Administration",
    "manager_id": 200,
    "location_id": 1700
}
```
This is the POJO we want to use to save above json 
```java
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@ToString

public class Department {
    // instructing jackson to bind json field called department_id to below java field
    @JsonProperty("department_id")
    private int departmentId;  //json field : department_id

    @JsonProperty("department_name")
    private String name;       //json field : department_name
    private int manager_id;
    private  int location_id;

}
```

You can also have POJO field inside another POJO 
for example given below Json 
```json
{
    "Title": "Avenger",
    "Year": "2006",
    "Released": "09 Apr 2006",
    "Language": "English",
    "Ratings": [
        {
            "Source": "Internet Movie Database",
            "Value": "5.7/10"
        },
        {
            "Source": "Rotten Tomatoes",
            "Value": "29%"
        }
    ]
}
```
We can represent the whole json Object as `Movie` POJO , 
We can represent `Ratings` Json Array as `List<Rating>` so the pojo can be as below 

* Rating POJO class
```java
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
public class Rating {
    @JsonProperty("Source")
    private String source;
    @JsonProperty("Value")
    private String value;
}
```
* Movie POJO class
```java
import lombok.*;

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
```

Jackson library will take care of all the conversion whenever it can. 


Breaking bad example : 
```json
    {
        "char_id": 1,
        "name": "Walter White",
        "occupation": [
            "High School Chemistry Teacher",
            "Meth King Pin"
        ],
        "nickname": "Heisenberg",
        "appearance": [
            1,
            2,
            3,
            4,
            5
        ]
    }
```
This can be represented in Character POJO below 
```java
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
```
Saving it into List<Character> and printing will generate result like below 
```
Character(character_id=1, name=Walter White, occupation=[High School Chemistry Teacher, Meth King Pin], nickname=Heisenberg, appearance=[1, 2, 3, 4, 5])
Character(character_id=2, name=Jesse Pinkman, occupation=[Meth Dealer], nickname=Cap n' Cook, appearance=[1, 2, 3, 4, 5])
Character(character_id=3, name=Skyler White, occupation=[House wife, Book Keeper, Car Wash Manager, Taxi Dispatcher], nickname=Sky, appearance=[1, 2, 3, 4, 5])
Character(character_id=4, name=Walter White Jr., occupation=[Teenager], nickname=Flynn, appearance=[1, 2, 3, 4, 5])
...
```
> OPTIONALLY Advanced JsonPath with Groovy example can be found here

[Rest Assured Doc for below example](https://github.com/rest-assured/rest-assured/wiki/Usage#example-3---complex-parsing-and-validation)

[Advance JsonPath with groovy example](https://github.com/Cybertek-B20/RestAssured_B20/blob/master/src/test/java/day09/AdvancedJsonPathWithGroovy.java)













