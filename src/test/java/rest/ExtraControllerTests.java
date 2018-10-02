package rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.sidenis.timemaster.common.rest.ExtraController;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.plugin2.message.JavaScriptMemberOpMessage;

import java.util.Map;

import static com.sidenis.timemaster.common.annotation.Constants.userName_AA;
import static com.sidenis.timemaster.common.annotation.Constants.userPassword_AA;
import static junit.framework.TestCase.assertTrue;

public class ExtraControllerTests extends ExtraController {

    @BeforeClass
    public static  void beforeClass() {
        RestAssured.useRelaxedHTTPSValidation();
    }


    static Map<String, String> cookies;
    private Response resp;
    private static int ID = ID_AA;
    private static String name = userName_AA;
    private static String pas = userPassword_AA;

    @Before
    public void login(){
        cookies = login(name, pas);
    }

    @After
    public void logout(){
        logout(cookies);
    }

    // ----------------------> DAY_CODES
    @Test
    public void getDayCodesCurrentTest() {
        resp = getDayCodesCurrent(cookies);
        assertTrue("Some error in getting day codes. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());

    }
    // <---------------------- DAY_CODES

    // ----------------------> HOLIDAYS
    @Test
    public void getHolidaysTest() {
        resp = getHolidays(cookies, 2018, 9);
        assertTrue("Some error in getting holidays. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());
    }
// <---------------------- HOLIDAYS

    // ----------------------> REPORTS
    @Test
    public void getReportsTest() {
        resp = getReports(cookies);
        assertTrue("Some error in getting reports. Status - " + resp.statusLine(),!resp.body().toString().isEmpty());
    }

    @Test
    public void postReportsByIDTest() {
        resp = postReportsByID(cookies, ID, 2018, 7);
        //assertTrue("Some error in getting reports by id. Status - " + resp, !resp.body().toString().isEmpty());
    }

// <---------------------- REPORTS

    // ----------------------> TIME_SHEET
    @Test
    public void getTimeSheetTest() {
        resp = getTimeSheet(cookies, 2018, 8);
        assertTrue("Some error in getting time sheet. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());
    }

    @Test
    public void postTimeSheetTest(){
        resp = postTimeSheet(cookies, "ru", "test");
    }
    // <---------------------- TIME_SHEET

    // ----------------------> TIME_TRACKER
    @Test
    public void getTimeTrackerTest() {
        resp = getTimeTracker(cookies, 2018, 8);
        assertTrue("Some error in getting time tracker. Status - " + resp.statusLine(),!resp.body().toString().isEmpty());
    }
// <---------------------- TIME_TRACKER

    // ----------------------> WHOAMI
    @Test
    public void getWhoAmITest() {
        resp = getWhoAmI(cookies);
        assertTrue("Some error in getting who am i. Status - " + resp.statusLine(), resp.body().as(JSONObject.class).get("uid").toString().equals(name));
    }
// <---------------------- WHOAMI

    // ----------------------> WORKLOAD
    //only superuser
    @Test
    public void getWorkloadAssignmentsTests() {
        resp = getWorkloadAssignments(cookies, 2018, 8);
        assertTrue("Some error in getting workload assignments. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());
    }

    @Test
    public void getWorkloadTimeTrackerTest() {
        resp = getWorkloadTimeTracker(cookies, ID, 2018, 8);
        assertTrue("Some error in getting workload time tracker. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());
    }
// <---------------------- WORKLOAD


}
