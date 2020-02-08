package com.automation.utils;

import com.automation.Data.ContentTypeEnum;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.automation.utils.PropertyUtils.getPropertyByKey;
import static io.restassured.RestAssured.given;

public class RequestUtils {

    static Logger logger = LogManager.getLogger(RequestUtils.class);

    static String requestType, endPoint, contnetType, jsonBody;


    /**
     * This method is used for Rest-GET request and returns Rest response
     *
     * @param endPoint           EX. : /v1/auth/login
     * @param contentType        EX.: ContentTypeEnum.ContentTypes.APPLICATION_VID_JSON
     * @param authorizationToken Ex. JWT jdbehJ7njd89dncd etc.
     * @return
     */
    public static Response getRequest(String endPoint, ContentTypeEnum.ContentTypes contentType, String authorizationToken) {
        String baseURI = getPropertyByKey("base.url");
        String url = baseURI + endPoint;
        Response response = null;
        String access = System.getProperty("access_token");
        try {
            response = RestAssured.given()
                    .header("Content-Type", contentType.getContentTypeString())
                    .header("Authorization", authorizationToken)
                    .when()
                    .get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * This method Can be used for Rest-GET request and returns Rest response
     *
     * @param endPoint    EX. : /v1/auth/login
     * @param contentType EX.: ContentTypeEnum.ContentTypes.APPLICATION_VID_JSON
     * @return
     */
    public static Response getRequest(String endPoint, ContentTypeEnum.ContentTypes contentType) {
        String baseURI = getPropertyByKey("base.url");
        String url = baseURI + endPoint;
        Response response = null;
        String access = System.getProperty("access_token");
        try {
            response = RestAssured.given()
                    .header("Content-Type", contentType)
                    .when()
                    .get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * This method will do POST REst Request and returns Rest Response
     *
     * @param endPoint    : key of end Point
     * @param contentType : ContentType of required API
     * @param body        : Body of POST request
     * @return Example: postRequest("post.login.api.endpoint", ContentTypeEnum.ContentTypes.APPLICATION_VID_JSON, body)
     */
    public static Response postRequest(String endPoint, ContentTypeEnum.ContentTypes contentType, String body) {
        String access_token = String.format(getPropertyByKey("authorization.token"), System.getProperty("access_token"));
        String url = getPropertyByKey("base.url") + getPropertyByKey(endPoint);
        Response response = given()
                .contentType("application/vnd.api+json")
                .header("Authorization", access_token)
                .body(body)
                .when()
                .post(url);
        return response;
    }


    /**
     * This method will parse josn and identify rest request type.  based on that it will create RestRequest
     *
     * @param apiInfo : Here we should pass key of API from .properties file which should contain API INfo json
     * @return : It will return response of provided API Info
     */
    public static Response restRequest(String apiInfo) {
        apiJsonParse(getPropertyByKey(apiInfo));

        String access_token = String.format(getPropertyByKey("authorization.token"), System.getProperty("access_token"));
        String url = getPropertyByKey("base.url") + endPoint;
        Response response = given()
                .contentType(contnetType)
                .accept(contnetType)
                .header("Authorization", access_token)
                .body(jsonBody)
                .when()
                .request(requestType, url);
        return response;
    }


    /**
     * This Method will parse string to json and returns requestType, contnetType and jsonBody from provided string
     *
     * @param jsonString EX. {"endPoint":"/v1/event-types?sort=name","method":"post", "content-type":"application/vnd.api+json","body":{'\"data\"': {'\"attributes\"': {'\"name\"': '\"Eveny by Rest assure\"'},'\"type\"': '\"event-type\"'}}}
     */
    public static void apiJsonParse(String jsonString) {
        DocumentContext jsonContext;
        jsonContext = JsonPath.parse(jsonString);
        requestType = jsonContext.read("$.method").toString();
        contnetType = jsonContext.read("$.content-type").toString();
        System.out.println(contnetType);
        jsonBody = jsonContext.read("$.body").toString().replaceAll("=", ":");
        endPoint = jsonContext.read("$.endPoint").toString();
    }


    public static Response restRequestForBodyUpdate(String apiData) {
        apiJsonParse(apiData);

        String access_token = String.format(getPropertyByKey("authorization.token"), System.getProperty("access_token"));
        String url = getPropertyByKey("base.url") + endPoint;
        Response response = given()
                .contentType(contnetType)
                .accept(contnetType)
                .header("Authorization", access_token)
                .body(jsonBody)
                .when()
                .request(requestType, url);
        return response;
    }

}
