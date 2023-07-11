package e_commerce;

import e_commerce.pojo.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.springframework.util.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.util.Assert.*;

public class ECommerceAPITest {
    public static void main(String[] args) {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        //Login
        RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(new LoginRequest("daoudbachar@gmail.com", "Shams2015"));
        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response()
                .as(LoginResponse.class);
        System.out.println(loginResponse.getToken());

        //Add Product
        RequestSpecification reqAddProductUrl = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addHeader("authorization", loginResponse.getToken())
                .build();
        RequestSpecification reqAddProduct =
                given().log().all().spec(reqAddProductUrl)
                        .param("productName", "My New Laptop")
                        .param("productAddedBy", loginResponse.getUserId())
                        .param("productCategory", "Electronic")
                        .param("productSubCategory", "Programming")
                        .param("productPrice", "1200")
                        .param("productDescription", "It is ram 400 mg")
                        .param("productFor", "Everyone")
                        .multiPart("productImage", new File("/Users/bachardaowd/Desktop/IMG-20181104-WA0016.jpg"));
         ResponseAddProduct responseAddProduct = reqAddProduct.when().post("/api/ecom/product/add-product").
                then().log().all().extract().response().as(ResponseAddProduct.class);

         //Create Order
        Order order = new Order("india", responseAddProduct.getProductId());
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        CreateOrder orders = new CreateOrder(orderList);

        RequestSpecification reqAddOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                setContentType(ContentType.JSON).
                addHeader("authorization", loginResponse.getToken())
                .build();
        RequestSpecification reqCreateOrder = given().log().all().spec(reqAddOrder).body(orders);
        String responseAddOrder = reqCreateOrder.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();

        //Delete Product
        RequestSpecification reqDeleteProductBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addHeader("authorization", loginResponse.getToken())
                .build();
        String productId = responseAddProduct.getProductId();
        RequestSpecification reqDeleteProduct = given().log().all().spec(reqDeleteProductBase).pathParam("productId", productId);
        String reqDeleteOrderResponse = reqDeleteProduct.when().delete("/api/ecom/product/delete-product/{productId}").
                then().log().all().extract().response().asString();
        JsonPath js = new JsonPath(reqDeleteOrderResponse);
        Assert.assertEquals("Product Deleted Successfully", js.get("message"));

    }
}
