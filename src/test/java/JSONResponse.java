import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class JSONResponse {
    String baseURI = "https://reqres.in/";
    String endPoint = "api/users?page=2";
    Response response;

    @Test
    public void validateJSONResponse() {
        response = RestAssured.given().get(baseURI + endPoint);
        response.then().statusCode(HttpStatus.SC_OK);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        System.out.println(jsonPath.getString("data.id"));
        List<HashMap> list = jsonPath.getList("data.id");
        Integer[] x = {3, 2, 7, 9, 1, 6, 0, 1};
        Arrays.sort(x, Collections.reverseOrder());
        System.out.printf("%s", Arrays.toString(x));
    }

    @Test
    public void validateJSONResponse2() {
        response = RestAssured.given().get(baseURI + endPoint);
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("data[0].id",equalTo("7"));
    }
}
