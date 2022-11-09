import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import dev.failsafe.internal.util.Assert;
public class WesternUnionTask2Test {
	private WebDriver driver;
	@Before
	public void setUp() {   
		System.setProperty("webdriver.chrome.driver",
			"./src/test/resources/chromedriver/chromedriver.exe");
		 System.setProperty("webdriver.chrome.whitelistedIps", "");
		driver = new ChromeDriver();
		driver.manage().window().maximize(); 
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.westernunion.com/lt/en/home.html");
	} 
	@Test
	public void testTask1 (){
		//close tooltip, must wait to ensure the whole page is loaded.
		WebElement tip = driver.findElement(By.id("evergage-tooltip-ambzBhez"));
		tip.findElement(By.cssSelector("a[title=\"Close Message\"]")).click();
		// select menu item to go page.
		WebElement subMenu = driver.findElement(
				By.cssSelector("header nav ul li:nth-child(3)"));
		Actions actions = new Actions(driver);
		actions.moveToElement(subMenu);
		actions.click().build().perform();
		// change default content to iframe.
		WebElement iframe = driver.findElement(
				By.cssSelector("#responsive-template iframe"));
		driver.switchTo().frame(iframe);
		// put zipcode in search input
		driver.findElement(By.cssSelector("form input[type=search]:nth-child(2)"))
		.sendKeys("08247");
		driver.findElement(By.cssSelector("form + div > button")).click();
		// change iframe to defaultcontent
		driver.switchTo().defaultContent();
		// change defaultcontent to iframe
		WebElement locationIFrame = driver.findElement(By.cssSelector(".findlocationsiframe iframe"));
		driver.switchTo().frame(locationIFrame);
		//select first item all locations
		List<WebElement> locations = 
		driver.findElements(By.cssSelector("ol[class^=\"wu_Results_ResultList\"] li:first-of-type div[class^=\"wu_LocationCard_AddressLine\"]"));
		boolean emptyString = false;
		// get two locations from item and create a loop to print/.
		for(WebElement e : locations) {
			String innerText = e.getAttribute("innerText");
			System.out.println(e.getAttribute("innerText"));
			// evaluate if a location is empty, if empty, fail test.
			if(innerText.length()==0 && !emptyString) {
				emptyString = true;
			}
		}
		//send accepted state to test.
		assertTrue(locations.size()==2 && !emptyString);
	}
	@After
	public void tearDown() {
		//driver.quit();
	}
	
}
