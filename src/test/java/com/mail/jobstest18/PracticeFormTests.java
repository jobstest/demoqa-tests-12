package com.mail.jobstest18;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormTests {

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = false;
    }

    @Test
    void fillFormTest() {
        open("/automation-practice-form");
        executeJavaScript("document.querySelector(\"footer\").hidden = 'true';document.querySelector(\"#fixedban\").hidden = 'true'");
        $("#firstName").setValue("Test");
        $("#lastName").setValue("Testov");
        $("#userEmail").setValue("test@mail.ru");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("9876543210");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("March");
        $(".react-datepicker__year-select").selectOption("1985");
        $(".react-datepicker__month").$(byText("11")).click();
        $("#subjectsInput").setValue("English");
        $("#subjectsInput").pressEnter();
        $("#hobbiesWrapper").$(byText("Reading")).click();
        $("#uploadPicture").uploadFile(new File("src/test/resources/Test.png"));
        $("#currentAddress").setValue("University of Cambridge, The Old Schools, Trinity Lane, Cambridge CB2 1TN");
        $("#stateCity-wrapper").scrollTo();
        $("#state").click();
        $(byText("NCR")).click();
        $("#city").click();
        $(byText("Delhi")).click();
        $("#submit").click();
        $(".table-responsive").shouldHave(text("Test"),
                text("Testov"),
                text("test@mail.ru"),
                text("Male"),
                text("9876543210"),
                text("11 March,1985"),
                text("English"),
                text("Reading"),
                text("Test.png"),
                text("University of Cambridge, The Old Schools, Trinity Lane, Cambridge CB2 1TN"),
                text("NCR Delhi"));
    }
}
