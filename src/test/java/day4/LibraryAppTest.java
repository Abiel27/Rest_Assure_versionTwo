package day4;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Library App Simple Test")
public class LibraryAppTest {

    @BeforeAll
    public static void init(){

        baseURI  = "http://library1.cybertekschool.com" ;
        basePath = "/rest/v1" ;

    }

    @AfterAll
    public static void cleanup(){
        reset();
    }

    @DisplayName("test POST /login")
    @Test
    public void testLogin(){

        // librarian69@library  , KNPXrm3S
        given()
                .log().all()
                .contentType(ContentType.URLENC) //"application/x-www-form-urlencoded"
                .formParam("email" , "librarian69@library")
                .formParam("password" , "KNPXrm3S").
        when()
                .post("/login").
        then()
                .statusCode(200)
                .log().all()
                .body("token" , is(not( blankOrNullString()  ) )   )
                ;


    }


    @DisplayName("test the token")
    @Test
    public void testGetTokenDecodeToken(){

        // first send request to POST /login extract token
        // then send request to POST /decode to verify emails and other info
        String username = "librarian69@library" ;
        String password =  "KNPXrm3S";

        String myToken = given()
                                .log().all()
                                .contentType(ContentType.URLENC)
                                .formParam("email", username)
                                .formParam("password" , password).
                        when()
                                .post("/login").
                        then()
                                .log().all()
                                .statusCode(200).
                        extract()
                                .path("token") ;

        given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParam("token", myToken  ).
        when()
                .post("/decode").
        then()
                .log().all()
                .statusCode(200)
                .body("email" , is( username) )
                ;


    }


    @DisplayName("Test GET /dashboard_stats endpoint")
    @Test
    public void testDashboardNumbers(){

        String username = "librarian69@library" ;
        String password =  "KNPXrm3S";

        String myToken = given()
                            .contentType(ContentType.URLENC)
                            .formParam("email", username)
                            .formParam("password" , password).
                        when()
                            .post("/login")
                            .path("token");


        given()
                .header("x-library-token",myToken).
        when()
                .get("/dashboard_stats").
        then()
                .log().all()
                .statusCode(200)
                .body("book_count" , is("2107")  )
                .body("borrowed_books" , is("775")  )
                .body("users" , is("8665")  )
        ;
        // alternatively , extract JsonPath object after status code check
        // assert each numbers separately
        JsonPath jp =
                given()
                        .header("x-library-token",myToken).
                when()
                        .get("/dashboard_stats").
                then()
                        .statusCode(200).
                  extract()
                        .jsonPath() ;

        assertThat( jp.getInt("book_count") , is(2107) ) ;
        assertThat( jp.getInt("borrowed_books") , is(775) ) ;
        assertThat( jp.getInt("users") , is(8665) ) ;




    }




}














