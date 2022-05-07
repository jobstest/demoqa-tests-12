package com.mail.jobstest18.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WebShopTests {

    Faker faker = new Faker();
    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            password = faker.number().digits(6);

    @ValueSource(strings = {
            "Computing and Internet",
            "Elite Desktop PC",
            "14.1-inch Laptop",
            "Camcorder",
            "Smartphone",
            "Blue Jeans",
            "3rd Album",
            "Diamond Pave Earrings",
            "$5 Virtual Gift Card"
    })
    @ParameterizedTest(name = "Поиск товара {0}")
    void searchProductStore(String testData) {
        Selenide.open("http://demowebshop.tricentis.com/");
        $("#small-searchterms").setValue(testData);
        $("input[value='Search']").click();
        $$(".product-grid").find(Condition.text(testData)).shouldBe(visible);
    }

    @CsvSource(value = {
            "@mail.ru, mail.ru",
            "@gmail.com, gmail.com"
    })

    @ParameterizedTest(name = "Регистрация  c полями {0}. Ожидаем результат - {1}")
    void registrationUser(String email, String result) {
        Selenide.open("http://demowebshop.tricentis.com/register");
        $("#gender-male").click();
        $("#FirstName").setValue(firstName);
        $("#LastName").setValue(lastName);
        $("#Email").setValue(firstName + email);
        $("#Password").setValue(password);
        $("#ConfirmPassword").setValue(password);
        $("#register-button").click();
        $$(".header-links-wrapper").find(Condition.text(result)).shouldBe(visible);
    }

    static Stream<Arguments> subcategoriesSource() {
        return Stream.of(
                Arguments.of("Computers", "Desktops"),
                Arguments.of("Electronics", "Cell phones")
        );
    }

    @MethodSource("subcategoriesSource")
    @ParameterizedTest(name = "Проверка подкатегорий - {0} на подкатегории - {1}")
    void subcategoriesSource(String categori, String subcategories) {
        Selenide.open("http://demowebshop.tricentis.com");
        $(".header-menu").$(byText(categori)).hover();
        $$(".master-wrapper-content").find(Condition.text(subcategories)).shouldBe(visible);
    }
}




