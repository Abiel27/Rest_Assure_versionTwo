# Day 8 Note 

# Deserialization Practice 

[Formula One API](http://ergast.com/mrd/) 

* Send `GET http://ergast.com/api/f1/drivers.json`
* Save the Driver information in [Driver POJO](../pojo/Driver.java)

> According to the documentation in order to get json result , 
> we need to add `.json` at the end of the request url.

> By default, it will return xml result.  
```java
JsonPath jp = get("/drivers.json").jsonPath() ;
Driver d1 = jp.getObject("MRData.DriverTable.Drivers[0]", Driver.class) ;
System.out.println("d1 = " + d1);

// Get all drivers as List<Driver>
List<Driver> allDriver = jp.getList("MRData.DriverTable.Drivers" , Driver.class) ;
System.out.println("allDriver = " + allDriver);

// Print the name of all American drivers in this list
for (Driver driver : allDriver) {
    if(driver.getNationality().equals("American")){
        System.out.println("driver.getGivenName() = " + driver.getGivenName());
    }
}

```

## Nested Json Deserialization

Given an API endpoint that return below response 
```json
    {
        "breed": "Yorkie",
        "color": "Gold",
        "age": 3,
        "owner": {
            "ownerName": "Inci",
            "address": "123 main street"
        }
    }
```
Represent the result json in pojo , and print them out

* Here is the [Dog POJO](../pojo/Dog.java) 
```java
@Getter @Setter
@ToString
    public class Dog{
    private String breed ;
    private String color ;
    private int age;
    private Owner owner ;
}
````
* Here is the [Owner POJO](../pojo/Owner.java)

Jackson library will automatically convert the type accordingly. 

We created a Postman Mock Server to return above result. 

Here is the [full doc for setting up mock server in Postman](https://learning.postman.com/docs/designing-and-developing-your-api/mocking-data/setting-up-mock/)




# API DB Validation 

Compare the API Response Data against the Database data we retrieved from SQL Query. 

Few Steps that we took to set up DB Configuration 

* Added DB_Utility class we created to easily work with SQL Query result 
* Added configurations.properties to store db properties 
* Added ConfigurationReader class to read above properties file 
* Tested our connection with simple query 
* Moved the creation connection and close connection part to base test @BeforeAll @AfterAll section 
* In actual test for example 
  `GET/spartans/{id}` ,
  * expected data can be retried by running query 
    ```SQL
    SELECT * FROM SPARTANS WHERE SPARTAN_ID = Id Goes Here
    ```
    We use DB Utility method `getRowMap` to save the result into `Map<String,String>` 
  * Send the actual request to the endpoint `GET/spartans/{id}` and compare the body with the data we got from db query. 
 * Repeated same process for HR ORDS API and Library App API. 

- Here is the example we did for [Spartans API](SpartanAPI_DB_Test.java)
- Here is the example we did for [HR ORDS API](HR_ORDS_API_DB_Test.java)
- Here is the example we did for [Library App API](Library_API_DB_Test.java)


# RestAssured method chain style 

Common style of method chaining in newer versions
```
given().
   all request related info goes here 
when() 
   send request here 
then() 
   assert here , it will fail as fast as first assertion fails 
```

Older style with expect , which evaluate all assertions 

```
given(). 
  all request related info goes here 
expect().
  all assertions expectation goes here
when(). 
  send request here 
```
This will not immediately fail until all assertions has been evaluated. 


JUnit 5 Also has concept of soft assertion using `assertAll`   
Here is the [full doc](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions)










