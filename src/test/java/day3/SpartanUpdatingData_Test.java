package day3;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Spartan;
import test_util.SpartanNoAuthBaseTest;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SpartanUpdatingData_Test extends SpartanNoAuthBaseTest {

    // you may repeat everything we did previously in post test for providing body
    // we will just look at Map and POJO

    @DisplayName("PUT /spartans/{id} body as Map")
    @Test
    public void testUpdateDataWithMap(){

        Map<String,Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name","Diana");
        bodyMap.put("gender","Female");
        bodyMap.put("phone",1800233232L);

        given()
                .log().all()
                .pathParam("id", 33)
                .contentType(ContentType.JSON)
                .body(bodyMap).
        when()
                .put("/spartans/{id}").
        then()
                .statusCode(204) ;


    }

    @DisplayName("PUT /spartans/{id} body as POJO")
    @Test
    public void testUpdateDataWithPOJO(){

        Spartan sp = new Spartan("Dean", "Male", 1231231239L) ;
        given()
                .log().all()
                .pathParam("id", 33)
                .contentType(ContentType.JSON)
                .body(sp).
        when()
                .put("/spartans/{id}").
        then()
                .statusCode(204) ;



    }

    @DisplayName("PATCH /spartans/{id} body as String")
    @Test
    public void testPartialUpdateDataWithString(){

        String patchBody = " {\"phone\":1234567890 } " ;
        System.out.println(patchBody); // {"phone":1234567890 }

        given()
                .log().all()
                .pathParam("id",33)
                .contentType(ContentType.JSON)
                .body(patchBody).
        when()
                .patch("/spartans/{id}").
        then()
                .statusCode(204) ;

    }


    @DisplayName("Test DELETE /spartans/{id} ")
    @Test
    public void testDeleteOne(){

        given()
                .log().uri()
                .pathParam("id" , 1001).
        when()
                .delete("/spartans/{id}").
        then()
                .statusCode( equalTo(204) ) ;


    }


}
