package org.example.oil;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OilViscosityControllerTests {

    String serviceUrl = String.format("%s/api/v1/oilviscosities", TestUtil.app_url);
    String oilViscosityId = "ef8ac859-3ab8-49de-9d39-de0effc16a3a";
    @Test
    public void get_Test() throws JSONException {
        String resStr = given()
                .when().get(serviceUrl)
                .then()
                .log().all()
                .statusCode(200)
                .body("listData", notNullValue())
                .body("count", equalTo(6))
                .extract().body().asString();
        Assertions.assertTrue(resStr.contains(oilViscosityId));
    }
}
