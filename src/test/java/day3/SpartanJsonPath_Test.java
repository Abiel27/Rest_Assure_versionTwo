package day3;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.SpartanNoAuthBaseTest;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class SpartanJsonPath_Test extends SpartanNoAuthBaseTest{

    //http://YourKitchenIp:8000/api/spartans/160

    @Test
    public void testOne(){

        Response response =
                given()
                        .log().all()
                        .pathParam("id","160").
                when()
                        .get("/spartans/{id}")
                        .prettyPeek() ;

        // using path method to extract data
        int myId = response.path("id") ;
        System.out.println("myId = " + myId);

        // Few meaning of JsonPath :
            //1. just like xpath -- it is used to provide location of certain data
            //2. JsonPath as a class coming from RestAssured to provide reusable methods to extract data
            //3. jsonPath() method of Response object to get JsonPath object
        JsonPath jp = response.jsonPath() ;  // number 2 for variable type , number 3 for method

        myId =   jp.getInt("id") ;
        System.out.println(" the result is = " + myId);

        long phoneNum =  jp.getLong("phone") ;
        System.out.println("phoneNum = " + phoneNum);

        String myName = jp.getString("name") ;
        System.out.println("myName = " + myName);


        System.out.println("Save whole json object into map = "
                + jp.getMap(""));

        Map<String, Object> resultJsonInMap = jp.getMap("") ;
        System.out.println("resultJsonInMap = " + resultJsonInMap);


    }

    @DisplayName("Extract data from GET /spartans ")
    @Test
    public void testGetAllSpartans(){

//        Response response = get("/spartans");
//        JsonPath jp = response.jsonPath() ;

        JsonPath jp = get("/spartans").jsonPath() ;

        // print first id in the json array response
       // [{},{},{} ]
        System.out.println("jp.getInt(\"id[0]\") = "
                + jp.getInt("id[0]"));
        // print second json object name in the json array response
        System.out.println("jp.getString(\"name[1]\") = "
                + jp.getString("name[1]"));

        System.out.println("jp.getMap(\"[0]\") = "
                        + jp.getMap("[0]"));

        // print first id in the json array response
        System.out.println("jp.getInt(\"[0].id\") = "
                + jp.getInt("[0].id"));


    }

    @DisplayName("Extract data from GET /spartans/search ")
    @Test
    public void testGetSearchSpartans(){

        //http://18.235.32.166:8000/api/spartans/search
        // ?nameContains=Abigale&gender=Male

            JsonPath jp =
                given()
                        .queryParam("nameContains","Abigale")
                        .queryParam("gender","Male")
                        .log().all().
                when()
                        .get("/spartans/search")
                        .prettyPeek()
                        .jsonPath();
        // find out first guy id  , second guy name
        //  content[0].id           content[1].name
        System.out.println("jp.getInt(\" content[0].id\") = "
                + jp.getInt(" content[0].id"));

        System.out.println("jp.getString(\"content[1].name\") = "
                + jp.getString("content[1].name"));


        // store first jsonObject into a map
        Map<String, Object> firstJsonInMap =
                jp.getMap("content[0]") ;
        System.out.println("firstJsonInMap = " + firstJsonInMap);


    }


    @DisplayName("Saving json array fields into List")
    @Test
    public void testSavingJsonArrayFieldsIntoList(){

        JsonPath jp =
                given()
                        .queryParam("nameContains","Abigale")
                        .queryParam("gender","Male")
                        .log().all().
                        when()
                        .get("/spartans/search")
                        .prettyPeek()
                        .jsonPath();
        //  save all the ids into a list
        System.out.println("jp.getList(\"content.id\") = "
                        + jp.getList("content.id"));
        System.out.println("jp.getList(\"content.name\") = "
                        + jp.getList("content.name"));
        System.out.println("jp.getList(\"content.phone\") = "
                        + jp.getList("content.phone"));

        // getList method has 2 overloaded versions
        // 1. jp.getList("json path here") ; -->> the type of list will be automatically determine
        List<Integer>  allIds = jp.getList("content.id") ;
        // 2. jp.getList("json path here", class Type  you want this list to have)
        List<Integer> allIds2 = jp.getList("content.id" , Integer.class ) ;

        List<String> allNames  = jp.getList("content.name") ;
        List<String> allNames2 = jp.getList("content.name", String.class) ;

        List<Long> allPhones  = jp.getList("content.phone") ;
        List<Long> allPhones2 = jp.getList("content.phone", Long.class) ;




    }

    @DisplayName("Get List Practice for GET /spartans")
    @Test
    public void testGetListOutOfAllSpartans(){

        JsonPath jp = get("/spartans").jsonPath();
        // save the list into List object and assert the size

        List<Integer> allIds = jp.getList("id" , Integer.class) ;
        List<String> allNames = jp.getList("name" , String.class) ;
        List<Long> allPhones = jp.getList("phone" , Long.class) ;

        assertThat( allIds , hasSize(12220)  );
        assertThat( allNames , hasSize(12220)  );
        assertThat( allPhones , hasSize(12220)  );



    }

    // Practice jsonPath as much as you can , using all those requests we have sent in Postman
    /*
     * for example :
     * GET http://www.omdbapi.com/?t=Superman&apiKey=YOUR KEY GOES HERE
     * save and print below information from the response using JsonPath
     *
     *  Title , Year , imdbRating  in correct data type
     *  Get second Ratings source
     *  Get first Ratings value
     *
     *
     * GET http://www.omdbapi.com/?s=Flash&type=series&apiKey=YOUR KEY GOES HERE
     *
     *  save and print 3rd json object fields : Title , Year , imdbID
     *  save and print all of the json array imdbID in to List<String>
     *
     *  print totalResult field value
     *  the request is designed to only give you 10 results per page
     *  Optionally :
     *      send more request if the result is more than 10
     *
     *  eventually save all movie titles from all the pages into List<String>
        from each and every pages
     *

     */





}
