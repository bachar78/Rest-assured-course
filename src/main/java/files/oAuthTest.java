package files;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.deserialization.GetCourse;


import static io.restassured.RestAssured.*;

public class oAuthTest {
    public static void main(String[] args) {

//    System.setProperty("webdriver.chrome.driver","C://chromedriver.exe");
//    WebDriver driver = new ChromeDriver();
//    driver.get("");

// Because of Google updated it's security, we can't simulate the driver using selenium anymore so we hit the url manulay and will get the url manually.
//The url to get the code manually is url= https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php

        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AZEOvhX-ZgW2esPX2PHvg1HvZOjd5KQFgIixNU_xiRiQtHR7YlLB7DU29rapxKFzu3hbOw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
        String splitUrl = url.split("code=")[1];
        String code = splitUrl.split("&scope")[0];


       String accessTokenResponse = given().urlEncodingEnabled(false)
               .queryParams("code", code).
                queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").
                queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").
                queryParams("grant_type", "authorization_code").
                when().log().all().
               post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");



        GetCourse response = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

    }

}
