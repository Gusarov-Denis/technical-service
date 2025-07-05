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

public class OilControllerTests {

    String serviceUrl = String.format("%s/api/v1/oils", TestUtil.app_url);
    String oilTypeId = "ba54b3b7-1dab-41a8-976a-6bfe68d67d41" ;
    String oilViscosityId = "ef8ac859-3ab8-49de-9d39-de0effc16a3a";

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
        Assertions.assertEquals("$.name: is missing but it is required; $.oilTypeId: is missing but it is required; $.oilViscosityId: is missing but it is required", jsonObj.getString("message"));
    }

    @Test
    public void noName_required_field_Test() throws JSONException {
        String data = String.format("{\"oilTypeId\":\"%s\",\"oilViscosityId\":\"%s\"}", oilTypeId, oilViscosityId);

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
        String data = String.format("{\"name\":\"%s\",\"oilTypeId\":\"%s\",\"oilViscosityId\":\"%s\"}",name, oilTypeId, oilViscosityId);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(400);
    }

    @Test
    public void wrong_oil_type_Test() throws JSONException {
        String name =UUID.randomUUID().toString();
        String oilTypeId = UUID.randomUUID().toString();
        String data = String.format("{\"name\":\"%s\",\"oilTypeId\":\"%s\",\"oilViscosityId\":\"%s\"}",name, oilTypeId, oilViscosityId);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(422);
    }

    @Test
    public void wrong_oil_voilViscosity_Test() throws JSONException {
        String name =UUID.randomUUID().toString();
        String oilViscosityId = UUID.randomUUID().toString();
        String data = String.format("{\"name\":\"%s\",\"oilTypeId\":\"%s\",\"oilViscosityId\":\"%s\"}",name, oilTypeId, oilViscosityId);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(422);
    }

    @Test
    public void create_get_all_Test() throws JSONException {
        String name =UUID.randomUUID().toString();
        String data = String.format("{\"name\":\"%s\",\"oilTypeId\":\"%s\",\"oilViscosityId\":\"%s\"}",name, oilTypeId, oilViscosityId);


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
        String data = String.format("{\"name\":\"%s\",\"oilTypeId\":\"%s\",\"oilViscosityId\":\"%s\"}",name, oilTypeId, oilViscosityId);

        String oilCreateStr =given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(serviceUrl)
                .then().log().all()
                .statusCode(201)
                .body("id", hasLength(36))
                .extract().body().asString();
        String oil_Id = new JSONObject(oilCreateStr).getString("id");
        String urlOil = String.format("%s/%s", serviceUrl, oil_Id);

        String substringDate = ZonedDateTime.now(Clock.systemUTC()).toString().substring(0, 10);

        given()
                .when().get(urlOil)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(oil_Id))
                .body("actual", equalTo(true))
                .body("version", equalTo(0))
                .body("dateCreated", startsWithIgnoringCase(substringDate))
                .body("lastUpdated", startsWithIgnoringCase(substringDate))
                .body("name", equalTo(name))
                .body("oilType", notNullValue())
                .body("oilType.id", equalTo(oilTypeId))
                .body("oilType.name", equalTo("Синтетическое"))
                .body("oilType.sysName", equalTo("synthetic"))
                .body("oilType.actual", equalTo(true))
                .body("oilViscosity", notNullValue())
                .body("oilViscosity.id", equalTo(oilViscosityId))
                .body("oilViscosity.name", equalTo("5w10"))
                .body("oilViscosity.sysName", equalTo("5w10"))
                .body("oilViscosity.actual", equalTo(true));
    }
}
