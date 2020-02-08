package com.automation.restApiTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static com.automation.Data.ResponseStatus.RESPONSE_OK;
import static com.automation.utils.CommonUtils.verifyResponseStatus;
import static com.automation.utils.PropertyUtils.loadProperties;
import static com.automation.utils.RequestUtils.restRequest;

public class CommonRestAPI {

    public static void loadPropertiesAndLoginUser() {
        loadProperties();
        Response response = restRequest("post.login.user.body");
        // tests
        verifyResponseStatus(RESPONSE_OK, response);

        JsonPath jsonPathEvaluator = response.jsonPath();
        String accessToken = jsonPathEvaluator.get("access_token");
        System.setProperty("access_token", accessToken);
    }
}
