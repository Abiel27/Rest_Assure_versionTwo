package day1;

import org.junit.jupiter.api.*;
//Test Lifecycle annotations
// @BeforeAll @AfterAll @BeforeEach @AfterEach


@DisplayName("Learning Test Lifecycle Annotations")
public class TestLifecycleAnnotations {

    @BeforeAll
    public static void init(){
        System.out.println("Before all is running");
    }
    @AfterAll
    public static void close(){
        System.out.println("After all is running");
    }

    @BeforeEach
    public void initEach(){
        System.out.println("Before Each is running");
    }

    @AfterEach
    public void tearDownEach(){
        System.out.println("After Each is running");
    }


    @Test
    public void test1(){
        System.out.println("Test 1 is running");
    }

    @Disabled
    @Test
    public void test2(){
        System.out.println("Test 2 is running");
    }

    // ignoring certain test using @Disabled annotation


}
/*
This is the output of above Test class
    Before all is running
    Before Each is running
    Test 1 is running
    After Each is running
    Before Each is running
    Test 2 is running
    After Each is running
    After all is running
 */
