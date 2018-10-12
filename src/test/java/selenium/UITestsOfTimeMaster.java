package selenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.sidenis.timemaster.common.selenium.extra.*;

import com.sidenis.timemaster.common.selenium.pages.TimeMasterUI;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;

import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.url;
import static com.sidenis.timemaster.common.selenium.extra.ConstantsUI.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


public class UITestsOfTimeMaster extends TimeMasterUI {


    @BeforeClass
    public static void start() {
        //    Configuration.browser = "chrome";
    }

    @After
    public void end() {
        Selenide.close();
        // WebDriverRunner.closeWebDriver();
    }

    //@Test
    public void login_selenium() throws InterruptedException {
        TimeMasterUI page = TimeMasterUI.open(loginURL);
        Thread.sleep(5000);
        page.putLogin(ConstantsUI.userName_AA);
        page.putPassword(ConstantsUI.userPassword_AA);
        page.submit();
        Thread.sleep(3000);
        // assertTrue(driver.getCurrentUrl().equals(ConstantsUI.URL_TimeTracker));


    }

    @Test
    public void login_selenide() {
        Selenide.open(loginURL);
        LOGIN();
        assertTrue(url().equals(ConstantsUI.URL_TimeTracker));
/**
 int k = 3;

 while(true) {
 $(By.xpath("//nav//button[8]")).click();
 sleep(3000);
 $(By.xpath("//input[@id='mat-input-"+ k +"']")).setValue(ConstantsUI.userName_AA);
 $(By.xpath("//input[@id='mat-input-"+ (k + 1) +"']")).setValue(ConstantsUI.userPassword_AA);
 $(By.xpath("//button[@type='submit']")).click();
 System.out.println("ITER----------------------> " + k);
 k = k + 3;
 }
 */

    }

    @Test
    public void TimeTrackerCommentTest() {
        Selenide.open(loginURL);
        LOGIN();

        DayRow("comments").doubleClick();
        $(By.xpath(input_field)).shouldBe(Condition.exist).clear();
        $(By.xpath(input_field)).setValue("123").pressEnter();

        Selenide.refresh();

        assertEquals("123", DayRow("comments").shouldBe(Condition.exist).text());

    }

    @Test
    public void openWorkloadTest() {
        Selenide.open(loginURL);
        LOGIN();

        $(By.xpath(Workload_button)).click();
        assertTrue($(By.xpath("//app-workload-table[1]/mat-form-field[1]/div[1]/div[1]/div[1]/input[1]")).shouldBe(Condition.exist).isDisplayed());
    }

    @Test
    public void settingsRadioTest() {
        /**
         * не получилось вытащить локатор радиобатона
         * или чекнуть его с помощью селенида*/
        Selenide.open(loginURL);
        LOGIN();

        $(By.xpath(Settings_button)).click();
        $(By.xpath(Projects_settings)).shouldBe(Condition.visible);
        assertTrue(url().equals(URL_Settings));


        $(By.xpath(Remember_radiobutton)).shouldBe(Condition.exist).click();

        assertEquals("Remember previous selected", $(By.xpath(CheckRadio)).getText());
        refresh();
        assertEquals("Remember previous selected", $(By.xpath(CheckRadio)).getText());

        $(By.xpath(Always_set_radiobutton)).click();

        assertEquals("Always set:", $(By.xpath(CheckRadio)).getText());
        refresh();
        System.out.println($(By.xpath(CheckRadio)).getText());
        assertEquals("Always set:", $(By.xpath(CheckRadio)).getText());
    }

    @Test
    public void refreshBreakTest() {
        Selenide.open(loginURL);
        LOGIN();

        DayRow("break").shouldBe(Condition.exist).doubleClick();
        try {
            $(By.xpath(input_field)).shouldBe(Condition.exist).clear();
        } catch (InvalidElementStateException e) {
            DayRow("break").shouldBe(Condition.exist).pressEnter();
            $(By.xpath(input_field)).shouldBe(Condition.exist).clear();
        }
        $(By.xpath(input_field)).setValue("00:30").pressEnter();

        Selenide.refresh();

        DayRow("break").shouldBe(Condition.exist).doubleClick();
        $(By.xpath(input_field)).clear();
        $(By.xpath(input_field)).setValue("00:45").pressEnter();

        Selenide.refresh();

        assertEquals("00:45",DayRow("break").shouldBe(Condition.exist).text());
    }

    @Test
    public void refreshWorkHoursTest() {
        Selenide.open(loginURL);
        LOGIN();

        DayRow("work_hours").doubleClick();
        $(By.xpath(input_field)).clear();
        $(By.xpath(input_field)).setValue("00:15 - 00:30").pressEnter();

        refresh();

        DayRow("work_hours").shouldBe(Condition.exist).doubleClick();
        $(By.xpath(input_field)).clear();
        $(By.xpath(input_field)).setValue("01:30 - 02:30").pressEnter();

        assertEquals("01:30 - 02:30", DayRow("work_hours").shouldBe(Condition.exist).text());

        refresh();

        DayRow("break").shouldBe(Condition.exist).doubleClick();
        $(By.xpath(input_field)).clear();
        $(By.xpath(input_field)).setValue("00:30").pressEnter();

        refresh();

        assertEquals("00:30",DayRow("total").text());
    }

    @Test
    public void dayTypeIllnessTest() {
        Selenide.open(loginURL);
        LOGIN();

        DayRow("day_type").doubleClick();
        $(By.xpath(input_field)).clear();
        $(By.xpath(input_field)).setValue("K").pressEnter();

        assertTrue(DayRow("work_hours").text().isEmpty());
        assertTrue(DayRow("break").text().isEmpty());
        assertTrue(DayRow("total").text().equals("08:00"));
    }

    @Test
    public void setTimeButtonTest() {

        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm");


        Selenide.open(loginURL);
        LOGIN();

        DayRow("work_hours").doubleClick();
        $(By.xpath(input_field)).clear();
        $(By.xpath(input_field)).setValue("00:00 - " + formatForDateNow.format(date)).pressEnter();

        $(By.xpath(SetTime_button)).click();

        refresh();

        assertEquals("00:00 - " + formatForDateNow.format(date), DayRow("work_hours").text());


    }

    @Test
    public void changeProjectsTest() {

        Selenide.open(loginURL);
        LOGIN();

        $(By.xpath(Settings_button)).click();
        $(By.xpath(Projects_settings)).shouldBe(Condition.exist);
        assertEquals(URL_Settings, url());


        refresh();
        $(By.xpath(Projects_settings)).shouldBe(Condition.exist).click();

        if ($(By.xpath(Project_tm)).shouldBe(Condition.exist).getAttribute("aria-selected").equals("true")) {
            $(By.xpath(Project_tm)).click();
        }

        $(By.xpath(Project_tm)).pressEscape();
        $(By.xpath(Timetable_button)).click();

        $(By.xpath(SetTime_button)).shouldBe(Condition.exist);

        int i = 0, flag = 0;
        while (!getProjectName(i).equals("NoN")) {
            System.out.println(getProjectName(i));
            if (getProjectName(i).equals("tm")) {
                flag = 1;
                break;
            }
            i++;
        }

        assertEquals(0, flag);

        $(By.xpath(Settings_button)).click();
        $(By.xpath(Projects_settings)).shouldBe(Condition.exist);
        assertEquals(URL_Settings, url());
        refresh();
        $(By.xpath(Projects_settings)).shouldBe(Condition.exist).click(); //тапаем на настройки проектов
        if ($(By.xpath(Project_tm)).shouldBe(Condition.exist).getAttribute("aria-selected").equals("false")) //если тм не выбран (а он должен быть таковым)
        {
            $(By.xpath(Project_tm)).click();

        }

        $(By.xpath(Project_tm)).pressEscape();
        $(By.xpath(Timetable_button)).shouldBe(Condition.exist).click();

        $(By.xpath(SetTime_button)).shouldBe(Condition.exist);
        i = 0;
        flag = 0;
        while (!getProjectName(i).equals("NoN")) {
            System.out.println(getProjectName(i));
            if (getProjectName(i).equals("tm")) {
                flag = 1;
                break;
            }
            i++;
        }

        assertEquals(1, flag);


    }

}
