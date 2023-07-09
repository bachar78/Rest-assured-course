package files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.*;

public class JiraTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "http://localhost:8080";

        //Login Scenario
        SessionFilter session = new SessionFilter();
        String responseLogin = given().header("Content-Type", "application/json")
                .body("{ \"username\": \"Bachar_Daowd\", \"password\": \"Shams1978\" }")
                .log().all()
                .filter(session).
                when().post("/rest/auth/1/session").
                then().log().all().extract().response().asString();

        String expectedMessage = "This is an comment for Rest Assured Intellij Course for section 8";
//Add comment
        String responseComment = given().pathParam("key", "10003").log().all().
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"body\": \"" + expectedMessage + "\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").
                filter(session).
                when().post("/rest/api/2/issue/{key}/comment").
                then().log().all().assertThat().statusCode(201).extract().response().asString();
        JsonPath jsComment = new JsonPath(responseComment);
        String commentId = jsComment.getString("id");

//Add Attachment
        given().header("Content-Type", "multipart/form-data").
                pathParam("key", "10003").log().all().

                header("X-Atlassian-Token", "no-check").filter(session).
                multiPart("file", new File("jira.txt")).
                when().post("/rest/api/2/issue/{key}/attachments").
                then().log().all().assertThat().statusCode(200);
//Get issue
        String responseGet = given().filter(session).
                pathParam("key", "10003").log().all().
                queryParam("fields", "comment").
                when().get("/rest/api/2/issue/{key}").
                then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = new JsonPath(responseGet);
        int commentsCount = js.get("fields.comment.comments.size()");
        for (int i = 0; i < commentsCount; i++) {
            String requiredIssueComment = js.get("fields.comment.comments[" + i + "].id").toString();
            if (requiredIssueComment.equalsIgnoreCase(commentId)) {
                String message = js.get("fields.comment.comments[" + i + "].body");
                Assert.assertEquals(message, expectedMessage);
            }
        }
    }
}
