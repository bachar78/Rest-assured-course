import files.payload;
import files.reUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DynamicJson {
    //Add some scenarios
    @Test
    public static void addBook() {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(payload.addBook())
                .when()
                .post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
                .extract().response().asString();
        JsonPath js = reUsableMethods.rawToJson(response);
        String bookId = js.get("ID");
    }
}
