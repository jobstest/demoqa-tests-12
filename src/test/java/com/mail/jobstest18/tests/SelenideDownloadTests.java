package com.mail.jobstest18.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.pdftest.matchers.ContainsExactText;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.MatcherAssert.assertThat;


public class SelenideDownloadTests {

    ClassLoader classLoader = SelenideDownloadTests.class.getClassLoader();
    String path = "src/test/resources/zip/";
    String zipFile = "files.zip";
    String pdfFile = "junit.pdf";
    String xlsFile = "file.xls";
    String csvFile = "test.csv";

    @Test
    void downloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File textFile = $("#raw-url").download();
        try (InputStream is = new FileInputStream(textFile)) {
        byte[] fileContent = is.readAllBytes();
        String strContent = new String(fileContent, StandardCharsets.UTF_8);
        org.assertj.core.api.Assertions.assertThat(strContent).contains("JUnit 5");
            }
    }

    @Test
    void pdfParsingTest() throws Exception {
        InputStream stream = classLoader.getResourceAsStream("pdf/junit-user-guide-5.8.2.pdf");
        PDF pdf = new PDF(stream);
        Assertions.assertEquals(166, pdf.numberOfPages);
        assertThat(pdf, new ContainsExactText("123"));}

    @Test
    void xlsParsingTest() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("xls/file_example_XLS_10.xls");
        XLS xls = new XLS(stream);
        String getStringCellValue = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
        org.assertj.core.api.Assertions.assertThat(getStringCellValue).contains("Dulce");
    }

    @Test
    void csvParsingTest() throws Exception {
        try (InputStream stream = classLoader.getResourceAsStream("csv/teachers.csv");
             CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                List<String[]> content = reader.readAll();
                org.assertj.core.api.Assertions.assertThat(content).contains(
                        new String[]{"Name","Surname"},
                        new String[]{"Test","Testov"},
                        new String[]{"Autotest","Autotestov"});
        }
    }

    @Test
    @DisplayName("Парсинг документов из zip")
    void zipParsingTest() throws Exception {
        try (InputStream inputStream = classLoader.getResourceAsStream("zip/files.zip");
            ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals(pdfFile)) {
                    PDF pdf = new PDF(zipInputStream);
                    Assertions.assertEquals(166, pdf.numberOfPages);
                    Assertions.assertEquals("JUnit 5 User Guide", pdf.title);
                    assertThat(pdf, new ContainsExactText("Table of Contents"));
                } else if (entry.getName().equals(xlsFile)) {
                    XLS xls = new XLS(zipInputStream);
                    String getStringCellValue = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                    org.assertj.core.api.Assertions.assertThat(getStringCellValue).contains("Dulce");
                } else if (entry.getName().equals(csvFile)) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8)); {

                        List<String[]> content = reader.readAll();
                        org.assertj.core.api.Assertions.assertThat(content).contains(
                                new String[]{"Name","Surname"},
                                new String[]{"Test","Testov"},
                                new String[]{"Autotest","Autotestov"});
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Парсинг json")
    void zipJsonParsing() throws Exception {
        Gson gson = new Gson();
        try (InputStream inputStream = classLoader.getResourceAsStream("json/simple.json")) {
             String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            org.assertj.core.api.Assertions.assertThat(jsonObject.get("name").getAsString()).isEqualTo("Test");
            org.assertj.core.api.Assertions.assertThat(jsonObject.get("address").getAsJsonObject().get("street").getAsString()).isEqualTo("Lenina");
        }}
}
