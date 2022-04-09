package com.mail.jobstest18;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class TestBoxTests {

    @BeforeAll
    static void setUp(){
        Configuration.holdBrowserOpen = false;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @Test
    void fillFormTest() {
        String name = "Test Test";
        open("/text-box");
        $("[id=userName]").setValue(name);
        $("[id=userEmail]").setValue("test@test.ru");
        $("[id=currentAddress]").setValue("Street1");
        $("[id=permanentAddress]").setValue("Street2");
        $("[id=submit]").click();
        $("[id=output]").shouldHave(text(name), text("test@test.ru"),
                text("Street1"), text("Street2"));
        $("[id=output] [id=name]").shouldHave(text(name));
        $("[id=output]").$("[id=name]").shouldHave(text(name));
        $("[id=permanentAddress]").shouldHave(text(name));
    }
}
