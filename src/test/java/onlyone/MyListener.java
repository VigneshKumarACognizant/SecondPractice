package onlyone;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class MyListener implements ITestListener
{
	public ExtentSparkReporter sparkReporter;  // UI of the report
	public ExtentReports extent;  //populate common info on the report
	public ExtentTest test; // creating test case entries in the report and update status of the test methods

	public void onStart(ITestContext context) {
			
		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+ "/myReport.html");//specify location of the report
		
		sparkReporter.config().setDocumentTitle("Automation Report"); // TiTle of report
		sparkReporter.config().setReportName("Functional Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Computer Name","localhost");
		extent.setSystemInfo("Environment","QA");
		extent.setSystemInfo("Tester Name","Vignesh Kumar");
		extent.setSystemInfo("os","Windows11");
		extent.setSystemInfo("Browser name","Chrome,Edge");
					
	}


	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getName()); // create a new enty in the report
		test.log(Status.PASS, "Test case PASSED is:" + result.getName()); // update status p/f/s
		System.out.println(result.getName()+" is passed");
		
	}

	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getName());
		test.log(Status.FAIL, "Test case FAILED is:" + result.getName());
		test.log(Status.FAIL, "Test Case FAILED cause is: " + result.getThrowable()); 
		System.out.println(result.getName()+" is Failed");
	}

	public void onTestSkipped(ITestResult result) {

		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test case SKIPPED is:" + result.getName());
		System.out.println(result.getName()+" is Skipped");
		
	}

	public void onTestStart(ITestResult result) 
	{
		System.out.println(result.getName()+" is Started");
	}
	
	

	public void onFinish(ITestContext context) {
		System.out.println(context.getName()+ " is Done");
		extent.flush();
	}
		
	
}
