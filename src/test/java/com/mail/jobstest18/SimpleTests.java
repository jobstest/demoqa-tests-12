package com.mail.jobstest18;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;

public class SimpleTests {

    @BeforeAll
    static void initDB(){
        System.out.println("### @BeforeAll");
    }

    @BeforeEach
    void openYaPge() {
        System.out.println("### @BeforeEach");
        Selenide.open("https://yandex.ru");
    }

    @AfterEach
    void closeYaPage() {
        System.out.println("### @AfterEach");
        WebDriverRunner.closeWindow();
    }

    @AfterAll
    static void cleanDB(){
        System.out.println("### @AfterAll");
    }

    @Test
    void assertTest1() {
        System.out.println("Test1");
    }

    @Test
    void assertTest2() {
        System.out.println("Test2");
    }

}
