package com.mail.jobstest18.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.IssueSteps;
import steps.LoginSteps;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class AllureTests {

    Faker faker = new Faker();

    String mail = "test_web_shop@mail.ru",
            password = "Selenium123",
            invalidPassword = faker.number().digits(9),
            invalidMail = faker.internet().emailAddress(),
            errorText = "Login was unsuccessful. Please correct the errors and try again.";

    String search = "jobstest/demoqa-tests-12";

    @DisplayName("Вход в ЛК")
    @Test
    void logIn() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("http://demowebshop.tricentis.com/");
        $(partialLinkText("Log in")).click();
        $("#Email").setValue(mail);
        $("#Password").setValue(password);
        $("input[class='button-1 login-button']").click();
        $(partialLinkText("test_web_shop@mail.ru")).shouldBe(visible);
    }

    @DisplayName("Вход в ЛК c невалидным паролем")
    @Test
    void logInInvalidPassword() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открыть главную страницу", () -> {
            open("http://demowebshop.tricentis.com/");
            }
        );
        step("Нажать на ссылку Log in", () -> {
            $(partialLinkText("Log in")).click();
            }
        );
        step("Заполнить поля Email и Password", () -> {
            $("#Email").setValue(mail);
            $("#Password").setValue(invalidPassword);
            }
        );
        step("Нажать на кнопку Log in", () -> {
            $("input[class='button-1 login-button']").click();
            }
        );
        step("Проверить сообщение об ошибке", () -> {
            $$(".validation-summary-errors").find(Condition.text(errorText)).shouldBe(visible);
            Allure.getLifecycle().addAttachment(
                    "Исходники страницы",
                    "text/html",
                    "html",
                    WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8));
            }
        );
    }

    @DisplayName("Вход в ЛК c невалидной почтой")
    @Test
    void logInInvalidMail() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        LoginSteps steps = new LoginSteps();

        steps.openMainPage();
        steps.clickLinkLogin();
        steps.fillEmailPassword(invalidMail, password);
        steps.clickButtonLogin();
        steps.visibleErrorMessage(errorText);
    }

    @Test
    @Owner("v.parfionov")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Вход в ЛК")
    @Story("Вход в ЛК под валидными и невалидными данными")
    @DisplayName("Вход в ЛК c невалидными почтой и паролем")
    @Link(value = "Главная страница", url = "http://demowebshop.tricentis.com/")
    void logInInvalidMailPassword() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("http://demowebshop.tricentis.com/");
        $(partialLinkText("Log in")).click();
        $("#Email").setValue(invalidMail);
        $("#Password").setValue(invalidPassword);
        $("input[class='button-1 login-button']").click();
        $$(".validation-summary-errors").find(Condition.text(errorText)).shouldBe(visible);
    }

    @Test
    @DisplayName("Проверить наличие вкладки Issue")
    void listenerTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://github.com/");
        $(".header-search-input").click();
        $(".header-search-input").setValue(search);
        $(".header-search-input").submit();
        $(linkText("jobstest/demoqa-tests-12")).click();
        $(partialLinkText("Issue")).shouldBe(visible);
        $(partialLinkText("Issue")).shouldHave(Condition.text("Issue"));
    }

    @Test
    @DisplayName("Проверить наличие вкладки Issue")
    void lambdaTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открыть главную страницу", () -> {
            open("https://github.com/");
            }
        );
        step("Найти репозиторий в поисковой строке ", () -> {
            $(".header-search-input").click();
            $(".header-search-input").setValue(search);
            $(".header-search-input").submit();
            }
        );
        step("Открыть репозиторий", () -> {
            $(linkText("jobstest/demoqa-tests-12")).click();
            }
        );
        step("Проверить наличие вкладки {Issue}", () -> {
            $(partialLinkText("Issue")).shouldBe(visible);
            $(partialLinkText("Issue")).shouldHave(Condition.text("Issue"));
            }
        );
    }

    @Test
    @DisplayName("Проверить наличие вкладки Issue")
    void annotationTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        IssueSteps isteps = new IssueSteps();

        isteps.openMainPage();
        isteps.searchRepo(search);
        isteps.openRepo();
        isteps.checkIssue();
    }
}
