package day10;

import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

/*
SEND request to GET http://www.omdbapi.com/?t=Superman&r=xml&apikey=YOUR KEY GOES HERE
get the movie attribute out from the xml response
Above is for getting one movie information
Now Send Separate request to http://www.omdbapi.com/?s=Superman&type=series&r=xml&apikey=YOUR KEY GOES HERE
and get all movie titles from this response into the list
 */
public class MovieAPI_XML_Test {


    @DisplayName("Get movie attributes in xml")
    @Test
    public void testAttributes(){

        XmlPath xp = given()
                        .baseUri("http://www.omdbapi.com")
                        .queryParam("apikey","YOUR OWN KEY HERE")
                        .queryParam("t","Superman")
                        .queryParam("r","xml").
                    when()
                        .get()
                        .xmlPath() ;
        String title = xp.getString("root.movie.@title") ;
        System.out.println("title = " + title);


    }
    @DisplayName("Get movies attributes in xml")
    @Test
    public void testAllMovieAttributes(){

        XmlPath xp = given()
                .baseUri("http://www.omdbapi.com")
                .queryParam("apikey","YOUR OWN KEY HERE")
                .queryParam("s","Superman")
                .queryParam("r","xml").
                        when()
                .get()
                .xmlPath() ;
        List<String> allTitles = xp.getList("root.result.@title", String.class);
        System.out.println("allTitles = " + allTitles);


    }

}
