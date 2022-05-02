package pages;

import com.codeborne.selenide.SelenideElement;
import pages.components.CalendarComponent;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class RegistrationFormPage {
    CalendarComponent calendar = new CalendarComponent();
    // locators
    SelenideElement firstNameInput = $("#firstName");
    SelenideElement lastNameInput = $("#lastName");
    SelenideElement emailInput = $("#userEmail");
    SelenideElement genderRadioButton = $("#genterWrapper");
    SelenideElement userNumberInput = $("#userNumber");
    SelenideElement dateOfBirthInput = $("#dateOfBirthInput");
    SelenideElement subjectsInput = $("#subjectsInput");
    SelenideElement hobbiesCheckBox = $("#hobbiesWrapper");
    SelenideElement uploadPictureButton = $("#uploadPicture");
    SelenideElement currentAddressInput = $("#currentAddress");
    SelenideElement stateCityBlock = $("#stateCity-wrapper");
    SelenideElement stateDropDownList = $("#state");
    SelenideElement cityDropDownList = $("#city");
    SelenideElement submitButton = $("#submit");
    SelenideElement tableResult = $(".table-responsive");
    // actions
    public RegistrationFormPage openPage() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");

        return this;
    }

    public RegistrationFormPage setFirstName(String fname) {
        firstNameInput.setValue(fname);

        return this;
    }

    public RegistrationFormPage setLastName(String lname) {
        lastNameInput.setValue(lname);

        return this;
    }

    public RegistrationFormPage setEmail(String mail) {
        emailInput.setValue(mail);

        return this;
    }

    public RegistrationFormPage setGender(String gender) {
        genderRadioButton.$(byText(gender)).click();

        return this;
    }

    public RegistrationFormPage setUserNumber(String number) {
        userNumberInput.setValue(number);

        return this;
    }

    public RegistrationFormPage setBirthDate(String day, String month, String year) {
        dateOfBirthInput.click();
        calendar.setDate(day, month, year);

        return this;
    }

    public RegistrationFormPage setSubjects(String subject) {
        subjectsInput.setValue(subject);
        subjectsInput.pressEnter();
        return this;
    }

    public RegistrationFormPage setHobbies(String hobbie) {
        hobbiesCheckBox.$(byText(hobbie)).click();

        return this;
    }

    public RegistrationFormPage uploadPicture(String file) {
        String filePath = "src/test/resources/";
        uploadPictureButton.uploadFile(new File(filePath+file));

        return  this;
    }

    public RegistrationFormPage setCurrentAddress(String address) {
        currentAddressInput.setValue(address);

        return this;
    }

    public RegistrationFormPage setState(String state) {
        stateCityBlock.scrollTo();
        stateDropDownList.click();
        stateDropDownList.$(byText(state)).click();

        return this;
    }

    public RegistrationFormPage setCity(String city) {
        cityDropDownList.click();
        cityDropDownList.$(byText(city)).click();

        return this;
    }

    public RegistrationFormPage setSubmitButton() {
        submitButton.click();

        return this;
    }

    public RegistrationFormPage checkResult(String key, String value) {
        tableResult.$(byText(key)).parent().shouldHave(text(value));

        return this;
    }
}
