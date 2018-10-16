package com.sidenis.timemaster.common.rest;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

//Daria
public class VacationController extends TimeMasterController {

    protected final static RequestSpecification rs = given().basePath("/api/").contentType(ContentType.JSON);

    public Response getVacationBalance(Map<String, String> cookies) {
        return rs
                .cookies(cookies)
                .when()
                .get("vacation/balance")
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getVacationBalanceByID(Map<String, String> cookies, int ID) {
        return rs
                .cookies(cookies)
                .when()
                .get("vacation/balance/" + ID)
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getVacationBalanceByYearMonth(Map<String, String> cookies, int year, int month) {
        return rs
                .cookies(cookies)
                .when()
                .get("vacation/current_user/" + year + "/" + month)
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response getVacationBalanceByIDYearMonth(Map<String, String> cookies, int ID, int year, int month) {
        return rs
                .cookies(cookies)
                .when()
                .get("vacation/" + ID + "/" + year + "/" + month)
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }


}
