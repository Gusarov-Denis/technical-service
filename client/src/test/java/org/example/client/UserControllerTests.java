package org.example.client;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasLength;

public class UserControllerTests {

    String userServiceUrl = String.format("%s/api/v1/users", TestUtil.app_url);

    @Test
    public void empty_data_Test() throws JSONException {
        String dataEmpty = "{}";
        String responseStr = given()
                .contentType(ContentType.JSON)
                .body(dataEmpty)
                .log().all()
                .when().post(userServiceUrl)
                .then().log().all()
                .statusCode(400)
                .extract().body().asString();

        Assertions.assertNotNull(responseStr);
        JSONObject jsonObj = new JSONObject(responseStr);
        Assertions.assertNotNull(jsonObj);
        Assertions.assertEquals("$.login: is missing but it is required; $.password: is missing but it is required; $.email: is missing but it is required", jsonObj.getString("message"));
    }

    @Test
    public void noLogin_required_field_Test() throws JSONException {
        String login = UUID.randomUUID().toString();
        String password = login.substring(0, 15);
        String email = String.format("%s@example.com", login);
        String data = String.format("{\"password\":\"%s\",\"email\":\"%s\"}", password, email);

        String responseStr = given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(userServiceUrl)
                .then().log().all()
                .statusCode(400)
                .extract().body().asString();

        Assertions.assertNotNull(responseStr);
        JSONObject jsonObj = new JSONObject(responseStr);
        Assertions.assertNotNull(jsonObj);
        Assertions.assertEquals("$.login: is missing but it is required", jsonObj.getString("message"));
    }

    @Test
    public void max_length_login_Test() throws JSONException {
        String login = String.format("%s%s%s", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        String password = login.substring(0, 15);
        String email = String.format("%s@example.com", login);
        String data = String.format("{\"login\":\"%s\",\"password\":\"%s\",\"email\":\"%s\"}", login, password, email);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(userServiceUrl)
                .then().log().all()
                .statusCode(400);
    }

    @Test
    public void create_getEmail_Test() throws JSONException {
        String login = UUID.randomUUID().toString();
        String password = login.substring(0, 15);
        String email = String.format("%s@example.com", login);
        String data = String.format("{\"login\":\"%s\",\"password\":\"%s\",\"email\":\"%s\"}", login, password, email);


        given()
                .contentType(ContentType.JSON)
                .body(data)
                .log().all()
                .when().post(userServiceUrl)
                .then().log().all()
                .statusCode(201)
                .body("id", hasLength(36))
                .extract().body().asString();

        String getEmailUrl = String.format("%s/emailbylogin/%s", userServiceUrl, login);

        String emailStr = given()
                .when().get(getEmailUrl)
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();
        Assertions.assertNotNull(emailStr);
        Assertions.assertEquals(email, emailStr);
    }

    @Test
    public void getNotExistEmail_Test() throws JSONException {
        String login = UUID.randomUUID().toString();
        String password = login.substring(0, 15);
        String email = String.format("%s@example.com", login);
        String getEmailUrl = String.format("%s/emailbylogin/%s", userServiceUrl, login);

        given()
                .when().get(getEmailUrl)
                .then()
                .log().all()
                .statusCode(404);
    }
}
