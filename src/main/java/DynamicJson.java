import files.payload;
import files.reUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DynamicJson {
    //Add some scenarios
    @Test(dataProvider="BooksData")
    public static void addBook(String aisle, String isbn) {
        //add book
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(payload.addBook(aisle, isbn))
                .when()
                .post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
                .extract().response().asString();
        JsonPath js = reUsableMethods.rawToJson(response);
        String bookId = js.get("ID");

        //delete book
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        //array = collection of elements
        //multidimensional array = collection of arrays
        // if we link the DataProvider with the above method so the test above will be executed as many arrays has the multidimensional array here
        return new Object[][]{{"ofjk", "124"}, {"klsk", "545"}, {"blkja", "098"}, {"ljklf", "234234"}};
    }
}
