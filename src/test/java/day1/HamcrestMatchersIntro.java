package day1;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersIntro {


    @Test
    public void simpleTest1(){

        // assert 10  equal to 5+5
        // this is the syntax for hamcrest way of assertion
        // assertThat( some actual value , some matcher that describe what you are trying to do)
        assertThat(5+5, is(10) ) ;
        assertThat(5+5, equalTo(10) );
        // All matchers has 2 overloaded version
        // first that accept actual value
        // second that accept another matchers
        // below examples is method is accepting another matchers equalTo to make it readable
        assertThat(5+5 , is( equalTo(10) ) );

        // negative assertion  5+5 is not 11
        assertThat(5+5, not(11) );
        assertThat(5+5, is( not(11) )   );
        assertThat(5+5, is(not(equalTo(11))));

        // number comparison
        //        greaterThan()
        assertThat(5+5, is( greaterThan(9))  );
        //        lessThan()
        //        greaterThanOrEqualTo()
        //        lessThanOrEqualTo()
    }


    @DisplayName("Matchers related to Strings ")
    @Test
    public void stringMatchers(){

        String msg = "B21 is learning Hamcrest" ;

        // checking for equality is same as numbers above
        assertThat(msg , is("B21 is learning Hamcrest") );
        assertThat(msg , equalTo("B21 is learning Hamcrest") );
        assertThat(msg , is(equalTo("B21 is learning Hamcrest")) );

        // check if this msg start with B21
        assertThat(msg, startsWith("B21"));
        // now do it in case insensitive manner
        assertThat(msg, startsWithIgnoringCase("b21") );
        // check if the msg end with rest
        assertThat(msg, endsWith("rest") );

        // check if msg contains String learning
        assertThat(msg, containsString("learning"));
        assertThat(msg, containsStringIgnoringCase("LEARNING"));

        String str = "   " ;
        // check if above str is blank
        assertThat(str, blankString() );
        // check if trimmed str is empty String
        assertThat(str.trim()  , emptyString()  );

    }

    @DisplayName("Hamcrest Support for Collection")
    @Test
    public void testCollection(){

        List<Integer> lst = Arrays.asList(1,4,7,3,7,44,88,99,44) ;

        // checking the side of this list
        assertThat(lst, hasSize(9) );
        // check if this list hasItem 7
        assertThat(lst, hasItem(7) );
        // check if this list hasItems 7,99,88
        assertThat(lst, hasItems(7,99,88) );

        // check if every item in this list is greaterThan 0
        assertThat(lst, everyItem( greaterThan(0) ) );



    }







}
