package files;

public class payload {
    public static String AddPlace() {
        return "{\n" +
                "\t\"location\":{\n" +
                "    \t\"lat\" : -38.383494,\n" +
                "    \t\"lng\" : 33.427362\n" +
                "\t},\n" +
                "\t\"accuracy\":50,\n" +
                "\t\"name\":\"Frontline house\",\n" +
                "\t\"phone_number\":\"(+91) 983 893 3937\",\n" +
                "\t\"address\" : \"29, side layout, cohen 09\",\n" +
                "\t\"types\": [\n" +
                "        \"shoe park\",\"shop\"\n" +
                "        ],\n" +
                "\t\"website\" : \"https://rahulshettyacademy.com\",\n" +
                "\t\"language\" : \"French-IN\"\n" +
                "}";
    }
}
