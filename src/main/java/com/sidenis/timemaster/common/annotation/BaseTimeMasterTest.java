package com.sidenis.timemaster.common.annotation;


import com.jayway.restassured.RestAssured;


public class BaseTimeMasterTest extends Constants {
    static{ RestAssured.baseURI = "http://timemaster-dev2.sidenis.io/";}


}

