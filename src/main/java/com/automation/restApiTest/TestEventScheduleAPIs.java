package com.automation.restApiTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.automation.Data.ResponseCode.STATUSCODE_CREATED;
import static com.automation.Data.ResponseStatus.RESPONSE_CREATED;
import static com.automation.Data.ResponseStatus.RESPONSE_OK;
import static com.automation.utils.CommonUtils.*;
import static com.automation.utils.PropertyUtils.getPropertyByKey;
import static com.automation.utils.PropertyUtils.loadProperties;
import static com.automation.utils.RequestUtils.restRequest;

public class TestEventScheduleAPIs {
    @BeforeClass
    public void beforeSuit() {
        loadProperties();
    }

    @Test(priority = 0, description = "Admin user login API test")
    public void postLoginApi() {
        Response response = restRequest("post.login.user.body");
        // tests
        verifyResponseStatus(RESPONSE_OK, response);
        response.then().statusCode(200);
        response.then().body("access_token", Matchers.anything());

        JsonPath jsonPathEvaluator = response.jsonPath();
        String accessToken = jsonPathEvaluator.get("access_token");
        System.setProperty("access_token", accessToken);
    }

    @Test(priority = 1, description = "Creating new event-type API test")
    public void testPostCreateEventType() {
        Response response = restRequest("post.create.event.type.body");
        verifyResponseStatus(RESPONSE_CREATED, response);
        verifyResponseStatusCode(STATUSCODE_CREATED, response);
        verifyResponseJsonPathValue(response, "data.attributes.name", getPropertyByKey("event.type"));
    }

    @Test(priority = 2, description = "Creating new event-topic API test")
    public void testCreateEventTopic() {
        Response response = restRequest("post.create.event.topic.body");
        verifyResponseStatusCode(STATUSCODE_CREATED, response);
        verifyResponseStatus(RESPONSE_CREATED, response);
        verifyResponseJsonPathValue(response, "data.attributes.name", getPropertyByKey("event.topic"));
    }

    @Test(priority = 3, description = "Creating new event-subtopic API test")
    public void testCreateEventSubTopic() {
        Response response = restRequest("post.create.event.sub.topic.body");
        verifyResponseStatusCode(STATUSCODE_CREATED, response);
        verifyResponseStatus(RESPONSE_CREATED, response);
        verifyResponseJsonPathValue(response, "data.attributes.name", getPropertyByKey("event.subtopic"));
    }

    @Test(priority = 4, description = "Creating new event API Test")
    public void testCreateAnEvent() {
        Response response = restRequest("post.create.event.body");
        verifyResponseStatusCode(STATUSCODE_CREATED, response);
        verifyResponseStatus(RESPONSE_CREATED, response);
    }
}
