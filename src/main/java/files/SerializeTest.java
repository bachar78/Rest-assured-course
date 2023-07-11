package files;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.serialization.AddPlace;
import pojo.serialization.Location;
import pojo.serialization.Types;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SerializeTest {
    public static void main(String[] args) {
        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        Types types = new Types(myList);
        Location location = new Location(-38.383494,33.427362);
        AddPlace p = new AddPlace(50, "Frontline house", "(+91) 983 893 3937", "29, side layout, cohen 09", "https://rahulshettyacademy.com", "French-IN",location, types);



        //Base URI
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //To get the static data from a file, to convert the content of the file into String => content to Byte -> Byte to String


        //Post Scenario
        Response response = given().queryParam("key", "qaclick123")
                .body(p).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .extract().response();
        System.out.println(response.asString());
    }
}
