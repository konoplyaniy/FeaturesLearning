package labmda.com;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    private static final String DEFAULT_FILE_PATH = "C:\\Users\\Светулька\\IdeaProjects\\FeaturesLearning\\test_data.xlsx";
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
                System.out.println("Not valid file name, it should have \".xlsx\" extension");
                System.out.println("Will be used default file: " + DEFAULT_FILE_PATH);
            }
        } else {
            System.out.println("Program running without any parameters!");
            System.out.println("Will be used default file: " + DEFAULT_FILE_PATH);
            excelWriter.writeToFile(DEFAULT_FILE_PATH);
        }
    }

    private void writeToFile(String filePath) {
        try {
            double start = System.currentTimeMillis();
            System.out.println("Open stream and write to file");
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("Created file: " + file.getAbsolutePath());
            }
            FileOutputStream outputStream = new FileOutputStream(filePath);
            currentBook.write(outputStream);
            currentBook.close();
            System.out.println("duration: " + (System.currentTimeMillis() - start) + " in ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
