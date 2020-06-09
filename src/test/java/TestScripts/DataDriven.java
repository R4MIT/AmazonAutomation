package TestScripts;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataDriven {
    public static ArrayList<String> main(String[] args) {
        String testCaseName = null;
//            public static void readExcelData(String testCaseName, WebDriver driver){
        ArrayList<String> array = new ArrayList<String>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("C:\\XLTestData.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int sheets = workbook.getNumberOfSheets();
        for (int i=0; i<sheets;i++){
            if (workbook.getSheetName(i).equalsIgnoreCase("TestData"));
            XSSFSheet sheet = workbook.getSheetAt(i);  // Sheet is collection of Rows
//        Identify the entire TestCases column by scanning the entire 1st row
            Iterator<Row> rows = sheet.iterator();
            Row firstRow = rows.next();                // Row is collection of cells
            Iterator<Cell> cell = firstRow.cellIterator();
            int column ;
            int k = 0;
            while (cell.hasNext());{
                Cell cvalue = cell.next();
               if (cvalue.getStringCellValue().equalsIgnoreCase("Test Cases"));{
//                   reaching to the Desired Column
                    column = k;
                }
                k++;
            }
            System.out.println(column);
//            once column is identified, then scan the entire column, to identify the specific row
            while (rows.hasNext());{
                Row rvalue = rows.next();
                if (rvalue.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)){
//                    After we grab specific testcase now, pull all the data of that row and feed it into the test
                    Iterator<Cell> mainCell = rvalue.cellIterator();
                    while (mainCell.hasNext()){
                        System.out.println(mainCell.next().getStringCellValue());
                        array.add(mainCell.next().getStringCellValue());
                    }
                }
            }
        }
        return array;
    }

}
