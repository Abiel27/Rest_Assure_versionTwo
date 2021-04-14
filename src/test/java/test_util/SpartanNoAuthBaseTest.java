package test_util;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class SpartanNoAuthBaseTest {

    @BeforeAll
    public static void init(){
        // as a homework , put these information
        // in configurations.properties file
        // this will set the part of URL at RestAssured
        RestAssured.baseURI     = "http://18.235.32.166:8000"  ;
//        RestAssured.port = 8000 ;
        RestAssured.basePath    = "/api" ;

        String url = ConfigurationReader.getProperty("spartan.database.url");
        String username = ConfigurationReader.getProperty("spartan.database.username");
        String password = ConfigurationReader.getProperty("spartan.database.password");
        DB_Utility.createConnection(url,username,password);

    }

    @AfterAll
    public static void cleanup(){

        RestAssured.reset();
        DB_Utility.destroy();
    }


}
