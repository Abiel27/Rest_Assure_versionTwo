package day3;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Spartan;
import test_util.SpartanNoAuthBaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SpartanPost_NegativeTest extends SpartanNoAuthBaseTest {


    @DisplayName("Post request without content type expect 415")
    @Test
    public void test1(){

        Spartan sp = new Spartan("B21", "Male" , 1231231231L) ;
                given()
                        .log().body()
                        .body(sp).
                when()
                        .post("/spartans").
                then()
                        .log().all()
                        .statusCode(415) ;


    }

    @DisplayName("Post request without valid json expect 400")
    @Test
    public void test2(){

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(" BAD JSON STRUCTURE HERE ").
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400)


        ;


    }

    @DisplayName("Post request with valid json , bad name - expect 400 with detailed name error message")
    @Test
    public void test3(){

        Spartan sp = new Spartan("a", "Male" , 1231231231L) ;

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(sp).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400)
                .body("message",is("Invalid Input!") )
                .body("errorCount",equalTo(1) )
                .body("errors[0].reason" , is("name should be at least 2 character and max 15 character") )


        ;



    }

    @DisplayName("Post request with bad name, phone - expect 400 with detailed name, phone error message")
    @Test
    public void test4(){

    }


    @DisplayName("Post request with bad name, phone , gender - expect 400 with 3 errors")
    @Test
    public void test5(){

    }

}
