package day10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.SpartanNoAuthBaseTest;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class jsonSchemaValidationTest extends SpartanNoAuthBaseTest {


    @DisplayName("Check GET /spartans/{id} json schema")
    @Test
    public void test1SpartanJsonSchema(){

        given()
                .pathParam("id" , 8005).
        when()
                .get("/spartans/{id}").
        then()
                .log().body()
//                .statusCode(200)
                // check the response body against
                // the schema file singleSpartanSchema.json we added under resources folder
                .body(  matchesJsonSchemaInClasspath("singleSpartanSchema.json")  )
        ;
    }

    @DisplayName("Check GET /spartans json schema")
    @Test
    public void testAllSpartansJsonSchema(){


        when()
                .get("/spartans").
        then()
                .body(matchesJsonSchemaInClasspath("allSpartansSchema.json")  )
                // what if this schema file was under day 10 package
                .body(matchesJsonSchema( new File("src/test/java/day10/allSpartansSchema.json")   ))


        ;
    }




    }
