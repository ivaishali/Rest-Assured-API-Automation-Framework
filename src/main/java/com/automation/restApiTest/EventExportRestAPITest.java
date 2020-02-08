package com.automation.restApiTest;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.automation.Data.ResponseCode.STATUSCODE_OK;
import static com.automation.Data.ResponseStatus.RESPONSE_OK;
import static com.automation.restApiTest.CommonRestAPI.loadPropertiesAndLoginUser;
import static com.automation.utils.CommonUtils.verifyResponseFromJsonPath;
import static com.automation.utils.CommonUtils.verifyResponseStatus;
import static com.automation.utils.PropertyUtils.getPropertyByKey;
import static com.automation.utils.RequestUtils.restRequest;
import static com.automation.utils.RequestUtils.restRequestForBodyUpdate;

public class EventExportRestAPITest {

    @BeforeClass
    public void beforeClassMethod() {
        loadPropertiesAndLoginUser();
    }

    @Test(priority = 0, description = "START EVENT EXPORT AS ICAL FILE")
    public void testGetEventExportAsICAL() {
        Response response = restRequest("get.event.export.as.ical.file");
        verifyResponseStatus(RESPONSE_OK, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_OK));
        response.then().body("task_url", Matchers.anything());
    }

    @Test(priority = 1, description = "Update a single user by setting the email, email and/or name")
    public void testUpdateUserDetail() {
        String randomAppenderStr = RandomStringUtils.randomNumeric(4);

        String requestStr = String.format(getPropertyByKey("patch.user.detail.update"), randomAppenderStr, randomAppenderStr);
        Response response = restRequestForBodyUpdate(requestStr);
        verifyResponseStatus(RESPONSE_OK, response);
        response.then().statusCode(Integer.parseInt(STATUSCODE_OK));

        verifyResponseFromJsonPath(response, "$.data.attributes.first-name", randomAppenderStr);
        verifyResponseFromJsonPath(response, "$.data.attributes.last-name", randomAppenderStr);
    }
}
