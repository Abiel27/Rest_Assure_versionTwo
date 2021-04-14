package day10;

import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class FormulaOneAPI_Test {

    @BeforeAll
    public static void init(){
        baseURI = "http://ergast.com";
        basePath = "/api/f1/";
    }

    @DisplayName("Test GET /drivers/{driver_id}")
    @Test
    public void testDriverOne(){

        // send request to get information of driver_id alonso and save  xml result and assert or assert in the chain
        XmlPath xp = given()
                        .pathParam("driver_id","alonso").
                     when()
                        .get("/drivers/{driver_id}")
                        .xmlPath() ;

        String givenName =  xp.getString("MRData.DriverTable.Driver.GivenName") ;
        System.out.println("givenName = " + givenName);
        String familyName =  xp.getString("MRData.DriverTable.Driver.FamilyName") ;
        System.out.println("familyName = " + familyName);


    }

    @DisplayName("Test GET /drivers")
    @Test
    public void testAllDrivers(){

        XmlPath xp = get("/drivers").xmlPath() ;
        // get first given name
        String firstGivenName = xp.getString("MRData.DriverTable.Driver[0].GivenName");
        System.out.println("firstGivenName = " + firstGivenName);
        // get third nationality
        String thirdNationality = xp.getString("MRData.DriverTable.Driver[2].Nationality");
        System.out.println("thirdNationality = " + thirdNationality);
        // get all last names
        List<String> allLastNames = xp.getList("MRData.DriverTable.Driver.FamilyName", String.class) ;
        System.out.println("allLastNames = " + allLastNames);

    }

    // getting attributes out of xml element
    @DisplayName("Test GET /drivers/{driver_id} get attributes ")
    @Test
    public void testDriverOneAttribute() {

        // send request to get information of driver_id alonso and save  xml result and assert or assert in the chain
        XmlPath xp = given()
                .pathParam("driver_id", "alonso").
                        when()
                .get("/drivers/{driver_id}")
                .xmlPath();

        String driverCode =  xp.getString("MRData.DriverTable.Driver.@code") ;
        System.out.println("driverCode = " + driverCode);
        String wikiURL  = xp.getString("MRData.DriverTable.Driver.@url") ;
        System.out.println("wikiURL = " + wikiURL);



    }









    @AfterAll
    public static void cleanup(){
        reset();
    }


}
