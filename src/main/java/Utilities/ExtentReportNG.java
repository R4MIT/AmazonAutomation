package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {

   static ExtentReports extent;
    public  static ExtentReports getReportObject(){
        String path = System.getProperty("user.dir")+"\\ExtentReports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Amazon Automation Results");
        reporter.config().setDocumentTitle("TestReports");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester","Ramit");
        return extent;

    }

}
