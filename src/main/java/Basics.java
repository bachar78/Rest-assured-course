import files.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        // validate if Add Place API is working as expected
        // Add place -> Update Place with New Address -> Get Place to validate if New address is present in response
        //given - all input details
        // when - Submit the API -resource, http method
        //Then - validate the response

        //Base URI
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Post Scenario
        String response = given().relaxedHTTPSValidation().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payload.AddPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        //extracting place id
        JsonPath js = reUsableMethods.rawToJson(response);// for parsing json
        String placeId = js.getString("place_id");

        String updateNewAddress = "70 Summer, Africa";
        //Put Scenario
        given().relaxedHTTPSValidation().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" +placeId + "\",\n" +
                        "\"address\":\"" + updateNewAddress +"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Scenario
        String getPlace = given().relaxedHTTPSValidation().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).body("address", equalTo(updateNewAddress)).extract().response().asString();
        JsonPath getPlaceJson = reUsableMethods.rawToJson(getPlace);
        String getUpdatedAddress = getPlaceJson.getString("address");
        //Junit and Testng
        Assert.assertEquals(updateNewAddress, getUpdatedAddress);


    }
}
