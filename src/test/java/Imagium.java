import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Imagium {

    private final static String imagiumServer = "http://10.0.0.2/api/"; //replace with your server

    //Get Project ID
    public static String getUID(String testName, String projectKey, String testMode) {
        try {
            RequestSpecification request = RestAssured.given();
            request.header("content-type", "application/json");
            JSONObject json = new JSONObject();
            json.put("TestName", testName);
            json.put("ProjectKey", projectKey);
            json.put("Mode", testMode);
            request.body(json.toJSONString());
            Response response = request.when().post(imagiumServer + "GetUID");
            int code = response.getStatusCode();
            String response_id = response.getBody().asString();
            System.out.println("TestID: " + response_id);
            return response_id;
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    //Post Base64 Image to Imagium
    public static String postRequest(String stepName, String uid, String imagebase64) {
        RequestSpecification request1 = RestAssured.given();
        request1.header("content-type", "application/json");
        JSONObject jo = new JSONObject();
        jo.put("TestRunID", uid.replace("\"", ""));
        jo.put("StepName", stepName);
        jo.put("ImageBase64", imagebase64);
//        System.out.println("imagebase64:" + imagebase64);
        request1.body(jo.toJSONString());
        Response response1 = request1.when().post(imagiumServer + "Validate");
        String response_id1 = response1.getBody().asString();
        System.out.println("Response: " + response_id1);
        return response_id1;
    }

}
