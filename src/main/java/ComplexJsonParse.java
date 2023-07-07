import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(payload.CoursePrice());
        int count = js.getInt("courses.size()");
        System.out.println(count);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);
        String titleOfFirstCourse = js.get("courses[0].title");
        System.out.println(titleOfFirstCourse);


        for (int i = 0; i < count; i++) {
            String title = js.get("courses["+i+"].title");
            int cost = js.getInt("courses["+i+"].price");
            if(title.equalsIgnoreCase("Selenium Python")) {
                System.out.println("The " +title + " coasts " + cost + " euro");
            }
        }


    }
}
