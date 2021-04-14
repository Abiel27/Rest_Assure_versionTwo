package day6;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import test_util.HR_ORDS_API_BaseTest;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class HR_ORDS_ParametrizedTest extends HR_ORDS_API_BaseTest {

    //GET http://18.235.32.166:1000/ords/hr/api/regions
    // base_uri = http://18.235.32.166:1000
    // base_path =  /ords/hr/api
    // resources /countries/{country_id}

   @Test
   public void testCountries(){

//       get("/countries").prettyPeek();
       get("/countries/AR").prettyPeek();
   }

   @ParameterizedTest
   @ValueSource(strings = {"AR", "AU" ,"US"}     )
   public void testSingleCountryWithValues( String countryIdArg ){

       // GET /countries/{country_id}
       given()
               .log().uri()
               .pathParam("country_id", countryIdArg).
       when()
               .get("/countries/{country_id}").
       then()
               .log().body()
               .statusCode(200)
                .body("count", is(1) )
       ;

   }

   @ParameterizedTest
   @CsvSource({
           "AR, Argentina",
           "US, United States of America",
           "UK, United Kingdom"
   })
   public void testSingleCountryWithCSVSource(String countryIdArg, String countryNameArg){

       // SEND request to GET /countries/{country_id}
       // Expect country name match the corresponding country id
       given()
               .log().uri()
               .pathParam("country_id",countryIdArg ).
        when()
                .get("/countries/{country_id}").
        then()
               .body("items[0].country_name", is(  countryNameArg  ) )
        ;
   }

   @ParameterizedTest
   @MethodSource("getManyCountryIds")
   public void testCountryWithMethodSource(String countryIdArg){

       System.out.println("countryIdArg = " + countryIdArg);
       // GET /countries/{country_id}
       given()
               .log().uri()
                .pathParam("country_id", countryIdArg).
       when()
               .get("/countries/{country_id}").
       then()
               .log().body()
               .statusCode(200)
               .body("count", is(1) )
       ;

   }



   // write static method that return list of country ids
    public static List<String> getManyCountryIds(){

        //List<String> countryNameList = Arrays.asList("AR", "BE","US") ;
        // SEND REQUEST TO GET /countries and save the country_id as List<String>

        List<String> countryNameList = get("/countries")
                                    .jsonPath().getList("items.country_id", String.class) ;

        return countryNameList ;

    }





}
