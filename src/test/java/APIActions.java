import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class APIActions {
    private static Response response;
    private static RequestSpecification request = given().relaxedHTTPSValidation();
    private static String baseURI;
    private static String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTQ2NTAiLCJleHAiOjE2MzI3NTQxNjd9.XWFZTpN82vedwuowoI9_7vSnGcPZSi1xULA8tpMguY4";
    private static String bearerToken;

    private static void setAPIEnvironment() {
        baseURI = "https://um-api-test.technical-service-portal-test-vodafone.de/api/";
    }

    public static RequestSpecification prepareRequest(String endPoint, String auth) {
        setAPIEnvironment();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseURI);
        requestSpecBuilder.setBasePath(endPoint);
        Map<String, String> header = new HashMap<>();
        if (Objects.equals(endPoint, "auth/token")) {
            header.put("Authentication", auth);
        } else {
            header.put("Authorization", auth);
        }
        requestSpecBuilder.addHeaders(header);
        return requestSpecBuilder.build();
    }

//    @Test
    public static void getBearerToken() {
        String authentication_EndPoint = "auth/token";
        request = given().spec(prepareRequest(authentication_EndPoint, token));
        response = request.when().get().then().extract().response();
        bearerToken = response.jsonPath().get("accessToken").toString();
        System.out.println(response.getBody().asString());
        System.out.println(bearerToken.trim());
    }

    @Test
    public static void getProfile() {
        getBearerToken();
        String getProfile_EndPoint = "users/profile";
        request = given().spec(prepareRequest(getProfile_EndPoint, bearerToken));
        response = request.when().get().then().extract().response();
        System.out.println(response.getBody().asString());
        System.out.println(response.jsonPath().get("name"));
    }
}