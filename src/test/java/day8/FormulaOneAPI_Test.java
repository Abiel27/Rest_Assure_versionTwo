package day8;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Driver;

import java.util.List;

import static io.restassured.RestAssured.*;

public class FormulaOneAPI_Test {

    @BeforeAll
    public static void init(){

        baseURI = "http://ergast.com";
        basePath = "api/f1" ;

    }

    @DisplayName("GET /drivers.json and save result to pojo")
    @Test
    public void testDrivers(){

        // Get first Driver as Driver POJO
        JsonPath jp = get("/drivers.json").jsonPath() ;
        Driver d1 = jp.getObject("MRData.DriverTable.Drivers[0]", Driver.class) ;
        System.out.println("d1 = " + d1);

        // Get all drivers as List<Driver>
        List<Driver> allDriver = jp.getList("MRData.DriverTable.Drivers" , Driver.class) ;
        System.out.println("allDriver = " + allDriver);

        // Print the name of all American drivers in this list
        for (Driver driver : allDriver) {
            if(driver.getNationality().equals("American")){
                System.out.println("driver.getGivenName() = " + driver.getGivenName());
            }
        }




    }

    @AfterAll
    public static void cleanup(){
        reset();
    }




}
