package org.example.oil;

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

public class OilTypeControllerTests {

    String serviceUrl = String.format("%s/api/v1/oiltypes", TestUtil.app_url);
    String oilTypeId = "ba54b3b7-1dab-41a8-976a-6bfe68d67d41" ;

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
        Assertions.assertTrue(resStr.contains(oilTypeId));
    }
}
