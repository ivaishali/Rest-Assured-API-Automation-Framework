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
import static com.automation.utils.RequestUtils.restRequestForBodyUpdate;

public class MicroLocations {

    @BeforeClass
    public void beforeClassMethod() {
        loadPropertiesAndLoginUser();
    }

    @Test(priority = 0, description = "Create a new microlocation using an event_id.")
    public void postCreateMicroLocations() {
        String microLocationName = RandomStringUtils.randomAlphanumeric(5);
        String latitude = RandomStringUtils.randomNumeric(1);
        String longitude = RandomStringUtils.randomNumeric(1);
        String floor = RandomStringUtils.randomNumeric(2);
        String room = RandomStringUtils.randomNumeric(1);

        String apiData = String.format(getPropertyByKey("post.create.micro.location"), microLocationName, latitude, longitude, floor, room);
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_CREATED, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_CREATED));

        verifyResponseFromJsonPath(response, "$.data.attributes.name", microLocationName);
        verifyResponseFromJsonPath(response, "$.data.attributes.latitude", latitude);
        verifyResponseFromJsonPath(response, "$.data.attributes.longitude", longitude);
        verifyResponseFromJsonPath(response, "$.data.attributes.floor", floor);
        verifyResponseFromJsonPath(response, "$.data.attributes.room", room);

        String idOfLocation = getValueFromJsonPath(response, "$.data.id");
        System.setProperty("Microlocation-id", idOfLocation);
    }


    @Test(priority = 1, description = "Delete a single microlocation")
    public void deleteMicroLocation() {
        String apiData = String.format(getPropertyByKey("delete.micro.location"), System.getProperty("Microlocation-id"));
        Response response = restRequestForBodyUpdate(apiData);
        verifyResponseStatus(RESPONSE_OK, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_OK));

        verifyResponseFromJsonPath(response, "$.meta.message", "Object successfully deleted");
    }
}
