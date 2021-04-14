package day5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodSourceForParameterizedTest {


    @ParameterizedTest
    @MethodSource("getManyNames")
    public void testPrintNames(String name){

        System.out.println("name = " + name);
        assertTrue( name.length()>=4 ) ;

    }

//    // create a static method to return many names
//    public static Stream<String> getManyNames(){
//
//        List<String> nameList = Arrays.asList("Emre","Mustafa","Diana","Shirin","Tucky","Don Corleone","Erjon","Muhammad","Saya","Afrooz") ;
//        return nameList.stream() ;
//
//    }

    // create a static method to return many names
    public static List<String> getManyNames(){
        // YOU CAN DO WHATEVER YOU WANT HERE .........
        List<String> nameList = Arrays.asList("Emre","Mustafa","Diana","Shirin","Tucky","Don Corleone","Erjon","Muhammad","Saya","Afrooz") ;
        return nameList ;

    }

}
