package day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.SpartanNoAuthBaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DisplayName("Spartan Test with path variable")
public class SpartanTest_PathVariable extends SpartanNoAuthBaseTest {

    @Test
    public void getOneSpartan(){

        //get("/spartans/16").prettyPeek();

        // I want to provide 16 as path variable|parameter
        // I want to provide accept header

        Response r1 =
                given()
                        .header("Accept", "application/json")
                        .pathParam("spartan_id",16).
                when()
                        .get("/spartans/{spartan_id}")
                        .prettyPeek()
                ;

        Response r2 =
                given()
                        // this is same as .header("Accept", "application/json")
                        .accept("application/json").
                when()
                        // This is alternative way of providing
                        // path variable and value directly in get method
                        .get("/spartans/{spartan_id}" , 16 )
                        .prettyPeek() ;
    }

    @DisplayName("logging the request")
    @Test
    public void getOneSpartanWihLog(){

        Response response =
                given()
                    .log().all()
                    .accept("application/json")
                    .pathParam("id",16).
                when()
                    .get("/spartans/{id}")
                    .prettyPeek();
                ;

        assertThat(response.statusCode() , equalTo(200) );
        assertThat(response.contentType() , is("application/json"));
        assertThat(response.path("name") , is("Wonder Woman")     );




    }









}
