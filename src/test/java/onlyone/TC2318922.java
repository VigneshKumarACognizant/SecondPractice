package onlyone;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class TC2318922 
{

	WebDriver driver;

	@BeforeTest
	@Parameters("Browser")
	public void beforeTest(String br) 
	{
		if(br.equals("Chrome"))
		{

			driver = new ChromeDriver();
			System.out.println("Chrome launced");
		}
		else if(br.equals("Edge")) {
			driver = new EdgeDriver();
			System.out.println("Edge launced");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().deleteAllCookies();
		driver.get("https://be.cognizant.com");
	}
	@Test(priority=-1)
	public void userVerification() throws InterruptedException
	{
		driver.navigate().refresh();
		Thread.sleep(5000);
		JavascriptExecutor js=(JavascriptExecutor)driver;
		WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(20)); 
		Thread.sleep(3000);
		WebElement click_info=myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='O365_MainLink_Me']")));
		js.executeScript("arguments[0].click();",click_info);
		Thread.sleep(5000);
		String cogni = driver.findElement(By.id("mectrl_currentAccount_secondary")).getText();
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(cogni.contains("@cognizant.com"), true," Not A Cognizant User");
		assertion.assertAll();
	}


	@Test(priority = 0)
	public void beCog() throws InterruptedException 
	{
		driver.findElement(By.xpath("//div[contains(@class,'ms-OverflowSet-item item')]//button[@name='Corporate Functions']")).click();
		Thread.sleep(2000);
		Actions action = new Actions(driver);
		action.	moveToElement(driver.findElement(By.xpath("//span[contains(@class,'ms-ContextualMenu-itemText') and text()='Security and Technology']"))).perform();
		driver.findElement(By.xpath("//span[contains(@class,'ms-ContextualMenu-itemText label') and text()='IT']")).click();
		Thread.sleep(1000);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals("Global IT", driver.getTitle(),"Not located to the Global it page");
		assertion.assertAll();
	}

	@Test(priority = 1)
	public void app()
	{
		List <WebElement> list = driver.findElements(By.xpath("//*[@data-automation-id='HeroTitle']"));
		SoftAssert assertion = new SoftAssert();
		int size = list.size();
		assertion.assertEquals(size>0, true,"No Apps found");
		System.out.println("The Apps are:");
		for(WebElement x: list)
			System.out.println(x.getText());
		assertion.assertAll();
	}

	@Test(priority = 2)
	public void news() throws InterruptedException
	{
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector('article>div>div').scrollTop=800");
		Thread.sleep(2000);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(driver.findElement(By.xpath("//div[@data-automation-id='CanvasLayout']/div[2]//span[@role='heading']")).getText(),"IT News","IT News is not there");
		assertion.assertAll();
	}

	@Test(priority = 3)
	public void awards() throws InterruptedException
	{
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector('article>div>div').scrollTop=1900");
		Thread.sleep(2000);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(driver.findElement(By.id("it-awards")).getText().contains("IT Awards"),true,"ItAward is not there");
		assertion.assertAll();
	}

	@Test(priority = 4)
	public void newsTooltip() throws InterruptedException
	{
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector('article>div>div').scrollTop=800");
		List <WebElement> list1 =driver.findElements(By.xpath("//div[@data-theme-emphasis='1']//div[@data-automation-id='BaseCollection-FreshData']//a[@data-automation-id='newsItemTitle']"));
		System.out.println("Total news:"+list1.size());
		int check=0;
		for(WebElement x : list1)
		{
			String actual_msg=x.getText();
			String Tooltip = x.getAttribute("title");
			if(!(actual_msg.equals(Tooltip))) 
			{
				System.out.println(actual_msg);
				check++;
			}
		}
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(check==0,true,"Tool Tip not matched");
		assertion.assertAll();
	}

	@Test(priority = 5)
	public void awardDetails() throws InterruptedException 
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector('article>div>div').scrollTop=2100");
		List<WebElement> awards = driver.findElements(By.xpath("//div[contains(@class, 'itemArea')]"));
		System.out.println("Total awards :"+awards.size());
		for(int i = 1;i<=awards.size();i++)
		{
			Thread.sleep(2000);
			WebElement element=driver.findElement(By.xpath("//div[contains(@class,'itemArea')]["+i+"]//a[@role='presentation']"));
			element.click();
			Thread.sleep(2000);
			List<WebElement> listn= driver.findElements(By.xpath("//div[@id='spPageCanvasContent']//div[@data-automation-id='Canvas']/div/div[1]/div/div/div[2]//p"));
			for(WebElement x:listn)		
				System.out.println(x.getText());
			driver.navigate().back();
			Thread.sleep(2000);
			js.executeScript("document.querySelector('article>div>div').scrollTop=2100");
			System.out.println();
		}
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
