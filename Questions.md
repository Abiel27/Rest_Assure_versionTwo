## What is the acronym of API?

Application Programming Interface

## How did you do API testing? What was your purpose ( in my last project)

I did API testing for a internal project employee info
Old applocation exposed the restful api for easy integration with other apps
so you tested the app functionality works in api layer

I have experience both in testing and automating in Postman
and automating using RestAssured Library


## What kind of requests do you send while testing the API?
I have worked on all kind of requests for CRUD Operations :
* `POST`
* `GET`
* `PUT`
* `PATCH`
* `DELETE`
* `HEAD`



## What `OPTIONS` Http method is for ?
It's used for secifying what kind of actions are available
for certian request URL (if the API provide such options)


## WHAT `HEAD` HTTP METHOD and Why did you use it?
It's the HTTP method that only return response header without the body

## Different between PUT and PATCH
* `PUT` is used for complete update
* `PATCH` is mostly for partial update

## Different between POST and PUT
* `PUT` is used for complete update of existing data
* `POST` is used for adding new data to the server



## Give us an example, step by step, how you would automate a test case API side. What do you do?

First read the documentation(functional requirement) of the application
understand each endpoints including authentication authorization
and other relevant information like query params , headers , expected status codes and response body , response headers.
Test it out manually in Postman to get results for both positive negatives responses
Write test scenarios and assertions around those expected outcomes according to the doc
I can write both in Postman and RestAssured
Latest project I worked on RestAssured Maven project



## If you need api authentication, how do you attach it to your request? Types of Authentication you know in API?
multiple experience :
* basic authentication
* token based authentication
    * bearer token in Authorization header
        - Authorization : Bearer verylongtokengoeshere
    * api key in custom header
        - x-library-tone : somelongtokenhere
    * api key in query parameter
        * www.omdb.com/?apikey=somesceretKeyhere.....
    * oAuth2
        * checkout spotify doc to try out this flow
        * [This is the link](https://developer.spotify.com/documentation/general/guides/authorization-guide/)


## Give an example on an API test you recently wrote and how detailed you went with the test case?
In a recent `POST /employees` endpoint
It expect a json as payload and it has strict restiction on the field values
like name length , phone number , email verifications
Along with the positive scenario where I add correct json payload
and expect `201` status code with valid headers and response payload
I added negative scenarios for all kind of `400 BAD REQUEST` scenarios
* either name as invalid length
* phone or email has invalid format
* or multiple invalid inputs

I have added additional `GET /employees/{id}` requests to verify the data was actually added correctly
Same flow repeated for `PUT` and `PATCH` Requests


## How do you validate a JSON body?
I can do validation of json body both in Postman and RestAssured.
In postman , save the json response as JavaScript object and access the property of the object for varification.

In RestAssured , I use JsonPath to capture the value of the field to be verified and compare that with expected result in the test



## How detailed are you when you test on Postman?
* I organize my requests in Collection according to the functionality of the app
* The Collection is designed to go multiple scenarios by carrying data created in previous steps to make it stable, for example while testing `DELETE` requests , instead of relying on data that exists in the app, i created my own data with post and use as test data.
* Tested all negative scenarios like `403 forbidden` Response to make sure only those who have authority can make a authorized rerquests



## How would you validate only part of the body of a response?
Capture the value from the json and compare that to according to the expected result.
In some scenario , I also add additional validation for JsonSchema to make sure the json structure is as expected according to the requirement.


## How would you test the structure of your Json Response without having to verify the actual value of the field ?
I do JsonSchema Validation to verify the structure of the json response.
I have JsonSchema file that i got from developers to describe how the response json structure should look like
In RestAssured Project I have below dependency
```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-schema-validator</artifactId>
    <version>4.3.3</version>
    <scope>test</scope>
</dependency>
```
Here is the [RestAssured Doc for Schema Validation](https://github.com/rest-assured/rest-assured/wiki/Usage#json-schema-validation)

And here is one example in RestAssured to validate the response json against schema with name `products-schema.json`
```java
get("/products")
.then()
.assertThat()
    .body(matchesJsonSchemaInClasspath("products-schema.json"));
```



## Do you only do status code validations? What other parts of the API do you validate?
* Status code
* Headers
* body (xml or json)
* Structure of the body with schema
* Optionally Response time



## What kind of edge cases did you test for API validations? Give an example from your project.
One of the `POST` Request I am working on only accept 10-13 digit number and supposed to give `400 BAD REQUEST` response if it was less or more digit
I tested with the number less than 10 and more than 13, it worked fine when it's less than 10 and digit is until 14 digit , however whenever it goes over 15 , the response is `500`. which was accepted as valid defect.



## How can you convert from Json to Java Object and Java Object to Json?
I have POJO(Plain Old Java Object) class that represent the structure of json object
I use `Jackson Data-bind` library to do such conversion known as Serialization and De-Serialization

`POJO` Class is used to represent data , it does not have extra functinality other than reresenting data
It should have
* Encapsulated fields (private fields , public getters and setters)
* No Arg Constructor (REQUIRED)
```java
    public class Car{
        private String make;
        private String model;
        private int year;
        public Car(){

        }
        // getters and setters here
    }
```

## What are the collections you use to read the json file and store?
`Map` , `List<Map>` , `List<POJO>`




## How do you test business restrictions that are on the API?
Restriction can be multiple things ,like restriction on authorization and authentication or restriction on valid data while adding data orr updating data.
Use multiple different test scenarios to test postive negative scenariros according to the restrictions



## How do you rate yourself from 1 to 10 for postman or rest assured?
8-9 out of 10



## Describe where eles did you use API other than API Testing?
Some of my UI scenario heavily rely on the correct data in correct state to be able to go through application flow. I did not have access to database or getting data from database was too complex since the data was spread among too many different database tables, However there was api endpoint that return such data I am looking for easily.
So I made api call to get my test data and pass it to UI Scenario to drive my UI test.
In some other scenario I used to for generating test data easily and fast.



## Do you have experience integrating third-party applications with your application through API?
You can talk about your app pull data from 3rd party api



## API challenge example.
* Lack of documentation
* Documentation is not up to date or no way to understand how it works or who to ask.
* Slow response from the request











## How do you pass a JSON file with API?
If the `POST /someEndpoint` accept binary file as body or form data that include key and binary file as body
you can use postman request body `form-data` tab to add such file to send to server.
If RestAssured this can be done using it's support for multipart for data by passing the location of the file in filesystem
Here is the [RestAssured Doc for Multi Part form data](https://github.com/rest-assured/rest-assured/wiki/Usage#multi-part-form-data)





## Initially your API return some dummy data from local storage or in memory databse as API response, Now It's connected to real test database , How can you verify your API request Response is matching data that coming from Database ?

This is a perfect API-DB validation scenario.
Make a connection to database using `JDBC` for getting expected result of your api request
```SQL
SELECT * FROM SPARTANS WHERE SPARTAN_ID = 5 ;
```
Then make actual API Request to
`GET /api/spartans/5` Endpoint
```json
{
  "id": 5,
  "name": "Blythe",
  "gender": "Female",
  "phone": 3677539542
}
```
and compare the data from the response match the data you got from database.











## Common Status codes reference

- A number to indicate the status of your response , list of all status codes can be found [here](https://httpstatuses.com/)

- `2xx` for success
    - `200 OK`
    - `201 CREATED`
    - `204 No Content`
- `4xx` for client side error
    - `400 Bad Request`
        - Sending bad data to the server
    - `401 Unauthorized`
        - Did not provide correct credentials, we do not know who you are.
    - `403 Forbidden`
        - Do not have permission to take this action, we know who you are but you do not have perission.
    - `404 Not Found`
        - The resource you are looking for does not exist
    - `405 Method Not Allowed`
        - Can not perform this http method on the endpoint
        - For example :
            - `POST /api/spartans/15` is not allowd!!!
            - Most of the public api like `movieDB`, `Breaking Bad` ,`Star War` apis only support `GET`
    - `406 Not Acceptable`
        - Endpoint only support getting the response in certain format and we asked for a format that not acceptable
        - For example :
            - `/spartans/{id}` only support json and if we put `accept` header value to `xml` We get this status code.

    - `415 Unsupported`
        - Server is expecting to get the body in certain content type , but the client sent unsupported content type
        - For example : `POST /api/spartans` only accept `json` as content type. if you forget to add the content type, it will automatically assume you are sending `plain/text` and we get this error.
        - Or if you specify the content type incorrectly , this status code will be returned.
- `5xx` for server side error
    - `500 Internal server error`
        - The server encountered an unexpected condition that prevented it from fulfilling the request.
        - For example :
            - in `PUT /api/spartans/{id}` request, unlike the `POST `, backend code does not have any error handling for incorrect body, so server does not know what to do with it and throw `500` error.
    - `503 Service Unavailable`
        - The server is currently unable to handle the request due to a temporary overload or scheduled maintenance, which will likely be alleviated after some delay.

* Suppose you had a bearer token, you used it and got 403 what does it mean? What is the first step you would do when you get 403?
* What is 401?
* What is the difference between 401 and 403?
* Tell me about 200 codes
* What is the difference between 200 codes and 400 codes?
* What are 400 and 500 response codes?
* API status codes; 200? 204? 404?




## Suppose you run your API suite, everyday it was taking 10 minutes, but this time it took 30 minutes. What would you do? What might be the reason?
* Could be server overloaded
* Could be too much data and taking time to return
* General internet slowness issue from server side

## Have you worked with XML response ?
Not recently , Most of the API Endpoint I have worked on return Json response.
I can work with XML Response using `XmlPath` in RestAssured


