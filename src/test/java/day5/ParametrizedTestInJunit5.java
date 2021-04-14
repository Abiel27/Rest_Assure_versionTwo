package day5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParametrizedTestInJunit5 {

    @ParameterizedTest
    @ValueSource( ints = {1, 2,4,6,7,10} )
    public void testPrintMultipleIteration(  int num   ){

//        int num = 10 ;
        System.out.println("num = " + num);
        assertTrue(  num > 0 ) ;

    }


    @ParameterizedTest
    @ValueSource(strings = {"Mustafa","Oguljeren","Diana","Erjon"} )
    public void testNames(  String name  ) {

        // assertThat all the names has more than 5 characters
        System.out.println("name = " + name);
        assertTrue(name.length() >= 5 );


    }

    // SEND GET REQUEST TO https://api.zippopotam.us/us/{zipcode}
    // with these zipcodes 22030,22031, 22032, 22033 , 22034, 22035, 22036
    // check status code 200

    @ParameterizedTest
    @ValueSource(shorts = { 22030,22031, 22032, 22033 , 22034, 22035, 22036})
    public void testZipcodes( short zip  ){

        System.out.println("zipcode = " + zip);

        given()
                .baseUri("https://api.zippopotam.us")
                .log().all()
                .pathParam("zipcode" ,   zip  ).
        when()
                .get("/us/{zipcode}").
        then()
                .statusCode( 200 )
                .log().all()
                ;



    }













}
