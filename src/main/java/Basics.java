import files.payload;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        // validate if Add Place API is working as expected
        //given - all input details
        // when - Submit the API -resource, http method
        //Then - validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().relaxedHTTPSValidation().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.AddPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)");

        // Add place -> Update Place with New Address -> Get Place to validate if New address is present in response
    }
}
