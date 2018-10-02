package rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.sidenis.timemaster.common.rest.TimeMasterController;
import com.sidenis.timemaster.common.rest.VacationController;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;

public class VacationControllerTests extends VacationController {

    @BeforeClass
    public static  void beforeClass() {
        RestAssured.useRelaxedHTTPSValidation();
    }


    static Map<String, String> cookies;
    private Response resp;
    private int ID;

    @Before
    public void login(){
        cookies = login(userName_AA, userPassword_AA);
        ID = ID_AA;
    }

    @After
    public void logout(){
        logout(cookies);
    }

    @Test
    public void getVacationBalanceTest() {
        resp = getVacationBalance(cookies);
        assertTrue("Some error in getting vacation balance. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());
    }

    @Test
    public void getVacationBalanceByIDTest() {
        resp = getVacationBalanceByID(cookies, ID);
        assertTrue("Some error in getting vacation balance by id. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());
    }

    @Test
    public void getVacationBalanceByYearMonthTest() { //странная генерация url
        resp = getVacationBalanceByYearMonth(cookies, 2018, 07);
        assertTrue("Some error in getting vacation balance by year, month. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());
    }

    @Test
    public void getVacationBalanceByIDYearMonthTest() {
        resp = getVacationBalanceByIDYearMonth(cookies, ID, 2018, 07);
        assertTrue("Some error in getting vacation balance by id, year and month. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());
    }

}
