package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {
    static String BASE_URI = "https://petstore.swagger.io/v2/user";

    @Test(priority = 1)
    public void addUserFromFile() throws IOException {
        FileInputStream inputJson = new FileInputStream("src/test/java/activities/userinfo.json");

        String reqBody = new String(inputJson.readAllBytes());

        Response response = given().contentType(ContentType.JSON)
                .body(reqBody)
                .when().post(BASE_URI);


        inputJson.close();

        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("9231"));
    }

    @Test(priority = 2)
    public void getUserInfo(){
        File outputJson = new File("src/test/java/activities/userGetResponse.json");
        Response response = given().contentType(ContentType.JSON)
                .pathParam("username", "shrijosh")
                .when().get(BASE_URI + "/{username}");

        String resBody = response.getBody().asPrettyString();

        try{
            outputJson.createNewFile();
            FileWriter writer = new FileWriter(outputJson.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.then().body("id", equalTo(9231));
        response.then().body("username", equalTo("shrijosh"));
        response.then().body("firstName", equalTo("Justin"));
        response.then().body("lastName", equalTo("Case"));
        response.then().body("email", equalTo("justincase@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));
    }

    @Test(priority = 3)
    public void deleteUser(){
        Response response = given().contentType(ContentType.JSON)
                .pathParam("username", "shrijosh")
                .when().delete(BASE_URI + "/{username}");

        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("shrijosh"));
    }

}
