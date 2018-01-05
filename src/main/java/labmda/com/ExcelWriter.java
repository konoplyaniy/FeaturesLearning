package labmda.com;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    static final org.apache.log4j.Logger log = Logger.getLogger(ExcelWriter.class.getName());

    private static final String DEFAULT_FILE_PATH = "C:\\Users\\sergiy.k\\Desktop\\test_data.xlsx";
    private static final XSSFWorkbook currentBook = new XSSFWorkbook();

    public static void main(String[] args) {
        ExcelWriter excelWriter = new ExcelWriter();
        ExcelSheets sheets = new ExcelSheets(currentBook);
        sheets.addUsersToBook();
        sheets.addLongNameUsersToBook();
        sheets.addValidUsersToBook();
        sheets.addIdNameToBook();

        if (args.length > 0) {
            if (args[0].endsWith(".xlsx")) {
                excelWriter.writeToFile(args[0]);
            } else {
                log.info("Not valid file name, it should have \".xlsx\" extension");
                log.info("Will be used default file: " + DEFAULT_FILE_PATH);
            }
        } else {
            log.info("Program running without any parameters!");
            log.info("Will be used default file: " + DEFAULT_FILE_PATH);
            excelWriter.writeToFile(DEFAULT_FILE_PATH);
        }
    }

    private void writeToFile(String filePath) {
        try {
            double start = System.currentTimeMillis();
            log.info("Open stream and write to file");
            File file = new File(filePath);
            if (file.createNewFile()) {
                log.info("Created file: " + file.getAbsolutePath());
            }
            FileOutputStream outputStream = new FileOutputStream(filePath);
            currentBook.write(outputStream);
            currentBook.close();
            log.info("duration: " + (System.currentTimeMillis() - start) + " in ms");
        } catch (IOException e) {
            log.error(e);
        }
    }
}
