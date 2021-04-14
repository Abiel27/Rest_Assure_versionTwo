package day9;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AssertingAllInJunit5 {

    @Test
    public void testAdd(){
        // we want to be able to evaluate all assertion and return one summary as result
        // instead of immediately failing the test

//        assertThat(5+5 , equalTo(10)) ;
//        assertThat(5+4 , equalTo(10)) ;
//        assertThat(5+6 , equalTo(10)) ;

        assertAll(
                ()-> assertThat(5+5 , equalTo(10)) ,
                ()-> assertThat(5+4 , equalTo(10)) ,
                ()-> assertThat(5+6 , equalTo(10))
                );



    }



}
