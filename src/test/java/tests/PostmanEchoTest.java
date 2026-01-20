package tests;

import io.restassured.http.ContentType;  // ← ПРАВИЛЬНЫЙ импорт
import org.junit.jupiter.api.Test;
import utils.TestBase;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
// УДАЛИТЬ: import static javax.swing.text.DefaultStyledDocument.ElementSpec.ContentType;
import static org.hamcrest.Matchers.*;

public class PostmanEchoTest extends TestBase {

    @Test
    public void testGetRequest() {
        given()
                .queryParam("param1", "value1")
                .queryParam("param2", "value2")
                .when()
                .get("/get")
                .then()
                .statusCode(200)
                .body("args.param1", equalTo("value1"))
                .body("args.param2", equalTo("value2"))
                .body("url", containsString("/get"));
    }

    @Test
    public void testPostRequest() {
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("name", "Test User");
        jsonBody.put("email", "test@example.com");

        given()
                .contentType(ContentType.JSON)  // ← теперь это io.restassured.http.ContentType
                .body(jsonBody)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("json.name", equalTo("Test User"))
                .body("json.email", equalTo("test@example.com"));
    }
}