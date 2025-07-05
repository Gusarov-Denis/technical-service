package org.example.car;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class MotorTypeControllerTests {

    String serviceUrl = String.format("%s/api/v1/motortypes", TestUtil.app_url);
    String motorTypeId = "be2a7dc4-fb32-4e98-80a7-801025bfa373" ;

    @Test
    public void get_Test() throws JSONException {
        String resStr = given()
                .when().get(serviceUrl)
                .then()
                .log().all()
                .statusCode(200)
                .body("listData", notNullValue())
                .body("count", equalTo(5))
                .extract().body().asString();
        Assertions.assertTrue(resStr.contains(motorTypeId));
    }
}