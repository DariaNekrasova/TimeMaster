package rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.sidenis.timemaster.common.rest.UsersController;
import net.minidev.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertTrue;

public class UsersControllerTests extends UsersController {


    @BeforeClass
    public static void beforeClass() {
        RestAssured.useRelaxedHTTPSValidation();
    }


    static Map<String, String> cookies;
    private Response resp;
    private int ID = ID_AA;

    @BeforeClass
    public static void login() {
        cookies = login(userName_AA, userPassword_AA);
    }

    @AfterClass
    public static void logout() {
        logout(cookies);
    }


    @Test
    public void getUsersTest() {
        resp = getUsers(cookies, "", "", false);
        assertTrue("Some error in getting users. Status - " + resp.statusLine(),  !resp.body().toString().isEmpty());
    }

    @Test
    public void getUsersTest2() {
        resp = getUsers(cookies, "", "uid", false);
        assertTrue("Some error in getting users. Status - " + resp.statusLine(),  !resp.body().toString().isEmpty());
    }

    @Test
    public void getUsersTest3() {
        resp = getUsers(cookies, "is", "uid", false);
        assertTrue("Some error in getting users. Status - " + resp.statusLine(), !resp.body().toString().isEmpty());
    }

    @Test
    public void getUsersByIDTest() {

        resp = getUsersByID(cookies, 1);

        assertTrue("Some error in getting users by ID. Status - " + resp.statusLine(), !resp.body().as(JSONObject.class).get("id").equals("1"));
    }

    @Test
    public void patchUsersByIDTest(){
        String data = "123";

       // resp = patchUsersByID(cookies,ID, data);
        resp = putUsersByID(cookies, ID, "123");
        assertTrue("Error! " + data + " != " + resp.as(JSONObject.class).get("email")
                ,resp.as(JSONObject.class).get("email").equals("test@test.test" +  data));
    }

  //  @Test
    public void postAddUserTest(){
        resp = postUserAddByAdmin(cookies);
        System.out.println(resp.body());
    }

}
