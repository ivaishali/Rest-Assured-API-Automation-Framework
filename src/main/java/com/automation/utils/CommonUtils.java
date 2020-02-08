package com.automation.utils;

import com.jayway.jsonpath.DocumentContext;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;

import static org.hamcrest.MatcherAssert.assertThat;


public class CommonUtils {

    static Logger logger = LogManager.getLogger(CommonUtils.class);

    public static void verifyResponseStatus(String status, Response response) {
        assertThat("Assert Response Status", response.getStatusLine(), Matchers.equalToIgnoringCase(status));
    }

    public static void verifyResponseStatusCode(String statusCode, Response response) {
        assertThat("Assert Response Status", String.valueOf(response.getStatusCode()), Matchers.equalToIgnoringCase(statusCode));
    }

    public static void verifyResponseJsonPathValue(Response response, String jsonPathKey, String actualValue) {
        String expectedValue = response.path(jsonPathKey).toString();
        assertThat("Assert Response Status", expectedValue, Matchers.equalToIgnoringCase(actualValue));
    }

    public static void verifyResponseFromJsonPath(Response response, String jsonPathKey, Object actualValue) {
        String jsonPathEvaluator = response.asString();
        DocumentContext jsonContext;
        jsonContext = com.jayway.jsonpath.JsonPath.parse(jsonPathEvaluator);
        String jsonPathValue = jsonContext.read(jsonPathKey).toString();
        if (actualValue instanceof String) {
            actualValue = actualValue.toString();
        }
        assertThat("Assert Response Status", jsonPathValue, Matchers.containsString(actualValue.toString()));
    }

    public static String getValueFromJsonPath(Response response, String jsonPathKey) {
        String jsonPathEvaluator = response.asString();
        DocumentContext jsonContext;
        jsonContext = com.jayway.jsonpath.JsonPath.parse(jsonPathEvaluator);
        return jsonContext.read(jsonPathKey).toString();
    }

}
