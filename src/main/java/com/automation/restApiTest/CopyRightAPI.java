package com.automation.restApiTest;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.automation.Data.ResponseCode.STATUSCODE_CREATED;
import static com.automation.Data.ResponseCode.STATUSCODE_OK;
import static com.automation.Data.ResponseStatus.RESPONSE_CREATED;
import static com.automation.Data.ResponseStatus.RESPONSE_OK;
import static com.automation.restApiTest.CommonRestAPI.loadPropertiesAndLoginUser;
import static com.automation.utils.CommonUtils.*;
import static com.automation.utils.PropertyUtils.getPropertyByKey;
import static com.automation.utils.RequestUtils.restRequest;
import static com.automation.utils.RequestUtils.restRequestForBodyUpdate;

public class CopyRightAPI {


    @BeforeClass
    public void beforeClass() {
        loadPropertiesAndLoginUser();
        createEvent();
    }


    @Test(priority = 0, description = "Create a new Event Copyright.")
    public void postCreateEventCopyRight() {
        String holdername = RandomStringUtils.randomAlphabetic(5);
        String licenceName = RandomStringUtils.randomAlphanumeric(5);
        String eventId = System.getProperty("event-id");

        String apiData = String.format(getPropertyByKey("post.create.event.copyright"), eventId, holdername, licenceName);
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_CREATED, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_CREATED));

        verifyResponseFromJsonPath(response, "$.data.attributes.holder", holdername);
        verifyResponseFromJsonPath(response, "$.data.attributes.licence", licenceName);

        String idOfEventCopyRight = getValueFromJsonPath(response, "$.data.id");
        System.setProperty("EventCopyRightID", idOfEventCopyRight);
        System.setProperty("holdername", holdername);
    }

    @Test(priority = 1, description = "EVENT COPYRIGHT DETAILS")
    public void getEventCopyRightDetails() {
        String EventCopyRightID = System.getProperty("EventCopyRightID");
        String apiData = String.format(getPropertyByKey("get.event.copyright.details"), EventCopyRightID);
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_OK, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_OK));
        verifyResponseFromJsonPath(response, "$.data.attributes.holder", System.getProperty("holdername"));
    }

    @Test(priority = 2, description = "Update a single event copyright by ID")
    public void updateEventCopyRight() {
        String holdername = RandomStringUtils.randomAlphabetic(8);
        String idOfEventCopyRight = System.getProperty("EventCopyRightID");

        String apiData = String.format(getPropertyByKey("patch.event.copyright.by.id"), idOfEventCopyRight, holdername, idOfEventCopyRight);
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_OK, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_OK));

        verifyResponseFromJsonPath(response, "$.data.attributes.holder", holdername);
        System.setProperty("holdername", holdername);
    }

    @Test(priority = 3, description = "Delete a single event copyright.")
    public void deleteEventCopyRight() {
        String idOfEventCopyRight = System.getProperty("EventCopyRightID");
        String apiData = String.format(getPropertyByKey("delete.event.copyright"), idOfEventCopyRight);
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_OK, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_OK));
        verifyResponseFromJsonPath(response, "$.meta.message", "Object successfully deleted");
    }


    public static void createEvent() {
        Response response = restRequest("post.create.event.body");
        verifyResponseStatusCode(STATUSCODE_CREATED, response);
        verifyResponseStatus(RESPONSE_CREATED, response);
        String idOfLocation = getValueFromJsonPath(response, "$.data.id");
        System.setProperty("event-id", idOfLocation);
    }
}
