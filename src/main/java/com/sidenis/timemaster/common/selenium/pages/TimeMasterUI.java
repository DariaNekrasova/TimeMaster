package com.sidenis.timemaster.common.selenium.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.sidenis.timemaster.common.selenium.extra.ConstantsUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.url;
import static com.sidenis.timemaster.common.selenium.extra.ConstantsUI.loginURL;
import static com.sidenis.timemaster.common.selenium.extra.DriverManager.getDriver;
import static junit.framework.TestCase.assertTrue;

public class TimeMasterUI extends HtmlElement {

    public static TimeMasterUI open() {
        getDriver().get(ConstantsUI.URL);
        return new TimeMasterUI();
    }

    public static TimeMasterUI open(String pass) {
        getDriver().get(pass);
        return new TimeMasterUI();
    }

//    public TimeMasterUI() {
//        HtmlElementLoader.populatePageObject(this, getDriver());
//    }


    @FindBy(xpath = "//input[@id='mat-input-0']")
    private WebElement login;

    @FindBy(xpath = "//input[@id='mat-input-1']")
    private WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submit;

    public void putLogin(String name) {
        login.sendKeys(name);
        login.sendKeys(Keys.ENTER);
    }

    public void putPassword(String pas) {
        password.sendKeys(pas);
        password.sendKeys(Keys.ENTER);
    }

    public void submit() {
        submit.click();
    }


    public static void LOGIN() {
        $(By.xpath("//*[@placeholder = 'Login']")).shouldBe(Condition.exist).setValue(ConstantsUI.userName_AA);
        $(By.xpath("//*[@placeholder = 'Password']")).setValue(ConstantsUI.userPassword_AA);
        $(By.xpath("//button[@type='submit']")).click();
        sleep(1000);
    }

    public static void REFRESH() {
        /** потом поменять на
         * Selenide.refresh();*/

        $(By.xpath(Logout_button)).click();
        sleep(3000);
        assertTrue(url().equals(loginURL));
        $(By.xpath("//*[@placeholder = 'Login']")).setValue(ConstantsUI.userName_AA);
        $(By.xpath("//*[@placeholder = 'Password']")).setValue(ConstantsUI.userPassword_AA);
        $(By.xpath("//button[@type='submit']")).click();
    }

    protected static SelenideElement DayRow(String field){
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd");

        switch(field){
            case "day":
                return  $(By.xpath("//tbody// tr[" + formatForDateNow.format(date) + "]//td[1]"));
            case "work_hours":
                return  $(By.xpath("//tbody// tr[" + formatForDateNow.format(date) + "]//td[2]"));
            case "break":
                return  $(By.xpath("//tbody// tr[" + formatForDateNow.format(date) + "]//td[3]"));
            case "total":
                return  $(By.xpath("//tbody// tr[" + formatForDateNow.format(date) + "]//td[4]"));
            case "day_type":
                return  $(By.xpath("//tbody// tr[" + formatForDateNow.format(date) + "]//td[5]"));
            case "comments":
                return  $(By.xpath("//tbody// tr[" + formatForDateNow.format(date) + "]//td[6]"));
            case "extra":
                return  $(By.xpath("//tbody// tr[" + formatForDateNow.format(date) + "]//td[7]"));
                  default:
                      return null;
        }
    }

    protected static SelenideElement DayRow(int num_project){
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd");

        return $(By.xpath("//tbody// tr[" + formatForDateNow.format(date) + "]//td["+ (7 + num_project) +"]"));
    }


    protected static final String Timetable_button = "//span[contains(text(),'Timetable')]";
    protected static final String Workload_button = "//span[contains(text(),'Workload')]";
    protected static final String Settings_button = "//span[contains(text(),'Settings')]";
    protected static final String Reports_button = "//span[contains(text(),'Reports')]";
    protected static final String Logout_button = "//span[contains(text(),'Logout')]";
    protected static final String Admin_button = "//span[contains(text(),'Admin')]]";

    protected static final String SetTime_button = "//*[text()=' SET TIME ']";
    protected static final String input_field = "//textarea[@class='handsontableInput']";

    protected static final String Remember_radiobutton = "//*[text()='Remember previous selected']";
    protected static final String Always_set_radiobutton = "//*[text()='Always set:']";
    protected static final String CheckRadio = "//mat-radio-button[@class='mat-radio-button mat-accent mat-radio-checked']";

    protected static final String Projects_settings = "//mat-select[@formcontrolname='projects']";
    protected static final String Projects_input = "//input[@*='Start to type...']";
    protected static final String Project_tm = "//mat-option[@id='mat-option-1']";

    protected static String getProjectName(int ID){
        if($(By.xpath("//div[@class='ht_clone_top handsontable']//tr//th["+ (8 + ID) +"]")).is(Condition.exist)){
            return $(By.xpath("//div[@class='ht_clone_top handsontable']//tr//th["+ (8 + ID) +"]")).text();
        }
        else
            return "NoN";
    }
}
