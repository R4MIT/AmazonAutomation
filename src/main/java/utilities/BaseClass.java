package utilities;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import pageObjects.HomePage;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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

    @DataProvider(name = "validEmail")
    public Object[][] getDataForEmailOnly() {
        Object[][] data = new Object[1][1];
        data[0][0] = "UserId";
        return data;
//        return new Object[][] {{"UserId"}};
    }

    @DataProvider(name = "inValidPassword")
    public Object[][] getDataForValidEmailInvalidPassword() {
        return readDataFromExcel();
       /* return new Object[][]{
                {"ramitsalotra@gmail.com", "password2.0"}

        };*/

//        ArrayList d = this.readExcelData( "SignInWithInValidEmail" , driver);
//        System.out.println(d.get(0));
      /*  Object[][] data = new Object[1][2];
        data[0][0] = properties.getProperty("validEmail1");
        data[0][1] = properties.getProperty("inValidPassword");
        return data;*/
    }

    @DataProvider(name = "validCredentials")
    public Object[][] getDataForValidCredentials() {
        return new Object[][]{{"validEmail2", "validPassword"}};
       /* Object[][] data = new Object[1][2];
        data[0][0] = properties.getProperty("validEmail2");
        data[0][1]= properties.getProperty("validPassword");
        return data;*/
    }

    public String getScreenshotPath(String testMethodName, WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        String destinationFile = "./ExtentReports/ScreenShots" + testMethodName + ".png";
        File source = ts.getScreenshotAs(OutputType.FILE);
        File path = new File(destinationFile);
        try {
            FileUtils.copyFile(source, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destinationFile;
    }
    public String getBase64ScreenshotPath(String testCaseName, WebDriver driver){
        String encodedBase64 = null;
        TakesScreenshot ts = (TakesScreenshot)driver;
        String destinationFilePath =System.getProperty("user.dir")+"./ExtentReports/ScreenShots/" + testCaseName + ".png";
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destinationFile=new File(destinationFilePath);
        try {
            FileUtils.copyFile(source, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream=new FileInputStream(destinationFile);
            byte[] bytes =new byte[(int)destinationFile.length()];
            fileInputStream.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/png;base64,"+encodedBase64;
    }
//    @DataProvider (name = "excelData")

    public ArrayList readExcelData(String testCaseName, WebDriver driver) {
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
        for (int i = 0; i < sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("TestData")) ;
            XSSFSheet sheet = workbook.getSheetAt(i);  // Sheet is collection of Rows
//        Identify the entire TestCases column by scanning the entire 1st row
            Iterator<Row> rows = sheet.iterator();
            Row firstRow = rows.next();                // Row is collection of cells
            Iterator<Cell> cell = firstRow.cellIterator();
            int column;
            int k = 0;
            while (cell.hasNext()) ;
            {
                Cell cvalue = cell.next();
                if (cvalue.getStringCellValue().equalsIgnoreCase("Test Cases")) ;
                {
//                   reaching to the Desired Column
                    column = k;
                }
                k++;
            }
            System.out.println(column);
//            once column is identified, then scan the entire column, to identify the specific row
            while (rows.hasNext()) ;
            {
                Row rvalue = rows.next();
                if (rvalue.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
//                    After we grab specific testcase now, pull all the data of that row and feed it into the test
                    Iterator<Cell> mainCell = rvalue.cellIterator();
                    while (mainCell.hasNext()) {
                        System.out.println(mainCell.next().getStringCellValue());
                        array.add(mainCell.next().getStringCellValue());
                    }
                }
            }
        }
        return array;
    }

    private Object[][] readDataFromExcel(){
        Object testData[][]=null;
        try {
            FileInputStream fileInputStream=new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/TestData.xlsx");
            XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
            XSSFSheet worksheet=workbook.getSheet("Sheet1");
            XSSFRow row=worksheet.getRow(1);
            DataFormatter formatter=new DataFormatter();
            int rowNum = worksheet.getPhysicalNumberOfRows();
            int colNum= row.getLastCellNum();
            testData = new Object[rowNum-1][colNum];
            for(int i=0; i<rowNum-1; i++)
            {
                XSSFRow rowVal= worksheet.getRow(i+1);
                for (int j=0; j<colNum; j++){
                    if(rowVal==null)
                        testData[i][j]= "";
                    else{
                        XSSFCell cell= row.getCell(j);
                        if(cell==null)
                            testData[i][j]= "";
                        else{
                            String value=formatter.formatCellValue(cell);
                            testData[i][j]=value;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testData;
    }


}

