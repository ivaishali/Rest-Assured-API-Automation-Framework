package com.automation.restApiTest;

import com.automation.Data.ContentTypeEnum;
import com.automation.utils.RequestUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.automation.utils.PropertyUtils.getPropertyByKey;
import static com.automation.utils.PropertyUtils.loadProperties;
import static io.restassured.RestAssured.given;

public class TestUserAuthenticationAPIs {

    @BeforeClass
    public void beforeClassMethod() {
        loadProperties();
    }

    @Test(priority = 0)
    public void testPostLoginApi() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"email\": \"admin@mailinator.com\",\n" +
                        "  \"password\": \"admin123#\"\n" +
                        "}")
                .when()
                .post(getPropertyByKey("base.url") + getPropertyByKey("post.login.user"));
        System.out.println("POST Response\n" + response.asString());
        // tests
        response.then().body("access_token", Matchers.anything());

        JsonPath jsonPathEvaluator = response.jsonPath();
        String accessToken = jsonPathEvaluator.get("access_token");
        System.setProperty("access_token", accessToken);
    }

    @Test(priority = 1)
    public void testGetUsersRestAPI() {
        String access = System.getProperty("access_token");
        Response response = RequestUtils.getRequest(getPropertyByKey("get.user.list"), ContentTypeEnum.ContentTypes.APPLICATION_VID_JSON, String.format(getPropertyByKey("authorization.token"), access));
        System.out.println(response.asString());
        // tests
        response.then().statusCode(200);
    }
}
