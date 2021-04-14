package day5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class CSVFileSourceParameterizedTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/state_city_zipCount.csv" , numLinesToSkip = 1 )
    public void testStateCityToZipEndpointWithCSVFile(String stateArg, String cityArg , int zipArg  ){

        System.out.println("stateArg = " + stateArg);
        System.out.println("cityArg = " + cityArg);
        System.out.println("zipArg = " + zipArg);

        // Write a parameterized test for this request
        // Get the data from csv source
        // GET https://api.zippopotam.us/us/{state}/{city}
        given()
                .baseUri("https://api.zippopotam.us")
                .pathParam("state" , stateArg)
                .pathParam("city", cityArg)
                .log().uri().
        when()
                .get("/us/{state}/{city}").
        then()
                .statusCode(200)
                .body("places" , hasSize(  zipArg   ))
        ;

    }


}
