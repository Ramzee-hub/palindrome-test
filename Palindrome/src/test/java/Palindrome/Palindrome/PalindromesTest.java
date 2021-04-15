package Palindrome.Palindrome;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;


public class PalindromesTest {


	WebDriver driver = null;

	ExtentReports extent;

	ExtentTest logger;
	ExtentHtmlReporter htmlReporter;

	@BeforeClass(alwaysRun = true)
	public void beforeTest() {
		WebDriverManager.chromedriver().version("90.0.4430.24").setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized"); 
		options.addArguments("enable-automation"); 
		options.addArguments("--no-sandbox"); 
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-browser-side-navigation"); 
		options.addArguments("--disable-gpu"); 

		driver = new ChromeDriver(options); 
		driver.get("https://www.google.com/");	

		htmlReporter = new ExtentHtmlReporter("C:\\Users\\User\\Documents\\seleniumExample\\test.html");

		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Extent Report Demo");
		htmlReporter.config().setReportName("Test Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		logger = extent.createTest("PalindromeTes", "description of test"); 

		driver=new ChromeDriver();

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		//		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);


	}

	@Test
	public void checkPageTitle() {
		driver.get("https://www.google.com/");
		logger.log(Status.INFO, "Opened palindrome site");
		AssertJUnit.assertEquals(driver.getTitle(), "The Palindrome Exercise - Excelon Development");
		logger.log(Status.PASS, "Palindrome site is loaded");
	}

	@Test
	@Parameters({"palString"})
	public void palindromeTest(String palString) {
		driver.findElement(By.id("originalWord")).sendKeys(palString);
		driver.findElement(By.id("button1")).click();
		String strInput = driver.findElement(By.id("originalWord")).getText().trim().toLowerCase();
		int i = 0, j = strInput.length() - 1;
		logger.log(Status.INFO, "Valid string entered");

		while (i < j) {
			if (strInput.charAt(i) != strInput.charAt(j)) {
				logger.log(Status.FAIL, "Input is a not palindrome");
				System.out.println("In loop");

				System.out.println(j);
			   return;}
			i++;
			j--;
		}
		
		if(j == 0) {
			logger.log(Status.FAIL, "Input is a not palindrome");
		}
	}


	@AfterMethod
	public void getResult(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
			logger.fail(result.getThrowable());
		}
		else if(result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
		}
		else {
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
			logger.skip(result.getThrowable());
		}

		extent.flush();

	}


	@AfterClass
	public void tearDown() throws Exception {
		driver.close();
	}
}