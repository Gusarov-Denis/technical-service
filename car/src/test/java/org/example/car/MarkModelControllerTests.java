package org.example.car;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MarkModelControllerTests {

    String serviceUrl = String.format("%s/api/v1/markmodels", TestUtil.app_url);
    String markModelId = "87ed5551-b718-4a81-9ad9-80a73882afea";

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
        Assertions.assertTrue(resStr.contains(markModelId));
    }
}