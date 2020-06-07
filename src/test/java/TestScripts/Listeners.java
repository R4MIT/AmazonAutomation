package TestScripts;

import Utilities.BaseClass;
import Utilities.ExtentReportNG;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.ExceptionInfo;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.sqlite.date.ExceptionUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class Listeners extends BaseClass implements ITestListener {
    ExtentReports extent = ExtentReportNG.getReportObject();
    ExtentTest test;


    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;
        String testMethodName = result.getMethod().getMethodName();
        try {
          driver  = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
//        test.addScreenCaptureFromPath(getScreenshotPath(testMethodName,driver));
//        test.fail(MediaEntityBuilder.createScreenCaptureFromPath(getScreenshotPath(testMethodName,driver)));
        test.fail(result.getThrowable());
        test.log(Status.FAIL, "Test Failed");
//        test.getStatus().valueOf(testMethodName);
        getScreenshotPath(testMethodName,driver);

    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onTestFailedWithTimeout(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {
        extent.flush();

    }
}

