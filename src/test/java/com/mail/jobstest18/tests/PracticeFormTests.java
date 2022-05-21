package com.mail.jobstest18.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.io.File;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static io.qameta.allure.Allure.step;

@Tag("jenkins")
public class PracticeFormTests extends TestBase{

    @Test
    @DisplayName("Succesfull fill registration test")
    void fillFormTest() {
        step("Open registration form", () -> {
            open("/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
            executeJavaScript("$('footer').remove()");
            executeJavaScript("$('#fixedban').remove()");
        });
        step("Fill registration form", () -> {
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
        });
        step("Verify form data", () -> {
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
        });
    }
}
