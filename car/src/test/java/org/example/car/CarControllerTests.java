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
import static org.hamcrest.Matchers.equalTo;

public class CarControllerTests {

    String serviceUrl = String.format("%s/api/v1/cars", TestUtil.app_url);
    String motorTypeId = "be2a7dc4-fb32-4e98-80a7-801025bfa373" ;
    String markModelId = "87ed5551-b718-4a81-9ad9-80a73882afea";
    String login = "l1";

    @Test
    public void empty_data_Test() throws JSONException {
        String dataEmpty = "{}";
        String responseStr = given()
                .contentType(ContentType.JSON)
                .body(dataEmpty)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(400)
                .extract().body().asString();

        Assertions.assertNotNull(responseStr);
        JSONObject jsonObj = new JSONObject(responseStr);
        Assertions.assertNotNull(jsonObj);
        Assertions.assertEquals("$.regNumber: is missing but it is required; $.login: is missing but it is required; $.motorTypeId: is missing but it is required; $.markModelId: is missing but it is required", jsonObj.getString("message"));
    }

    @Test
    public void noRegNumber_required_field_Test() throws JSONException {
        String data = String.format("{\"motorTypeId\":\"%s\",\"markModelId\":\"%s\",\"login\":\"%s\"}", motorTypeId, markModelId, login);

        String responseStr = given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(400)
                .extract().body().asString();

        Assertions.assertNotNull(responseStr);
        JSONObject jsonObj = new JSONObject(responseStr);
        Assertions.assertNotNull(jsonObj);
        Assertions.assertEquals("$.regNumber: is missing but it is required", jsonObj.getString("message"));
    }

    @Test
    public void max_length_name_Test() throws JSONException {
        String name = String.format("%s%s%s", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        String data = String.format("{\"name\":\"%s\",\"oilTypeId\":\"%s\",\"oilViscosityId\":\"%s\"}",name, motorTypeId, markModelId);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(400);
    }

    @Test
    public void wrong_motor_type_Test() throws JSONException {
        String regNumber =UUID.randomUUID().toString().substring(0,8);
        String motorTypeId = UUID.randomUUID().toString();
        String data = String.format("{\"regNumber\":\"%s\",\"motorTypeId\":\"%s\",\"markModelId\":\"%s\",\"login\":\"%s\"}",regNumber, motorTypeId, markModelId, login);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(422);
    }

    @Test
    public void wrong_markModel_Test() throws JSONException {
        String regNumber =UUID.randomUUID().toString().substring(0,8);
        String markModelId = UUID.randomUUID().toString();
        String data = String.format("{\"regNumber\":\"%s\",\"motorTypeId\":\"%s\",\"markModelId\":\"%s\",\"login\":\"%s\"}",regNumber, motorTypeId, markModelId, login);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(422);
    }

    @Test
    public void create_get_list_Test() throws JSONException {
        String regNumber =UUID.randomUUID().toString().substring(0,8);
        String data = String.format("{\"regNumber\":\"%s\",\"motorTypeId\":\"%s\",\"markModelId\":\"%s\",\"login\":\"%s\"}",regNumber, motorTypeId, markModelId, login);

        String getStr = given()
                .when().get(serviceUrl)
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        int count = new JSONObject(getStr).getInt("count");
        Assertions.assertFalse(getStr.contains(regNumber));

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(201)
                .body("id", hasLength(36));

        String getStr2 = given()
                .when().get(serviceUrl)
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        int count2 = new JSONObject(getStr2).getInt("count");
        Assertions.assertEquals(count+1, count2);
        Assertions.assertTrue(getStr2.contains(regNumber));
    }

    @Test
    public void create_get_Test() throws JSONException {
        String regNumber =UUID.randomUUID().toString().substring(0,8);
        String data = String.format("{\"regNumber\":\"%s\",\"motorTypeId\":\"%s\",\"markModelId\":\"%s\",\"login\":\"%s\"}",regNumber, motorTypeId, markModelId, login);

        String createStr =given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(201)
                .body("id", hasLength(36))
                .extract().body().asString();
        String id = new JSONObject(createStr).getString("id");
        String urlOil = String.format("%s/%s", serviceUrl, id);

        String substringDate = ZonedDateTime.now(Clock.systemUTC()).toString().substring(0, 10);

        given()
                .when().get(urlOil)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("actual", equalTo(true))
                .body("version", equalTo(0))
                .body("dateCreated", startsWithIgnoringCase(substringDate))
                .body("lastUpdated", startsWithIgnoringCase(substringDate))
                .body("regNumber", equalTo(regNumber))
                .body("motorType", notNullValue())
                .body("motorType.id", equalTo(motorTypeId))
                .body("motorType.name", equalTo("Бензин"))
                .body("motorType.sysName", equalTo("benz"))
                .body("motorType.actual", equalTo(true))
                .body("markModel", notNullValue())
                .body("markModel.id", equalTo(markModelId))
                .body("markModel.name", equalTo("Mitsubishi Outlander"))
                .body("markModel.sysName", equalTo("mits_out"))
                .body("markModel.actual", equalTo(true));
    }

    @Test
    public void create_getByRegNumber_Test() throws JSONException {
        String regNumber =UUID.randomUUID().toString().substring(0,8);
        String data = String.format("{\"regNumber\":\"%s\",\"motorTypeId\":\"%s\",\"markModelId\":\"%s\",\"login\":\"%s\"}",regNumber, motorTypeId, markModelId, login);

        String createStr =given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(201)
                .body("id", hasLength(36))
                .extract().body().asString();
        String id = new JSONObject(createStr).getString("id");
        String urlOil = String.format("%s/byregnumber/%s", serviceUrl, regNumber);

        String substringDate = ZonedDateTime.now(Clock.systemUTC()).toString().substring(0, 10);

        given()
                .when().get(urlOil)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("actual", equalTo(true))
                .body("version", equalTo(0))
                .body("dateCreated", startsWithIgnoringCase(substringDate))
                .body("lastUpdated", startsWithIgnoringCase(substringDate))
                .body("regNumber", equalTo(regNumber))
                .body("motorType", notNullValue())
                .body("motorType.id", equalTo(motorTypeId))
                .body("motorType.name", equalTo("Бензин"))
                .body("motorType.sysName", equalTo("benz"))
                .body("motorType.actual", equalTo(true))
                .body("markModel", notNullValue())
                .body("markModel.id", equalTo(markModelId))
                .body("markModel.name", equalTo("Mitsubishi Outlander"))
                .body("markModel.sysName", equalTo("mits_out"))
                .body("markModel.actual", equalTo(true));
    }

    @Test
    public void wrong_login_Test(){
        String regNumber = UUID.randomUUID().toString().substring(0, 8);
        String login= "abracadabra";
        String data = String.format("{\"regNumber\":\"%s\",\"motorTypeId\":\"%s\",\"markModelId\":\"%s\",\"login\":\"%s\"}", regNumber, motorTypeId, markModelId, login);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(422);
    }
}