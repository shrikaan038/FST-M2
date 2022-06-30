package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3 {
    RequestSpecification reqSpec;
    ResponseSpecification resSpec;

    @BeforeClass
    public void setUp(){
        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .build();

        resSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status", equalTo("alive"))
                .build();
    }

    @DataProvider
    public Object[][] petDataProvider(){
        Object[][] testData = new Object[][]{
                { 77234, "Riley", "alive" },
                { 77235, "Hansel", "alive" }
        };
        return testData;
    }

    @Test(priority = 1)
    public void addPets() throws IOException {
        String reqBody = "{\"id\": 77234, \"name\": \"Riley\", \"status\": \"alive\"}";

        Response response = given().spec(reqSpec)
                .body(reqBody)
                .when().post();

        reqBody = "{\"id\": 77235, \"name\": \"Hansel\", \"status\": \"alive\"}";

        response = given().spec(reqSpec)
                .body(reqBody)
                .when().post();

        response.then().spec(resSpec);
    }

    @Test(dataProvider = "petDataProvider", priority = 2)
    public void getPetInfo(int id, String name, String status){
        Response response = given().spec(reqSpec)
                .pathParam("petId", id)
                .when().get("/{petId}");

        System.out.println(response.asPrettyString());

        response.then().
        spec(resSpec).body("name", equalTo(name));
    }

    @Test(dataProvider = "petDataProvider", priority = 3)
    public void deletePets(int id, String name, String status){
        Response response = given().spec(reqSpec)
                .pathParam("petId", id)
                .when().delete("/{petId}");

        response.then().body("code", equalTo(200));
    }

}
