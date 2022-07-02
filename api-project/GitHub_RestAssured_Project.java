package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHub_RestAssured_Project {
    RequestSpecification reqSpec;

    String sshKey;
    int sshKeyID;

    @BeforeClass
    public void setup(){
        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();
        sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQDRd/VYlL3EkQSTONSxoVKr8rZsI/iL0po0bEm0bYfMokWrBRyea3ppCx5pVU0kzNdPpfS+HxwJwNWi0ejfX1gZ6gaSFEKqpjNMGhlFviqgYRK9NqiijH5mZHdHD+AqJ2/wIRMKCkYOyxW2kQiVFVdMr9gVJwBEbtYATVKIvsqq+w==";

    }

    @Test(priority = 1)
    public void addKeys(){
        String reqBody = "{\"title\": \"TestKey\", \"key\": \"" +sshKey+"\"}";
        Response response = given().spec(reqSpec)
                .body(reqBody)
                .when().post("/user/keys");

        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        sshKeyID = response.then().extract().path("id");

        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getKeys(){
        Response response = given().spec(reqSpec)
                .when().get("/user/keys");

        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);

        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteKeys(){
        Response response = given().spec(reqSpec)
                .pathParam("keyId", sshKeyID)
                .when().delete("/user/keys/{keyId}");

        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);

        response.then().statusCode(204);
    }

}
