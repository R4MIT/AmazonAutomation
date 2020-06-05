package Utilities;

import PageObjects.HomePage;
import org.apache.commons.io.FileUtils;
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
import java.util.Properties;

public class BaseClass extends BrowserUtility {

    public WebDriver setBrowserAndUrl() {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("C:\\GitHub_AmazonAutomation\\AmazonAutomation\\src\\main\\java\\Config\\Config.Properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebDriver driver = getBrowserInstance(properties.getProperty("browser"));
        driver.get(properties.getProperty("url"));
        return driver;
    }

    @BeforeTest
    public void getSignInPage() {
        System.out.println("HomePage URL is  " + driver.getCurrentUrl());
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.getHomePageLocator());
        homePage.clickSignInButton(driver);
    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

    @DataProvider(name="ramit")
    public Object[][] getDataForEmailOnly() {
        Object[][] data = new Object[1][1];
        data[0][0] = "UserId";
        return data;
//        return new Object[][] {{"UserId"}};
    }

    @DataProvider(name="invalidPassword")
    public Object[][] getDataForInvalidPassword() {
//       return new Object[][] {{"ramitsalotra@gmail.com","password2.0"}};
        Object[][] data = new Object[1][2];
        data[0][0] = "ramitsalotra@gmail.com" ;
        data[0][1] = "password2.0";
        return data;
    }

    public void getScreenshotPath(String testCaseName, WebDriver driver){
        TakesScreenshot ts = (TakesScreenshot)driver;
        String destinationFile = System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
        File source = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File(destinationFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

