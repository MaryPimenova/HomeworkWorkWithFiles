package ru.maruspim;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FileDownloadTest {

    @Test
    void downloadTest(){

        open("https://github.com/qa-guru/niffler/blob/master/README.md");
        $("a[href*='/qa-guru/niffler/raw/master/README.md']").download();
    }
}
