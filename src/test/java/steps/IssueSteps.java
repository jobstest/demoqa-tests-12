package steps;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class IssueSteps {

    @Step("Открыть главную страницу")
    public void openMainPage() {
        open("https://github.com/");
    }

    @Step("Найти репозиторий в поисковой строке")
    public void searchRepo(String search) {
        $(".header-search-input").click();
        $(".header-search-input").setValue(search);
        $(".header-search-input").submit();
    }

    @Step("Открыть репозиторий")
    public void openRepo() {
        $(linkText("jobstest/demoqa-tests-12")).click();
    }

    @Step("Проверить наличие вкладки {Issue}")
    public void checkIssue() {
        $(partialLinkText("Issue")).shouldBe(visible);
        $(partialLinkText("Issue")).shouldHave(Condition.text("Issue"));
    }
}
