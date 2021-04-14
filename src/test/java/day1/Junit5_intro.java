package day1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// setting display name of the test class in test result using @DisplayName
@DisplayName("Day 1 of JUnit5 Test")
public class Junit5_intro {

    @DisplayName("Testing numbers")
    @Test
    public void test(){
        System.out.println("Learning JUnit5");

        assertEquals(1,1) ;
        assertEquals(1,2,"Something is wrong !!");

    }

    // add one more test ,
    // to assert your name first character start with letter A
    @DisplayName("Testing name start with A")
    @Test
    public void testName(){
        String name = "Adnan";
        assertEquals('A' , name.charAt(0) );
//        assertTrue(name.startsWith("A"));
    }
}
//Test Lifecycle annotations
// @BeforeAll @AfterAll @BeforeEach @AfterEach