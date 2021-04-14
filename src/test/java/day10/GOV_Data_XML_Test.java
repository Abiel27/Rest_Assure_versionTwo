package day10;

import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GOV_Data_XML_Test {

    //GET https://data.ct.gov/api/views/qm34-pq7e/rows.xml
    // save all the numbers in "unknown" element in the response into the list
    // save all the year into the list
    // find out the max number and year of that max number

    @DisplayName("Find out the year max number of unknown race hired")
    @Test
    public void testMax(){


        XmlPath xp = given()
                        .baseUri("https://data.ct.gov")
                        .basePath("api/views/qm34-pq7e").
                    when()
                        .get("rows.xml").xmlPath() ;

        List<Integer> unknownList = xp.getList("response.row.row.unknown", Integer.class) ;
        System.out.println("unknownList = " + unknownList);

        List<Integer> yearList = xp.getList("response.row.row.year", Integer.class) ;
        System.out.println("yearList = " + yearList);

        int maxUnknown = Collections.max(unknownList) ;
        System.out.println("maxUnknown = " + maxUnknown);

        // find index of that max number in the list so we can use that index in year list
        int indexOfMaxUnknown = unknownList.indexOf(  maxUnknown  )  ;

        System.out.println("Year that most unknown race hired " + yearList.get(indexOfMaxUnknown));



    }



}
