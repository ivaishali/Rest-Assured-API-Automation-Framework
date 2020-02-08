package com.automation.restApiTest;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.automation.Data.ResponseCode.STATUSCODE_OK;
import static com.automation.Data.ResponseStatus.RESPONSE_CREATED;
import static com.automation.Data.ResponseStatus.RESPONSE_OK;
import static com.automation.restApiTest.CommonRestAPI.loadPropertiesAndLoginUser;
import static com.automation.restApiTest.CopyRightAPI.createEvent;
import static com.automation.utils.CommonUtils.*;
import static com.automation.utils.PropertyUtils.getPropertyByKey;
import static com.automation.utils.RequestUtils.restRequestForBodyUpdate;

public class SpeakerAPI {

    @BeforeClass
    public void beforeClass() {
        loadPropertiesAndLoginUser();
        createEvent();
    }

    @Test(priority = 0, description = "Create a Speaker profile")
    public void postCreateSpeakerProfile() {
        String eventId = System.getProperty("event-id");
        String sessionName = RandomStringUtils.randomAlphanumeric(10);

        String apiData = String.format(getPropertyByKey("post.create.speaker.profile"), eventId, sessionName);
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_CREATED, response);
        verifyResponseFromJsonPath(response, "$.data.attributes.name", sessionName);
        String idOfSpeakerProfile = getValueFromJsonPath(response, "$.data.id");
        System.setProperty("idOfSpeakerProfile", idOfSpeakerProfile);
        System.setProperty("sessionName", sessionName);
    }

    @Test(priority = 1, description = "Get a single Speaker profile by ID")
    public void getSpeakerProfileDetailsByID() {
        String idOfSpeakerProfile = System.getProperty("idOfSpeakerProfile");
        String sessionName = System.getProperty("sessionName");
        String apiData = String.format(getPropertyByKey("get.speaker.profile.detail"), idOfSpeakerProfile, sessionName, sessionName);
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_OK, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_OK));
        verifyResponseFromJsonPath(response, "$.data.attributes.name", sessionName);
    }

    @Test(priority = 2, description = "Update a single speaker by id")
    public void updateSpeakerProfileDetailsByID() {
        String sessionName = RandomStringUtils.randomAlphabetic(8);
        String idOfEventCopyRight = System.getProperty("idOfSpeakerProfile");

        String apiData = String.format(getPropertyByKey("patch.update.speaker.profile.detail"), idOfEventCopyRight, sessionName, idOfEventCopyRight);
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_OK, response);

        verifyResponseFromJsonPath(response, "$.data.attributes.name", sessionName);
        System.setProperty("sessionName", sessionName);

    }

    @Test(priority = 3, description = "Delete a single speaker by id")
    public void deleteSpeakerProfileDetailsByID() {
        String idOfSpeakerProfile = System.getProperty("idOfSpeakerProfile");
        String apiData = String.format(getPropertyByKey("delete.sepaker.profile"), idOfSpeakerProfile);
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_OK, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_OK));
        verifyResponseFromJsonPath(response, "$.meta.message", "Object successfully deleted");
    }
}
