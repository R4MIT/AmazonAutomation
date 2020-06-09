package TestScripts;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.BaseClass;

public class ListenersWithBase64 extends BaseClass implements ITestListener {
    ExtentReports extent;
    ExtentTest test;
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
    }
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, MarkupHelper.createLabel(result.getMethod().getMethodName()+ " - Test Case Failed", ExtentColor.RED));
        test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable()+ " - Test Case Failed", ExtentColor.RED));
        WebDriver driver = null;
        String testMethodName = result.getMethod().getMethodName();
        try {
            driver  = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String imagePath= getBase64ScreenshotPath(testMethodName, driver);
//        String imagePath=getScreenshotPath(testMethodName, driver);
        System.out.println("&&&&&&&"+imagePath);
        test.fail("Test Case Failed Snapshot is below\n" +test.addScreenCaptureFromBase64String(imagePath));
//        try {
//            test.fail("Test Case Failed Snapshot is below " + test.addScreenCaptureFromPath(imagePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//            test.fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(imagePath).build());
//            test.log(MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
//        getScreenshotPath(testMethodName, driver );
    }

    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, MarkupHelper.createLabel(result.getMethod().getMethodName() + " - Test Case Skipped", ExtentColor.ORANGE));
    }
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
    public void onTestFailedWithTimeout(ITestResult result) {
    }
    public void onStart(ITestContext context) {
        String path = System.getProperty("user.dir")+"/ExtentReports/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Amazon Automation Results");
        reporter.config().setDocumentTitle("TestReports");
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setEncoding("UTF-8");
        reporter.config().setProtocol(Protocol.HTTPS);
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester","Ramit");
    }
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
