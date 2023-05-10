package ru.maruspim.parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FileParsingTest {

    private ClassLoader cl = FileParsingTest.class.getClassLoader(); // получение информации о classLoader

    @Test
    void pdfParseTest() throws Exception {

        open("https://junit.org/junit5/docs/current/user-guide/");
        File download = $("a[href*='junit-user-guide-5.9.3.pdf']").download();
        PDF pdf = new PDF(download);
        System.out.println();
        Assertions.assertEquals(
                "Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein",
                pdf.author);

    }

    @Test
    void xlsParseTest() throws Exception {

        open("https://excelvba.ru/programmes/Teachers");
        File download = $("a[href*='teachers.xls']").download();
        XLS xls = new XLS(download);
        Assertions.assertTrue(
                xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue()
                        .startsWith("1. Суммарное количество часов планируемое на штатную по всем разделам плана"));

    }

    @Test
    void csvParseTest() throws Exception {

        try (InputStream is = cl.getResourceAsStream("qaguru19.csv"); // 2 ресурса, подлежащих закрытию при окончании работы программы
             InputStreamReader isr = new InputStreamReader(is)) {
            CSVReader csvReader = new CSVReader(isr);
            List<String[]> content = csvReader.readAll();
            Assertions.assertArrayEquals(new String[]{"Тучс", "Junit5"}, content.get(1));
        }
    }
    @Test
    void zipParseTest() throws Exception {

        try (InputStream is = cl.getResourceAsStream("sample.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                Assertions.assertTrue(entry.getName().contains("sample.txt"));
            }

        }
    }

    @Test
    void jsonTest() throws Exception {

    }
}