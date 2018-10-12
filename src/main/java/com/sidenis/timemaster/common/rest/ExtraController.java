package com.sidenis.timemaster.common.rest;



import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

//Daria
public class ExtraController extends TimeMasterController {

    protected final static RequestSpecification rs = given().basePath("/api/").contentType(ContentType.JSON);


    public Response getDayCodesCurrent(Map<String, String> cookies) {
        return rs
                .cookies(cookies)
                .when()
                .get("day_codes/current_user")
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getHolidays(Map<String, String> cookies, int year, int month) {
        return rs
                .cookies(cookies)
                .when()
                .get("holidays/current_user/" + year + "/" + month)
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getReports(Map<String, String> cookies) {
        return rs
                .cookies(cookies)
                .when()
                .get("reports/")
                .then()
                .log().all().statusCode(200)
                .extract().response();

    }

    public Response postReportsByID(Map<String, String> cookies, Integer ID, int year, int month) {

        String body = "{\"user_ids\":[\"163\"]}";
        String URL = "http://timemaster-dev2.sidenis.io/api" + "/reports/" + year + "/" + month + "/generate_work_hour_report";
        System.out.println(URL);
        System.out.println("{\"user_ids\":[\"163\"]}");

        return given()
                .header("x-CSRFToken", cookies.get("csrftoken"))
                .baseUri(URL).contentType(ContentType.JSON)
                .cookies(cookies)
                .body(body)
                .when()
                .post()
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getTimeSheet(Map<String, String> cookies, int year, int month) {
        return rs
                .cookies(cookies)
                .when()
                .get("time_sheet/days/current_user/" + year + "/" + month + "/")
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getTimeTracker(Map<String, String> cookies, int year, int month) {
        return rs
                .cookies(cookies)
                .when()
                .get("time_tracker/current_user/" + year + "/" + month + "/")
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getWhoAmI(Map<String, String> cookies) {
        return rs
                .cookies(cookies)
                .when()
                .get("whoami")
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getWorkloadAssignments(Map<String, String> cookies, int year, int month) {
        return rs
                .cookies(cookies)
                .when()
                .get("workload/assignments/total/" + year + "/" + month + "/")
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getWorkloadTimeTracker(Map<String, String> cookies, int id, int year, int month) {
        return rs
                .cookies(cookies)
                .when()
                .get("workload/time_tracker/" + id + "/" + year + "/" + month + "/")
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response postTimeSheet(Map<String, String> cookies, String country, String time_sheet_name){

        JSONObject requestBody = new JSONObject();

        requestBody.put("day_duration", 28800);
        requestBody.put("short_day_duration", 28800);
        requestBody.put("since", "2017-07-27");
        requestBody.put("until", "2020-07-27");


        return given().contentType(ContentType.JSON)
                .header("x-CSRFToken", cookies.get("csrftoken"))
                .cookies(cookies)
                .baseUri("http://timemaster-dev2.sidenis.io/api/time_sheet/"+ country +"/"+time_sheet_name+"/" )
                .body(requestBody)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

    }


}
