package files;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.serialization.AddPlace;
import pojo.serialization.Location;
import pojo.serialization.Types;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
    public static void main(String[] args) {
        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        Types types = new Types(myList);
        Location location = new Location(-38.383494,33.427362);
        AddPlace p = new AddPlace(50, "Frontline house", "(+91) 983 893 3937", "29, side layout, cohen 09", "https://rahulshettyacademy.com", "French-IN",location, types);



        RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        //To get the static data from a file, to convert the content of the file into String => content to Byte -> Byte to String

        //Post Scenario
        RequestSpecification resp = given().spec(reqSpec).body(p);
                Response response = resp.when().post("maps/api/place/add/json")
                .then().spec(resSpec)
                .extract().response();
        System.out.println(response.asString());
    }
}
