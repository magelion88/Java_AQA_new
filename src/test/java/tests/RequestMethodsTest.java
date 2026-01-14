package tests;

import org.junit.jupiter.api.Test;
import utils.TestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RequestMethodsTest extends TestBase {

    @Test
    public void testGetRequestSimple() {
        given()
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .when()
                .get("/get")
                .then()
                .statusCode(200)

                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"))
                .body("headers.host", equalTo("postman-echo.com"))
                .body("headers.accept", containsString("*"))
                .body("url", equalTo("https://postman-echo.com/get?foo1=bar1&foo2=bar2"));
    }

    @Test
    public void testPostRawText() {
        String rawTextBody = "{\n    \"test\": \"value\"\n}";

        given()
                .contentType("text/plain")  // важно: text/plain, а не application/json
                .body(rawTextBody)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data", equalTo(rawTextBody))  // data содержит отправленный текст
                .body("headers.content-type", containsString("text/plain"))
                .body("headers.host", equalTo("postman-echo.com"))
                .body("json", nullValue())  // json будет null при text/plain
                .body("url", equalTo("https://postman-echo.com/post"));
    }

    @Test
    public void testPostFormDataMultiPart() {
        given()
                .contentType("multipart/form-data")  // multipart вместо urlencoded
                .multiPart("foo1", "bar1")
                .multiPart("foo2", "bar2")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("form.foo1", equalTo("bar1"))
                .body("form.foo2", equalTo("bar2"));
    }

    @Test
    public void testPutRequest() {
        String requestBody = "This is expected to be sent back as part of response body.";

        given()
                .contentType("text/plain")
                .body(requestBody)
                .when()
                .put("/put")
                .then()
                .statusCode(200)
                .body("data", equalTo(requestBody))
                .body("json", nullValue())
                .body("headers.host", equalTo("postman-echo.com"))
                .body("headers.content-type", containsString("text/plain"))
                .body("url", equalTo("https://postman-echo.com/put"));
    }

    @Test
    public void testPatchRequest() {
        String requestBody = "This is expected to be sent back as part of response body.";

        given()
                .contentType("text/plain")
                .body(requestBody)
                .when()
                .patch("/patch")
                .then()
                .statusCode(200)
                .body("data", equalTo(requestBody))
                .body("json", nullValue())
                .body("headers.host", equalTo("postman-echo.com"))
                .body("headers.content-type", containsString("text/plain"))
                .body("url", equalTo("https://postman-echo.com/patch"));
    }

    @Test
    public void testDeleteRequest() {
        String requestBody = "This is expected to be sent back as part of response body.";

        given()
                .contentType("text/plain")
                .body(requestBody)
                .when()
                .delete("/delete")
                .then()
                .statusCode(200)
                .body("data", equalTo(requestBody))
                .body("json", nullValue())
                .body("headers.host", equalTo("postman-echo.com"))
                .body("headers.content-type", containsString("text/plain"))
                .body("url", equalTo("https://postman-echo.com/delete"));
    }
}