package day5;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MovieAPI_Practice {


    @DisplayName("Search movies JsonPath practice")
    @Test
    public void testSearch(){

        //GET http://www.omdbapi.com?s=Superman&type=series&Your_API_KEY_GOES_HERE

        // if you do not have other place you are using it ,
        // you directly provide your baseURI using baseURI method in given

        JsonPath jp =
            given()
                    .baseUri("http://www.omdbapi.com")
                    .log().all()
                    .queryParam("apikey" ,  "5b5d0fe8")
                    .queryParam("s" , "Superman")
                    .queryParam("type","series").
            when()
                    .get().
            then()
                    .log().all()
                    .statusCode(200)
                    // CONTINUE from here save Titles as list
                .extract()
                    .jsonPath()
        ;
        // json Path to get all movie title is  Search.Title
        List<String> allTitles = jp.getList("Search.Title", String.class) ;
        System.out.println("allTitles = " + allTitles);

        // assert the size of this list is 10
        assertThat(allTitles , hasSize(10) );

        // assert first item contains String Superman
        assertThat(allTitles.get(0)  , containsString("Superman") );

        // assert it has item "Superman and Lois"
        assertThat(allTitles ,  hasItem("Superman and Lois")  );

        // assert it has items "Superman and Lois" , "The New Adventures of Superman"
        assertThat(allTitles , hasItems( "Superman and Lois" , "The New Adventures of Superman"));

        // assert every items contains String Superman

        assertThat(allTitles , everyItem(  containsString("Superman")   )    );


    }

    @DisplayName("Search movies response body validation in the chain ")
    @Test
    public void testSearch2() {

        //GET http://www.omdbapi.com?s=Superman&type=series&Your_API_KEY_GOES_HERE

        // if you do not have other place you are using it ,
        // you directly provide your baseURI using baseURI method in given

                given()
                        .baseUri("http://www.omdbapi.com")
                        .log().all()
                        .queryParam("apikey", "5b5d0fe8")
                        .queryParam("s", "Superman")
                        .queryParam("type", "series").
                when()
                        .get().
                then()
                        .log().all()
                        .statusCode(200)
                        .body("Search.Title" ,  hasSize(10) )
                        .body("Search[0].Title" , containsString("Superman") )
                        .body("Search.Title" , hasItem("Superman and Lois") )
                        .body("Search.Title" , hasItems("Superman and Lois","The New Adventures of Superman" ) )
                        .body("Search.Title" ,everyItem(  containsString("Superman")   )  )


                ;


    }





}
