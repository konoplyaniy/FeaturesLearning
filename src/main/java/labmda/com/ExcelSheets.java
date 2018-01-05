package labmda.com;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static labmda.com.DataGenerator.users;
import static labmda.com.ExcelWriter.log;
import static labmda.com.LambdaTest.*;

class ExcelSheets {
    private XSSFWorkbook currentBook;

    ExcelSheets(XSSFWorkbook currentBook) {
        this.currentBook = currentBook;
    }

    void addIdNameToBook() {
        double start = System.currentTimeMillis();
        log.info("Create sheet ID Name");
        XSSFSheet idNameSheet = currentBook.createSheet("ID + Name ");
        Row row = idNameSheet.createRow(0);
        row.createCell(0).setCellValue("User ID");
        row.createCell(1).setCellValue("User name");
        idNameMap().forEach((id, name) -> {
            Row newRow = idNameSheet.createRow(idNameSheet.getLastRowNum() + 1);
            newRow.createCell(0).setCellValue(id);
            newRow.createCell(1).setCellValue(name);
        });
        log.info("addIdNameToBook duration: (in ms)" + (System.currentTimeMillis() - start));
    }

    void addLongNameUsersToBook() {
        double start = System.currentTimeMillis();
        log.info("Create sheet Valid Users");
        XSSFSheet longNameUsersSheet = currentBook.createSheet("Long name users");
        addRowTitleForUser(longNameUsersSheet);
        longNameUsersList().forEach(user -> addUserAsRow(longNameUsersSheet, user));
        log.info("addLongNameUsersToBook duration(in ms): " + (System.currentTimeMillis() - start));
    }

    void addValidUsersToBook() {
        double start = System.currentTimeMillis();
        log.info("Create sheet Valid Users");
        XSSFSheet validUsersSheet = currentBook.createSheet("Valid users");
        addRowTitleForUser(validUsersSheet);
        validUsersList().forEach(user -> addUserAsRow(validUsersSheet, user));
        log.info("addValidUsersToBook duration(in ms): " + (System.currentTimeMillis() - start));
    }

    void addUsersToBook() {
        double start = System.currentTimeMillis();
        log.info("Create sheet All Users");
        XSSFSheet allUsersSheet = currentBook.createSheet("All Users");
        allUsersSheet.createFreezePane(3, 1);
        allUsersSheet.autoSizeColumn(0);
        allUsersSheet.autoSizeColumn(1);
        allUsersSheet.autoSizeColumn(2);
        log.info("Add titles for columns");
        addRowTitleForUser(allUsersSheet);
        log.info("Generate data for table");
        users.forEach(user -> addUserAsRow(allUsersSheet, user));
        log.info("addUsersToBook duration(in ms): " + (System.currentTimeMillis() - start));
    }

    private void addRowTitleForUser(XSSFSheet sheet) {
        Row titleRow = sheet.createRow(0);
        Cell cell1 = titleRow.createCell(0);
        cell1.setCellValue("ID");
        Cell cell2 = titleRow.createCell(1);
        cell2.setCellValue("NAME");
        Cell cel3 = titleRow.createCell(2);
        cel3.setCellValue("AGE");
    }

    private void addUserAsRow(XSSFSheet currentSheet, User user) {
        Row newRow = currentSheet.createRow(currentSheet.getLastRowNum() + 1);
        Cell idCell = newRow.createCell(0);
        idCell.setCellValue(user.getId());
        idCell.setCellType(CellType.NUMERIC);
        Cell nameCell = newRow.createCell(1);
        nameCell.setCellValue(user.getName());
        Cell ageCell = newRow.createCell(2);
        ageCell.setCellType(CellType.NUMERIC);
        ageCell.setCellValue(user.getAge());
    }
}
