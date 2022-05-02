package com.mail.jobstest18.tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.RegistrationFormPage;

import static java.lang.String.format;

public class PracticeFormWithTestDataTests {

    RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    Faker faker = new Faker();
    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            userNumber = faker.number().digits(10),
            address = faker.address().fullAddress();
    String day = "11";
    String month = "March";
    String year = "1985";
    String gender = "Male";
    String subject = "English";
    String hobbie = "Reading";
    String file = "Test.png";
    String state = "NCR";
    String city = "Delhi";

    String fullName = format("%s %s", firstName, lastName);
    String stateAndCity = format("%s %s", state, city);
    String birthDate = format("%s %s,%s", day, month, year);

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @Test
    void fillFormTest() {
        registrationFormPage.openPage().
                setFirstName(firstName).
                setLastName(lastName).
                setEmail(email).
                setGender(gender).
                setUserNumber(userNumber).
                setBirthDate(day, month, year).
                setSubjects(subject).
                setHobbies(hobbie).
                uploadPicture(file).
                setCurrentAddress(address).
                setState(state).
                setCity(city).
                setSubmitButton().
                checkResult("Student Name", fullName).
                checkResult("Student Email", email).
                checkResult("Gender", gender).
                checkResult("Mobile", userNumber).
                checkResult("Date of Birth", birthDate).
                checkResult("Subjects", subject).
                checkResult("Hobbies", hobbie).
                checkResult("Picture", file).
                checkResult("Address", address).
                checkResult("State and City", stateAndCity);
    }
}
