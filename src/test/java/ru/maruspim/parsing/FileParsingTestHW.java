package ru.maruspim.parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.maruspim.modal.AlexNet;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
@DisplayName("Testing of json, pdf, xls and scv files")
public class FileParsingTestHW {
    private ClassLoader cl = FileParsingTestHW.class.getClassLoader(); // получение информации о classLoader
    ObjectMapper objectMapper = new ObjectMapper ();
    private String jsonName = "AlexNet.json";
    private String archiveName = "sampleArchive.zip";
    private String pdfName = "Lucas.pdf";
    private String xlsName = "task14.xls";
    private String csvName = "SampleCSV.csv";

    @Test
    @DisplayName("Testing of .json files")
    void jsonParseTest() throws Exception {

        try (InputStream is = cl.getResourceAsStream(jsonName);
             InputStreamReader isr = new InputStreamReader(is)) {
            AlexNet alexNet = objectMapper.readValue (isr, AlexNet.class);
            Assertions.assertTrue(alexNet.isNonlinear);
            Assertions.assertTrue(alexNet.dropoutUsage);
            Assertions.assertEquals("Sutskever", alexNet.authors.get(1));
            Assertions.assertEquals(5, alexNet.convolutionalLayersNumber);
            Assertions.assertEquals("ReLU", alexNet.activationFunction);
            Assertions.assertEquals(50000, alexNet.dataset.validationSetSize);
        }
    }
    @Test
    @DisplayName("Testing of .pdf files from zip-archive")
    void pdfParseTest() throws Exception {

        try (InputStream is = cl.getResourceAsStream(archiveName);
             ZipInputStream zip = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().equals(pdfName)) {
                    PDF pdf = new PDF(zip);
                    Assertions.assertTrue(pdf.text.contains("Автоматизация. Современные технологии"));
                    Assertions.assertTrue(pdf.text.contains("Том 75"));
                }
            }
        }
    }

    @Test
    @DisplayName("Testing of .xls files from zip-archive")
    void xlsParseTest() throws Exception {

        try (InputStream is = cl.getResourceAsStream(archiveName);
             ZipInputStream zip = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().equals(xlsName)) {
                    XLS xls = new XLS(zip);
                    Assertions.assertEquals(
                            "Фамилия",
                            xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
                    Assertions.assertEquals(
                            "Андреев",
                            xls.excel.getSheetAt(0).getRow(5).getCell(0).getStringCellValue());
                }
            }
        }
    }

    @Test
    @DisplayName("Testing of .csv files from zip-archive")
    void csvParseTest() throws Exception {

        try (InputStream is = cl.getResourceAsStream(archiveName);
             ZipInputStream zip = new ZipInputStream(is);
             InputStreamReader isr = new InputStreamReader(zip)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().equals(csvName)) {
                    CSVReader csvReader = new CSVReader(isr);
                    List<String[]> content = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[]{"Anita", "Jorgensen", "Contoso"}, content.get(1));
                }
            }
        }
    }
}
