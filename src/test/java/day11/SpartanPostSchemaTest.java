package day11;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Spartan;
import spartan_util.SpartanUtil;
import test_util.SpartanWithAuthBaseTest;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SpartanPostSchemaTest extends SpartanWithAuthBaseTest {

    @DisplayName("Test Json Schema for Post Response")
    @Test
    public void testAdd1SpartanResponse(){

        Spartan bodyPOJO = SpartanUtil.getRandomSpartanPOJO() ;
        System.out.println("bodyPOJO = " + bodyPOJO);

        given()
               .log().body()
               .auth().basic("admin","admin")
               .contentType(ContentType.JSON)
               .body(bodyPOJO).
        when()
                .post("/spartans").
        then()
                .statusCode(201)
                .log().all()
                .body(matchesJsonSchemaInClasspath("spartanPostJsonSchema.json") )
                .body(matchesJsonSchema(new File("src/test/resources/spartanPostJsonSchema.json")));
        ;
        // if the schema file is in different location (anywhere other than resources folder)
        // then we use matchesJsonSchema ( new File("Your full path goes here"))

        // as a homework
        // try to store the request spec into reusable variable
        // try to add the body validation into response spec

    }


}
