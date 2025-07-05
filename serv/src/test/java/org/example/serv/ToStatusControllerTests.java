package org.example.serv;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ToStatusControllerTests {

    String serviceUrl = String.format("%s/api/v1/tostatuses", TestUtil.app_url);
    String toStatusId = "da7ee44a-cb0b-4c63-bf4f-f51632fc3606";

    @Test
    public void get_Test() throws JSONException {
        String resStr = given()
                .when().get(serviceUrl)
                .then()
                .log().all()
                .statusCode(200)
                .body("listData", notNullValue())
                .body("count", equalTo(3))
                .extract().body().asString();
        Assertions.assertTrue(resStr.contains(toStatusId));
    }
}
