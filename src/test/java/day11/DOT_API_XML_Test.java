package day11;


import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;

public class DOT_API_XML_Test {
    /*
    Send GET Request to https://vpic.nhtsa.dot.gov/api/vehicles/GetMakeForManufacturer/988?format=xml
    and verify the
    count element text is 2
    message Results returned successfully
    first Make_ID is  474 , Make_Name Honda
     */

    @BeforeAll
    public static void init(){
        baseURI = "https://vpic.nhtsa.dot.gov" ;
        basePath = "/api/vehicles" ;
    }
    @AfterAll
    public static void cleanup(){
        reset();
    }

    @DisplayName("XML Test with Vehicles API GET /GetMakeForManufacturer/988?format=xml")
    @Test
    public void testHonda(){
         // what we did yesterday without checking status code
        /*
        XmlPath xp =
        given()
                .queryParam("format","xml").
        when()
                .get("/GetMakeForManufacturer/988")
                .xmlPath();
         */

        // check status code then extract xmlPath
        XmlPath xp =
        given()
                .queryParam("format","xml").
        when()
                .get("/GetMakeForManufacturer/988").
        then()
                .statusCode(200)
                .log().body().
            extract()
                .xmlPath()
                ;

//        count element text is 2
        int countElementText =  xp.getInt("Response.Count") ;
        System.out.println("countElementText = " + countElementText);

//    message Results returned successfully
        String message = xp.getString("Response.Message") ;
        System.out.println("message = " + message);
//    first Make_ID is  474 , Make_Name Honda
        int makeId1 = xp.getInt("Response.Results.MakesForMfg[0].Make_ID");
        String makeName1 = xp.getString("Response.Results.MakesForMfg[0].Make_Name");
        System.out.println("makeId1 = " + makeId1);
        System.out.println("makeName1 = " + makeName1);

        assertThat( countElementText , is(2) ) ;
        assertThat(message , is("Results returned successfully") );
        assertThat( makeId1 , is(474) ) ;
        assertThat(makeName1 , equalToIgnoringCase("Honda") );


    }

    @DisplayName("XML Test with Vehicles API in method chain")
    @Test
    public void testHondaInMethodChain(){

        given()
                .queryParam("format","xml").
        when()
                .get("/GetMakeForManufacturer/988").
        then()
                .log().body()
                .statusCode(200)
                .body("Response.Count" , is("2") )
                .body("Response.Message", is("Results returned successfully"))
                .body("Response.Results.MakesForMfg[0].Make_ID", is("474"))
                .body("Response.Results.MakesForMfg[0].Make_Name", is("HONDA"))
        ;


    }



}
