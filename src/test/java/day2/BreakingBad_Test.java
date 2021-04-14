package day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class BreakingBad_Test {

    //https://www.breakingbadapi.com/api/characters?name=Walter
    @BeforeAll
    public static void init(){
        baseURI     = "https://www.breakingbadapi.com";
        basePath    = "/api" ;
    }
    @AfterAll
    public static void cleanup(){
        reset();
    }

    @DisplayName("GET /characters with name query param")
    @Test
    public void testFilterCharacterName(){

        given()
                .log().uri()
                .queryParam("name", "Walter").
        when()
                .get("/characters").
        then()
//                .assertThat() // this is coming from restassured, it's just for readability
                .log().all()
                //and() // this is just for readability
                .statusCode(200)
                .header("Content-Type","application/json; charset=utf-8")
                .contentType("application/json; charset=utf-8")
                ;

    }

    @DisplayName("Test GET /characters/{char_id}")
    @Test
    public void test1Character(){

        given()
                .pathParam("char_id" , 1 )
                .log().uri().
        when()
                .get("/characters/{char_id}").
        then()
                .log().all()
                .statusCode(200)
                .header("Content-Type","application/json; charset=utf-8")
                .contentType("application/json; charset=utf-8")
        ;


    }

    // /episodes/60
    @DisplayName("Test GET /episodes/{episode_id}")
    @Test
    public void test1Episode(){

            given()
                    .pathParam("episode_id", 60)
                    .log().all().
            when()
                    .get("/episodes/{episode_id}").
            then()
                    .log().all()
                    .statusCode(200)
                    .contentType( ContentType.JSON ) ;

    }







}
