package day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.SpartanNoAuthBaseTest;

import static io.restassured.RestAssured.given;

public class SpartanTest_QueryParam extends SpartanNoAuthBaseTest {

    @DisplayName("Test GET /spartans/search Endpoint")
    @Test
    public void testSearch(){

        Response response =
                given()
                    .log().all() // this will log everything about request
                    .queryParam("nameContains", "Mustafa")
                    .queryParam("gender","Male").
                when()
                    .get("/spartans/search")
                    .prettyPeek();
        // print the totalElement field value from the response
        System.out.println("response.path(\"totalElement\") = "
                + response.path("totalElement"));

    }

}
