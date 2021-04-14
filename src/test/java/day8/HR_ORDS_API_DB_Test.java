package day8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Region;
import test_util.HR_ORDS_API_BaseTest;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static test_util.DB_Utility.*;

public class HR_ORDS_API_DB_Test extends HR_ORDS_API_BaseTest {

    // We added DB Connection and DB Destroy method into base test
    // We added the base uri of HR ORDS API into configuration file

    @Test
    public void testHR_ORDS_API(){

        runQuery("SELECT * FROM REGIONS WHERE REGION_ID = 1") ;
        displayAllData();
        // send request to GET /regions/{region_id} and compare the result with DB result

        // save expected data for region_id of 1 into List
        List<String> firstRowAsExpectedList = getRowDataAsList(1) ;
        System.out.println("firstRowAsExpectedList = " + firstRowAsExpectedList);
        // send API request to GET /regions/{region_id} with id of 1 , save the result into POJO
        Region r1 = given()
                        .pathParam("region_id",1).
                     when()
                         .get("/regions/{region_id}")
                        .jsonPath()
                        .getObject("items[0]", Region.class) ;
        System.out.println("r1 = " + r1);

        // compare the result
        assertThat( r1.getRegion_id() ,  is(   Integer.parseInt(  firstRowAsExpectedList.get(0)    )   )  );
        assertThat( r1.getRegion_name() , is(   firstRowAsExpectedList.get(1)  ) );


    }

    @DisplayName("Test GET /regions and compare with expected DB Result")
    @Test
    public void testAllRegionsWithDB(){

        runQuery("SELECT * FROM REGIONS") ;
        // Saving all rows into List of Map
        List<Map<String,String>> expectedRowMapList = getAllRowAsListOfMap() ;
        System.out.println("expectedRowMapList = " + expectedRowMapList);

        List<Region> allRegionsPojoLst = get("/regions")
                                        .jsonPath().getList("items", Region.class) ;
        System.out.println("allRegionsPojoLst = " + allRegionsPojoLst);

        assertThat( expectedRowMapList.size()  , equalTo(  allRegionsPojoLst.size()  )   );

        for (int i = 0; i < expectedRowMapList.size()  ; i++) {
            // compare each region id and region name match the expected region id and name
            assertThat( allRegionsPojoLst.get(i).getRegion_id() ,
                        is(  Integer.parseInt(expectedRowMapList.get(i).get("REGION_ID") ) )  ) ;

            assertThat( allRegionsPojoLst.get(i).getRegion_name() , is( expectedRowMapList.get(i).get("REGION_NAME")  )   );

        }


    }
    // HOMEWORK : RUN QUERY  runQuery("SELECT * FROM REGIONS") save result as List of Map
    // Write a method to return above List of Map called getAllRegionListOfMap
    // Write a parameterized Test for GET /regions/{region_id}
    // Use getAllRegionListOfMap method as Method Source for your Parameterized Test




}
