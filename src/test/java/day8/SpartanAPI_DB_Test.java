package day8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.SpartanPOJO;
import test_util.DB_Utility;
import test_util.SpartanNoAuthBaseTest;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SpartanAPI_DB_Test extends SpartanNoAuthBaseTest {


    @Test
    public void testDB_Connection(){
//        // this will create HR connection according to your properties file
////        DB_Utility.createConnection();
//        String url = ConfigurationReader.getProperty("spartan.database.url");
//        String username = ConfigurationReader.getProperty("spartan.database.username");
//        String password = ConfigurationReader.getProperty("spartan.database.password");
//        DB_Utility.createConnection(url,username,password);
         DB_Utility.runQuery("SELECT * FROM SPARTANS") ;
         DB_Utility.displayAllData();

//        DB_Utility.destroy();
    }

    @DisplayName("Test GET /spartans/{id} match DB Data")
    @Test
    public void testGetSingleSpartanResponseMatchDB_Result(){

        // expected data -- DB query result
        // actual   data -- api response json

        // we want to test spartan with ID of 8003 , api response json match database data
        // RUN QUERY  SELECT * FROM SPARTANS WHERE SPARTAN_ID = 8003
        int spartanIdToCheck = 8003 ;

        DB_Utility.runQuery("SELECT * FROM SPARTANS WHERE SPARTAN_ID = " + spartanIdToCheck ) ;
        DB_Utility.displayAllData();

        Map<String,String> firstRowMap = DB_Utility.getRowMap(1) ;
        System.out.println("expected api response data we will use  = \n" + firstRowMap);

        // send api request
        given()
                .pathParam("id" , spartanIdToCheck)
                .log().uri().
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .statusCode(200)
                .body("id" , is(spartanIdToCheck)  )
                .body("name" ,  is( firstRowMap.get("NAME") )    )
                .body("gender" , is (firstRowMap.get("GENDER") ) )
                // phone jsonpath here is returning int if number fall into the range of int and long if the number fall into range of long
                // in order to make sure the type match , you need to make sure both side is long or int
                // easy way to make sure this phone jsonPath always return long is
                // using groovy method called toLong() to get long at all times
                .body("phone.toLong()" , is ( Long.parseLong(firstRowMap.get("PHONE") )   ) )

        ;

    }


    @DisplayName("Test GET /spartans/{id} match DB Data with POJO")
    @Test
    public void testGetSingleSpartanResponsePOJO_MatchDB_Result(){

        int spartanIdToCheck = 8005 ;

        DB_Utility.runQuery("SELECT * FROM SPARTANS WHERE SPARTAN_ID = " + spartanIdToCheck ) ;
        Map<String,String> expectedResultMap = DB_Utility.getRowMap(1) ;

        SpartanPOJO actualResultInPojo =  given()
                                            .pathParam("id" , spartanIdToCheck).
                                          when()
                                            .get("/spartans/{id}")
                                            .as(SpartanPOJO.class) ;
        System.out.println("actualResultInPojo = " + actualResultInPojo);

        assertThat( actualResultInPojo.getId() , is (spartanIdToCheck) ) ;
        assertThat( actualResultInPojo.getName() , is ( expectedResultMap.get("NAME") )    );
        assertThat( actualResultInPojo.getGender() , is ( expectedResultMap.get("GENDER") )    );
        assertThat(actualResultInPojo.getPhone() ,  is(  Long.parseLong( expectedResultMap.get("PHONE"))   )   );



    }

    // WHAT IF I WANT TO GET THE ID DYNAMICALLY INSTEAD OF HARCODING 8003
    @DisplayName("Test GET /spartans/{id} match DB Data with POJO 2")
    @Test
    public void testGetSingleSpartanResponsePOJO_MatchDB_Result2(){

        DB_Utility.runQuery("SELECT * FROM SPARTANS") ;
        Map<String,String> expectedResultMap = DB_Utility.getRowMap(1) ;
        // get the ID from this map and save it into below variable
        int spartanIdToCheck =  Integer.parseInt(  expectedResultMap.get("SPARTAN_ID")        )  ;

        SpartanPOJO actualResultInPojo =  given()
                .pathParam("id" , spartanIdToCheck).
                        when()
                .get("/spartans/{id}")
                .as(SpartanPOJO.class) ;
        System.out.println("actualResultInPojo = " + actualResultInPojo);

        assertThat( actualResultInPojo.getId() , is (spartanIdToCheck) ) ;
        assertThat( actualResultInPojo.getName() , is ( expectedResultMap.get("NAME") )    );
        assertThat( actualResultInPojo.getGender() , is ( expectedResultMap.get("GENDER") )    );
        assertThat(actualResultInPojo.getPhone() ,  is(  Long.parseLong( expectedResultMap.get("PHONE"))   )   );



    }
    // AS HOMEWORK  GET /spartans/search
    // search for nameContains a and Female
    // compare DB Result total count with API Result total count
    // SELECT * FROM SPARTANS WHERE LOWER(NAME) LIKE '%a%' and GENDER = 'Female'

    // Make sure all your data in json array match exact criteria above (nameContains a and Female
    // Optionally , Write a parametrized Test with multiple different search criteria



}
