# NOTE Day 5 

##  Star War API Task 
```
    Interview Questions :
    Send request to  GET https://swapi.dev/api/people/
    Find out average height of all people showed up in the response
```
Starting with just the first page 
```java
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
```

Now If we want to expand this for entire 82 results with 9 pages, 

Here the steps : 
* Create an empty Integer empty list
* Send `GET /people` 
    * capture the total count using jsonPath
    * save first page heights into the list

* Loop : from page 2 till last page 
    * get the list of height integer using jsonPath 
    * add this to the big list

```java
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

```

# JUnit 5 Parameterized Test

Here is [the documentation link for Parameterized Test](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)

Few Options to Parameterize the test 
* `ValueSources` 
  
  Provide one data per iteration directly
    ```java
    @ParameterizedTest
    @ValueSource( ints = {1, 2,4,6,7,10} )
    public void testPrintMultipleIteration(  int num   ){
    }
    
  // bytes ,shorts, ints , longs , floats , doubles , strings ..
    ```

* `@CsvSource`
    ```csv
    1, 3 , 4 
    2, 3 , 5 
    8, 7 , 15 
    ```
  You can use this as Test data to provide more than one value per iteration of test 
  ```java
  @ParameterizedTest
  @CsvSource({"1, 3 , 4",
              "2, 3 , 5",
              "8, 7 , 15",
              "10, 9 , 19"
              })
  public void testAddition(int num1 , int num2 , int expectedResult ){
    // this will run for 4 iteration
    // num1 = 1 , num2 = 3 , expectedResult = 4
    // num1 = 2 , num2 = 3 , expectedResult = 5
    // num1 = 8 , num2 = 7 , expectedResult = 15
    // num1 = 10 , num2 = 9 , expectedResult = 19
  }
  ```
  [Full Example Here](CsvSourceParameterizedTest.java)


* `@CSVFileSource`

  You can also use external csv file to data drive the test. 
  
  You can place the csv file under [resource folder](../../resources) for easy access. 
  
  In our example we added a file with name [state_city_zipCount.csv](../../resources/state_city_zipCount.csv) under resource folder. 
  It has below content 
  ```
  state,city,zip_count
  NY, New York,166
  CO, Denver,76
  VA, Fairfax,10
  VA, Arlington,33
  MA, Boston,56
  VA,McLean,3
  MD, Annapolis,9
  ```

  Now we can point to that file in the Parameterized Test as below 

  ```java
  // if the file was under resources folder, you can use resources = "path here"
  // if the first line is header numLinesToSkip = 1 will start from second line
  @ParameterizedTest
  @CsvFileSource(resources = "/state_city_zipCount.csv" , numLinesToSkip = 1 )
  public void testStateCityToZipEndpointWithCSVFile(String stateArg, String cityArg , int zipArg  ){
  
  
  }
  ```
  Here is the [full example](CSVFileSourceParameterizedTest.java)

* `MethodSource`
    Allows you to refer to one or more factory methods of the test class or external classes.
  ```java
   // This test is expected to get the data from the returned value 
   // of a method called getManyNames 
   // JUnit5 expect this method to return Stream<SomeType>
   // We were able to provide a method that return List<SomeType>
   // and it was automatically converted and used as data source 
    @ParameterizedTest
    @MethodSource("getManyNames")
    public void testPrintNames(String name){
  
        System.out.println("name = " + name);
        assertTrue( name.length()>=4 ) ;
  
    }
  
  // create a static method to return many names
    public static List<String> getManyNames(){
    // YOU CAN DO WHATEVER YOU WANT HERE .........
      List<String> nameList = Arrays.asList("Emre","Mustafa","Diana","Shirin","Tucky","Don Corleone","Erjon","Muhammad","Saya","Afrooz") ;
      return nameList ;
  
    }
    
  //// This is how it should be 
  //    // create a static method to return many names
  //    public static Stream<String> getManyNames(){
  //
  //        List<String> nameList = Arrays.asList("Emre","Mustafa","Diana","Shirin","Tucky","Don Corleone","Erjon","Muhammad","Saya","Afrooz") ;
  //        return nameList.stream() ;
  //
  //    }
    
    ```
  Here is the [full example](MethodSourceForParameterizedTest.java)


