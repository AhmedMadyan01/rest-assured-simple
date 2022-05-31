import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssured {

    Response response;

    @Test
    public void validateStatusCode() {
        given().get("https://parabank.parasoft.com/parabank/services/bank/customers/12212").then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void validateStatusCodeFromResponse() {
        response = io.restassured.RestAssured.get("https://parabank.parasoft.com/parabank/services/bank/customers/12212");
        response.then().statusCode(HttpStatus.SC_OK);
        System.out.println("API Status Code = " + response.statusCode());
    }

    @Test
    public void validateResponseBody() {
        response = io.restassured.RestAssured.get("https://parabank.parasoft.com/parabank/services/bank/customers/12212");
        response.then().body("customer.id", equalTo("12212"))
                .and().body("customer.firstName", equalTo("JohnArtium"))
                .and().body("customer.lastName", equalTo("Lifshitz"));
        System.out.println(response.body().asString());
        System.out.println("API Status Code = " + response.statusCode());
    }
}
