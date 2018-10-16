package com.sidenis.timemaster.common.rest;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

//Daria
public class UsersController extends TimeMasterController {

       protected final static RequestSpecification rs = given().basePath("/api/").contentType(ContentType.JSON);

    public Response getUsers(Map<String, String> cookies, String search, String ordering, boolean is_active) {
        return rs
                .cookies(cookies)
                .param("search", search)
                .param("ordering", ordering)
                .param("is_active", is_active)
                .when()
                .get("users/")
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }


    public Response getUsersByID(Map<String, String> cookies, int ID) {
        return rs
                .cookies(cookies)
                .when()
                .get("users/" + ID)
                .then()
                .log().all().statusCode(200)
                .extract().response();
    }

    public Response postUserAddByAdmin(Map<String, String> cookies){
        //http://timemaster-dev2.sidenis.io/admin/auth/user/add/
        return given().contentType(ContentType.TEXT)
                .baseUri("http://timemaster-dev2.sidenis.io/admin/auth/user/add/")
                .cookies(cookies)
                .when()
                .post("/admin/auth/user/306/change/")
                .then()
                .log().all()
                .statusCode(302)
                .extract().response();
    }

    public Response patchUsersByID(Map<String, String> cookies, int ID, String str){
        JSONObject request = new JSONObject();

        request.put("is_superuser", true);
        request.put("is_staff", true);
        request.put("is_active", true);
        request.put("email", str + "test@test.test");


        return given().baseUri("http://timemaster-dev2.sidenis.io/api/users/" + ID)
                .contentType(ContentType.JSON)
                .header("X-CSRFToken",cookies.get("csrftoken") )
                .cookies(cookies)
                .when()
                .body(request)
                .put()
                .then()
                .statusCode(200)
               // .log().all()
                .extract().response();
    }

    public Response putUsersByID(Map<String, String> cookies, int ID, String str){
        JSONObject request = new JSONObject();

        request.put("is_superuser", true);
        request.put("is_staff", true);
        request.put("is_active", true);
        request.put("email", str);


        return given().baseUri("http://timemaster-dev2.sidenis.io/api/users/" + ID + "/")
                .contentType(ContentType.JSON)
                .header("X-CSRFToken",cookies.get("csrftoken") )
                .cookies(cookies)
                .when()
                .body(request)
                .put()
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }

}
