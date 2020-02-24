import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class FreeCRMTest {
	
	
	
	public WebDriver driver;
	public ExtentReports extend;
	public ExtentTest extentTest;
	
	@BeforeTest
	
	public void setExtend() {
		
		extend = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtendReport.html", true);
		extend.addSystemInfo("Host Name", "THSCP10091");
		extend.addSystemInfo("User Name", "Vineethg");
		extend.addSystemInfo("Environment", "QA");
	}
	
	
	@AfterTest
	
	public void EndReport() {
		
		extend.flush();
		extend.close();
		
	}
	
	
	public static String getScreenshot(WebDriver driver, String screenshotname ) throws IOException {
		
		String dateName = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotname + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
		
	}
	
	
	
	@BeforeMethod
	public void setUp() {
		
		
		System.setProperty("webdriver.chrome.driver", "E:\\selenium jar files\\chrome driver\\chromedriver.exe");
		driver = new ChromeDriver();
		
		 driver.manage().window().maximize();
		    
		    // to delete all cache
			
		     driver.manage().deleteAllCookies();
		     
		     driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		     
		     driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		     
		     // to launch url
				
		     driver.get("https://freecrm.co.in/");
	}
	
	
	
	@Test
	public void freeCrmTitleTest() {
		
		extentTest = extend.startTest("freeCrmTitleTest");
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Free CRM #1 cloud software for any business large or small123");
	}
	
	
	
   @AfterMethod
 	
 	public void teardown(ITestResult result) throws IOException {
	   
	   if(result.getStatus()==ITestResult.FAILURE){
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getName()); //to add name in extent report
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getThrowable()); //to add error/exception in extent report
			
			String screenshotPath = FreeCRMTest.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); //to add screenshot in extent report
			//extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath)); //to add screencast/video in extent report
		}
		else if(result.getStatus()==ITestResult.SKIP){
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS){
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

		}
		
		
		extend.endTest(extentTest); //ending test and ends the current test and prepare to create html report
		driver.quit();
 		
 		driver.quit();
 	}

}
