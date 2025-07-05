package org.example.serv;

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

public class ToControllerTests {

    String serviceUrl = String.format("%s/api/v1/tos", TestUtil.app_url);
    String oilId = "414f631c-9b9d-4c3a-9d1b-4e7329f9126d" ;
    String carId = "3692993a-ac1f-4fea-8bc3-8a89d67031d5";
    String login = "l1";
    Integer mileage = 0;
    String toStatusId = "da7ee44a-cb0b-4c63-bf4f-f51632fc3606";

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
        Assertions.assertEquals("$.name: is missing but it is required; $.login: is missing but it is required; $.oilId: is missing but it is required; $.carId: is missing but it is required; $.mileage: is missing but it is required; $.toStatusId: is missing but it is required", jsonObj.getString("message"));
    }

    @Test
    public void noName_required_field_Test() throws JSONException {
        String data = String.format("{\"oilId\":\"%s\",\"carId\":\"%s\",\"mileage\":%s,\"login\":\"%s\",\"toStatusId\":\"%s\"}", oilId, carId, mileage, login, toStatusId);

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
        Assertions.assertEquals("$.name: is missing but it is required", jsonObj.getString("message"));
    }

    @Test
    public void max_length_name_Test() throws JSONException {
        String name = String.format("%s%s%s", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        String data = String.format("{\"name\":\"%s\",\"oilTypeId\":\"%s\",\"oilViscosityId\":\"%s\"}",name, oilId, carId);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(400);
    }

    @Test
    public void wrong_carId_Test() throws JSONException {
        String name =UUID.randomUUID().toString();
        String carId = UUID.randomUUID().toString();
        String data = String.format("{\"name\":\"%s\",\"oilId\":\"%s\",\"carId\":\"%s\",\"mileage\":%s,\"login\":\"%s\",\"toStatusId\":\"%s\"}", name, oilId, carId, mileage, login, toStatusId);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(422);
    }

    @Test
    public void wrong_oilId_Test() throws JSONException {
        String name =UUID.randomUUID().toString();
        String oilId = UUID.randomUUID().toString();
        String data = String.format("{\"name\":\"%s\",\"oilId\":\"%s\",\"carId\":\"%s\",\"mileage\":%s,\"login\":\"%s\",\"toStatusId\":\"%s\"}", name, oilId, carId, mileage, login, toStatusId);

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
        String name =UUID.randomUUID().toString();
        String data = String.format("{\"name\":\"%s\",\"oilId\":\"%s\",\"carId\":\"%s\",\"mileage\":%s,\"login\":\"%s\",\"toStatusId\":\"%s\"}", name, oilId, carId, mileage, login, toStatusId);

        String getStr = given()
                .when().get(serviceUrl)
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        int count = new JSONObject(getStr).getInt("count");
        Assertions.assertFalse(getStr.contains(name));

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
        Assertions.assertTrue(getStr2.contains(name));
    }

    @Test
    public void create_get_Test() throws JSONException {
        String name =UUID.randomUUID().toString();
        String data = String.format("{\"name\":\"%s\",\"oilId\":\"%s\",\"carId\":\"%s\",\"mileage\":%s,\"login\":\"%s\",\"toStatusId\":\"%s\"}", name, oilId, carId, mileage, login, toStatusId);

        String oilCreateStr =given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(201)
                .body("id", hasLength(36))
                .extract().body().asString();
        String id = new JSONObject(oilCreateStr).getString("id");
        String urlById = String.format("%s/%s", serviceUrl, id);

        String substringDate = ZonedDateTime.now(Clock.systemUTC()).toString().substring(0, 10);

        given()
                .when().get(urlById)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("actual", equalTo(true))
                .body("version", equalTo(0))
                .body("dateCreated", startsWithIgnoringCase(substringDate))
                .body("lastUpdated", startsWithIgnoringCase(substringDate))
                .body("name", equalTo(name))
                .body("login", equalTo(login))
                .body("oilId", equalTo(oilId))
                .body("carId", equalTo(carId))
                .body("mileage", equalTo(mileage))
                .body("dateTo", nullValue())
                .body("toStatus", notNullValue())
                .body("toStatus.id", equalTo("da7ee44a-cb0b-4c63-bf4f-f51632fc3606"))
                .body("toStatus.name", equalTo("Проведено ТО"))
                .body("toStatus.sysName", equalTo("done"))
                .body("toStatus.actual", equalTo(true));
    }

    @Test
    public void create_Change_Status_Scheduler_NotRequiredToRequired_Test() throws JSONException {
        String name =UUID.randomUUID().toString();
        String toStatusId ="c409b9aa-4643-4115-a308-c829c492291f"; //Не требуется ТО
        String data = String.format("{\"name\":\"%s\",\"oilId\":\"%s\",\"carId\":\"%s\",\"mileage\":%s,\"login\":\"%s\",\"toStatusId\":\"%s\"}", name, oilId, carId, mileage, login, toStatusId);

        String oilCreateStr =given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(201)
                .body("id", hasLength(36))
                .extract().body().asString();
        String id = new JSONObject(oilCreateStr).getString("id");
        String urlById = String.format("%s/%s", serviceUrl, id);

        String substringDate = ZonedDateTime.now(Clock.systemUTC()).toString().substring(0, 10);

        try {
            Thread.sleep(65000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        given()
                .when().get(urlById)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("actual", equalTo(true))
                .body("version", equalTo(1))
                .body("dateCreated", startsWithIgnoringCase(substringDate))
                .body("lastUpdated", startsWithIgnoringCase(substringDate))
                .body("name", equalTo(name))
                .body("login", equalTo(login))
                .body("oilId", equalTo(oilId))
                .body("carId", equalTo(carId))
                .body("mileage", equalTo(mileage))
                .body("dateTo", nullValue())
                .body("toStatus", notNullValue())
                .body("toStatus.id", equalTo("de32b8d3-5852-4776-a459-7899ecb2f0c5"))
                .body("toStatus.name", equalTo("Требуется провести ТО"))
                .body("toStatus.sysName", equalTo("required"))
                .body("toStatus.actual", equalTo(true));
    }

    @Test
    public void create_Change_Status_Scheduler_DoneToRequired_Test() throws JSONException {
        String name =UUID.randomUUID().toString();
        String toStatusId ="da7ee44a-cb0b-4c63-bf4f-f51632fc3606"; //Проведено ТО
        String dateTo ="2023-09-26T15:30:00.123Z";
        String data = String.format("{\"dateTo\":\"%s\",\"name\":\"%s\",\"oilId\":\"%s\",\"carId\":\"%s\",\"mileage\":%s,\"login\":\"%s\",\"toStatusId\":\"%s\"}", dateTo, name, oilId, carId, mileage, login, toStatusId);

        String oilCreateStr =given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(201)
                .body("id", hasLength(36))
                .extract().body().asString();
        String id = new JSONObject(oilCreateStr).getString("id");
        String urlById = String.format("%s/%s", serviceUrl, id);

        String substringDate = ZonedDateTime.now(Clock.systemUTC()).toString().substring(0, 10);

        try {
            Thread.sleep(65000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        given()
                .when().get(urlById)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("actual", equalTo(true))
                .body("version", equalTo(1))
                .body("dateCreated", startsWithIgnoringCase(substringDate))
                .body("lastUpdated", startsWithIgnoringCase(substringDate))
                .body("name", equalTo(name))
                .body("login", equalTo(login))
                .body("oilId", equalTo(oilId))
                .body("carId", equalTo(carId))
                .body("mileage", equalTo(mileage))
                .body("dateTo", equalTo(dateTo))
                .body("toStatus", notNullValue())
                .body("toStatus.id", equalTo("de32b8d3-5852-4776-a459-7899ecb2f0c5"))
                .body("toStatus.name", equalTo("Требуется провести ТО"))
                .body("toStatus.sysName", equalTo("required"))
                .body("toStatus.actual", equalTo(true));
    }
}
