# Note Day 3

# Extracting response data using JsonPath 

To make it clear what we refer when we talk about the word json path , let's see few places this word comes up :
 Few meaning of JsonPath :
1. just like xpath -- it is used to provide location of certain data
2. `JsonPath` as a class coming from RestAssured to provide reusable methods to extract data
3. `jsonPath()` method of Response object to get JsonPath object

It's easy to get `JsonPath` object from response using `jsonPath()` method. 

```java
// any valid request to get response will work,
// for simplicity here we are just using get
Response response = get("/spartans/160") ; 
JsonPath jp = response.jsonPath();
// or in one shot 
// JsonPath jp = get("/spartans/160").jsonPath();

  int myId       = jp.getInt("id") ; //160
  String myName  = jp.getString("name") ; //B21 user
  String myGender= jp.getString("gender") ; //Male
  long mylong    = jp.getLong("phone") ; //9172288772
// json path to get the whole json object is empty String since it's at root level 
  Map<String, Object> resultMap = jp.getMap("");
  //resultMap  :  {id=160, name=B21 user, gender=Male, phone=9172288772}

```
Here is the simple json response for this request without any nested json 
```json
{
    "id": 160,
    "name": "B21 user",
    "gender": "Male",
    "phone": 9172288772
}
```

Data in Json array can be retrieved using index , JsonPath object also have support for saving the field values into the list 

for example : 
```java
JsonPath jp = get("/spartans").jsonPath() ;

int firstID = jp.getInt("[0].id")
// or  jp.getInt("id[0]")
// getting second object name 
String secondName  = jp.getString("name[1]")

List<Integer> allIds = jp.getList("id" , Integer.class) ;
List<String> allNames = jp.getList("name" , String.class) ;
List<Long> allPhones = jp.getList("phone" , Long.class) ;

```
Result 
```json 
[
    {
        "id": 33,
        "name": "Wilek",
        "gender": "Male",
        "phone": 2877865902
    },
    {
        "id": 34,
        "name": "Tucky",
        "gender": "Male",
        "phone": 2935099804
    },
    {
        "id": 35,
        "name": "Gardiner",
        "gender": "Male",
        "phone": 3751113352
    }
]
```
More detailed examples can be found [here](SpartanJsonPath_Test.java)



# POST, PUT , Patch Request in RestAssured 
There are few ways to provide request body in RestAssured 

You can provide as String , File , Java Object. 
* String as Body 
```java
  String postStrBody = "{\n" +
          "                \"name\" : \"Abigale\",\n" +
          "                \"gender\" : \"Female\",\n" +
          "                \"phone\" : 1800233232\n" +
          "            }" ;

        given()
                .contentType( ContentType.JSON)
                .body(postStrBody).
        when()
                .post("/spartans").
        then()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)// this is asserting response header is json
                .body("success" , is("A Spartan is Born!")  )
                .body("data.name", is("Abigale") )
```
* File as Body 

        // singleSpartan.json with below content
```json
  {
      "name" : "Abigale",
      "gender" : "Female",
      "phone" : 1800233232
  }
```
You can send body as below
```java
File jsonFile = new File("singleSpartan.json");
given()
        .contentType( ContentType.JSON) 
        .body(jsonFile).
when()
        .post("/spartans").
then()
        .statusCode( is(201) )
        .contentType(ContentType.JSON)// this is asserting response header is json
        .body("success" , is("A Spartan is Born!")  )
        .body("data.name", is("Abigale") )
```
* As Map object or POJO 
  
  You need to tell RestAssured what kind of serializer you want it to use , polular option Jackson , GSON .. 
```xml
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.12.2</version>
    </dependency>
```

```java 
Map<String,Object> bodyMap = new LinkedHashMap<>();
bodyMap.put("name","Abigale");
bodyMap.put("gender","Female");
bodyMap.put("phone",1800233232L);


System.out.println("bodyMap = " + bodyMap);
// We are expecting this Java Map object to be converted into Json String and send as body
// initially it failed , RestAssured can no find any serializer to convert java object to json
// We added Jackson-data-bind dependency in pom , so RestAssured can used it to make it conversion happen
given()
        .log().all()
        .contentType(ContentType.JSON)
        .body(   bodyMap   ).
when()
        .post("/spartans").
then()
        .log().all()
        .statusCode(201)

```
* POJO Class 
  
This class is meant to be blueprint for Creating Spartan pojo to represent json data with 3 fields name, gender, phone

**`POJO`** : **Plain old java object , used to represent data**
 * Required part of POJO
   * Encapsulated fields (private fields public getters and setters )
   * No Arg Constructor
 * OPTIONALLY
   * we will add all arg constructor for creating object in one shot
   * toString method to view the printed result
```java 
package pojo;

public class Spartan {

    private String name;
    private String gender;
    private long phone ;

    public Spartan(){

    }

    public Spartan(String name, String gender, long phone) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }
}

```

Now we can use it as body in `POST` or `PUT` requests 

```java 
Spartan sp = new Spartan("Abigale","Female",1800233232L) ;
// turn into below
/*
  {
    "name": "Abigale",
    "gender": "Female",
    "phone": 1800233232
}
  */
System.out.println("sp = " + sp);

given()
        .log().all()
        .contentType(ContentType.JSON)
        .body(   sp   ).
when()
        .post("/spartans").
        then()
        .log().all()
        .statusCode(201)
;
```

Same way can be used for anywhere you can provide body like `PUT` and `Patch` Request

You can find example [here](SpartanUpdatingData_Test.java)


## Negative Tests for `POST` Requests
Complete these tests according to the [examples given](SpartanPost_NegativeTest.java)


## Homework
Practice jsonPath as much as you can , using all those requests we have sent in Postman

For example :

`GET http://www.omdbapi.com/?t=Superman&apiKey=YOUR KEY GOES HERE`

Save and print below information from the response using JsonPath

* Title , Year , imdbRating  in correct data type
* Get second Ratings source
* Get first Ratings value


`GET http://www.omdbapi.com/?s=Flash&type=series&apiKey=YOUR KEY GOES HERE`

Save and print
* Third json object fields : `Title` , `Year`, `imdbID`
  save and print all the json array `imdbID` in to `List<String>`

* print totalResult field value

>The request is designed to only give you 10 results per page. 

**Optionally** :

send more request if the result is more than 10.

Eventually save all movie titles from all the pages into List<String>

