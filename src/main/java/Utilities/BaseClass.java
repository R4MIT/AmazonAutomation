package Utilities;

import PageObjects.HomePage;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseClass extends BrowserUtility {

    @BeforeTest
    public void getSignInPage() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.getHomePageLocator());
        homePage.clickSignInButton(driver);
    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

    @DataProvider(name="validEmail")
    public Object[][] getDataForEmailOnly() {
        Object[][] data = new Object[1][1];
        data[0][0] = "UserId";
        return data;
//        return new Object[][] {{"UserId"}};
    }

    @DataProvider(name="inValidPassword")
    public Object[][] getDataForValidEmailInvalidPassword() {
       return new Object[][] {{"ramitsalotra@gmail.com","password2.0"}};

//        ArrayList d = this.readExcelData( "SignInWithInValidEmail" , driver);
//        System.out.println(d.get(0));
      /*  Object[][] data = new Object[1][2];
        data[0][0] = properties.getProperty("validEmail1");
        data[0][1] = properties.getProperty("inValidPassword");
        return data;*/
    }
    @DataProvider (name = "validCredentials")
    public Object[][] getDataForValidCredentials(){
        return new Object[][] {{"validEmail2","validPassword"}};
       /* Object[][] data = new Object[1][2];
        data[0][0] = properties.getProperty("validEmail2");
        data[0][1]= properties.getProperty("validPassword");
        return data;*/
    }

    public void getScreenshotPath(String testMethodName, WebDriver driver){
        TakesScreenshot ts = (TakesScreenshot)driver;
        String destinationFile = "./ExtentReports/"+testMethodName+".png";
        File source = ts.getScreenshotAs(OutputType.FILE);
        File path = new File(destinationFile);
        try {
            FileUtils.copyFile(source, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return destinationFile;
    }
//    @DataProvider (name = "excelData")

    public ArrayList readExcelData(String testCaseName, WebDriver driver){
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

