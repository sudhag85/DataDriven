package apachePOIOperations;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class Reporting extends TestListenerAdapter{
	ExtentReports extentReport;
	ExtentHtmlReporter htmlReporter;
	ExtentTest logger;

	public void onStart(ITestContext testContext) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String ReportName= "Test-Report_"+timeStamp+".html";
		extentReport = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/Reports/"+ReportName);
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml/");
		extentReport.attachReporter(htmlReporter);
		extentReport.setSystemInfo("Host Name", "LocalHost");
		extentReport.setSystemInfo("Environment", "QA");
		extentReport.setSystemInfo("User", "Sudha");

		htmlReporter.config().setDocumentTitle("InetBanking Project Reports");//Title of the report
		htmlReporter.config().setReportName("Functional Test Report");//Name of the report
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);//Location of the chart
		htmlReporter.config().setTheme(Theme.DARK);
	}
	
	public void onTestSuccess(ITestResult tr) {
		logger = extentReport.createTest(tr.getName());//Create new entry in the report		
		logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));		
	}
	
	public void onTestFailure(ITestResult tr) {
		logger = extentReport.createTest(tr.getName());
		logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
		String FilePath = System.getProperty("user.dir")+"/Screenshots/"+tr.getName()+".png";
		File f = new File(FilePath);
		if(f.exists()) {
			try {
				logger.fail("ScreenShot is below:"+logger.addScreenCaptureFromPath(FilePath));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void onTestSkipped(ITestResult tr) {
		logger = extentReport.createTest(tr.getName());
		logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(),ExtentColor.ORANGE));		
	}
	
	public void onFinish(ITestContext testContext) {
		extentReport.flush();	
	}
	
}