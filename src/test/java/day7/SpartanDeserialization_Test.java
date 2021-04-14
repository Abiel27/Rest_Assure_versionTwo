package day7;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.SpartanPOJO;
import test_util.SpartanNoAuthBaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;

public class SpartanDeserialization_Test extends SpartanNoAuthBaseTest {

    // Serialization    : Java Object to Json (or any other type of text|byteStream)
    // De-Serialization : Json(text | byteStream) to Java

    @DisplayName("GET /spartans/{id}")
    @Test
    public void testGetOneData(){

        given()
                .pathParam("id", 8001).
        when()
                .get("/spartans/{id}").
        then()
                .statusCode(200)
                .log().body() ;

    // Send same request , store the result into SpartanPOJO object

        Response response = given()
                            .pathParam("id", 8001).
                            when()
                            .get("/spartans/{id}") ;

        SpartanPOJO sp = response.as(SpartanPOJO.class) ;
        System.out.println("sp = " + sp);

        JsonPath jp = response.jsonPath();
        SpartanPOJO sp1 = jp.getObject("",SpartanPOJO.class) ;
        System.out.println("sp1 = " + sp1);


    }

    @DisplayName("GET /spartans/search")
    @Test
    public void testSearch(){

        ///spartans/search?nameContains=a&gender=Male
        // send get request to above endpoint and save first object with type SpartanPOJO

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
        System.out.println("sp = " + sp);

        // this is how we can do whole thing in one chain ;

        SpartanPOJO sp1 =  given()
                                .log().uri()
                                .queryParam("nameContains", "a")
                                .queryParam("gender", "Male").
                            when()
                                .get("/spartans/search")
                                .jsonPath()
                                .getObject("content[0]", SpartanPOJO.class );
        System.out.println("sp1 = " + sp1);

    }


    @DisplayName("GET /spartans/search and save as List<SpartanPOJO>")
    @Test
    public void testSearchSaveList(){

        //spartans/search?nameContains=a&gender=Male
        // send get request to above endpoint and save the json array into List<SpartanPOJO>

        List<SpartanPOJO> lst =  given()
                                    .queryParam("nameContains", "a")
                                    .queryParam("gender","Male")
                                .when()
                                    .get("/spartans/search")
                                    .jsonPath()
                                    .getList("content", SpartanPOJO.class) ;
                                // something.class return type class to specify what kind of Item you want to have in your list
        System.out.println("lst = " + lst);
        lst.forEach( blabla -> System.out.println(blabla) );



    }



}
