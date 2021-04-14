package day5;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class StarWarAPI_Test {

    /*
    Interview Questions :
    Send request to  GET https://swapi.dev/api/people/
    Find out average height of all people showed up in the response
     */
    @BeforeAll
    public static void init(){
        baseURI = "https://swapi.dev";
        basePath = "/api" ;
    }

    @DisplayName("GET average height from GET /people response")
    @Test
    public void testGetAverageHeight(){

        List<Integer> allHeights =  get("/people")
                                    .jsonPath()
                                    .getList("results.height", Integer.class)
                                    ;
        System.out.println("allHeights = " + allHeights);
        // from here it's all java
        int total = 0;
        for (Integer height : allHeights) {
            total+=height;
        }
        int average = total/(allHeights.size());
        System.out.println("average = " + average);


    }
    // Above code will only retrieve first page that include 10 people
    // but we have more than 10 people in star war
    // we can get total count of people in first response count field
    // the decide how many page we have to go through by sending more request
    // then loop through the rest of the pages to add all heights to the list
    // and calculate the average from final list
    // in order to go to next page we can use
    // page query parameter to decide which page we want to see

    // HERE IS THE STEPS :
    // Create an empty Integer empty list
    //  Send GET /people -->>
        // capture the total count using jsonPath
        // save first page heights into the list

    //  Loop : from page 2 till last page
        // get the list of height integer using jsonPath
        // add this to the big list
    @DisplayName("Get all heights from all the pages and find average")
    @Test
    public void testGetAllPagesAverageHeight(){

        List<Integer> allHeights = new ArrayList<>() ;

        // send initial request , find total count and decide how many pages exists
        JsonPath jp =  get("/people").jsonPath() ;
        int peopleCount =  jp.getInt("count") ;  //82
        // if there is remainder we want to add 1 , if there is not keep it as is
        int pageCount = (peopleCount % 10==0)  ? peopleCount / 10  :  (peopleCount / 10)+1 ;


        List<Integer> firstPageHeights = jp.getList("results.height") ;
        System.out.println("firstPageHeights = " + firstPageHeights);
        allHeights.addAll(firstPageHeights ) ;


        // now it's time to loop and get the rest of the pages
        for (int pageNum = 2; pageNum <= pageCount ; pageNum++) {
            // GET /people?page = yourPageNumberGoesHere

            List<Integer> heightsOnThisPage =   get("/people?page="+pageNum )
                                                    .jsonPath()
                                                    .getList("results.height");
            allHeights.addAll( heightsOnThisPage ) ;
            // just in case you wanted to see each pages height
            System.out.println("heightsOnThisPage = " + heightsOnThisPage);
        }
        // third page has a value unknown and it's somehow got stored since getList get all all
        // jsonPath is backed by groovy language and it's allowing such value here so we will remove it
        allHeights.remove("unknown") ;
        System.out.println("allHeights = " + allHeights);
        System.out.println("allHeights.size() = " + allHeights.size());

    }



    @AfterAll
    public static void cleanup(){
        reset();
    }




}
