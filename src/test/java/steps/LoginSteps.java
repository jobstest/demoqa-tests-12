package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginSteps {

    @Step("Открыть главную страницу")
    public void openMainPage() {
        open("http://demowebshop.tricentis.com/");
    }

    @Step("Нажать на ссылку Log in")
    public void clickLinkLogin() {
        $(By.partialLinkText("Log in")).click();
    }

    @Step("Заполнить поля {Email} и {Password}")
    public void fillEmailPassword(String invalidMail, String password) {
        $("#Email").setValue(invalidMail);
        $("#Password").setValue(password);
    }

    @Step("Нажать на кнопку Log in")
    public void clickButtonLogin() {
        $("input[class='button-1 login-button']").click();
    }

    @Step("Проверить сообщение об ошибке {errorText}")
    public void visibleErrorMessage(String errorText) {
        $$(".validation-summary-errors").find(Condition.text(errorText)).shouldBe(visible);
        attachScreenshot();
    }

    @Attachment(value = "Скриншот", type = "image/png", fileExtension = "png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
