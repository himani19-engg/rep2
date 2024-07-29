package solRetailIHM.Utilities;

import static java.awt.SystemColor.window;
import static org.apache.commons.lang.ArrayUtils.INDEX_NOT_FOUND;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.indexOfDifference;
import static solRetailIHM.Utilities.BaseClass.driver;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import io.restassured.mapper.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.jackson.JsonProcessingException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

@Listeners(solRetailIHM.Runner.ListenerTest.class)

public class UniversalMethods {
	public static Properties config;
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static String value;
	public static Document document;
	public static String Language = null;
	public static String Country = null;
	public static String Filepath_XML;
	int colNum;
	int rowNum;

	public static Properties LoadConfig() throws IOException {

		config = new Properties();
		InputStream input;
		input = new FileInputStream("config.properties");
		// System.out.println("The input is: "+input);
		config.load(input);
		return config;
	}

	public UniversalMethods() {
		try {
			LoadConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getScreenShot(WebDriver driver, String screenshotName) {
		String dateName;
		String destination = "";
		try {
			dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			// after execution, you could see a folder "FailedTestsScreenshots" under src
			// folder
			destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destination;
	}

	public String ReadCellData(int vRow, int vColumn, String ExcelSheetName, int SheetIndex) {
		// getRowAndColumn(ExcelSheetName);
		value = null; // variable for storing the cell value
		HSSFWorkbook wb = null; // initialize Workbook null
		try {
			InputStream ins = getClass().getResourceAsStream("/com/" + ExcelSheetName + ".xls");
			wb = new HSSFWorkbook(ins);

			HSSFSheet sheet = wb.getSheetAt(SheetIndex); // getting the XSSFSheet object at given index
			HSSFRow row1 = sheet.getRow(0);
			colNum = row1.getLastCellNum();
			// System.out.println("Total Number of Columns in the excel is : " + colNum);
			rowNum = sheet.getLastRowNum() + 1;
			// System.out.println("Total Number of Rows in the excel is : " + rowNum);
			Row row = sheet.getRow(vRow);// returns the logical row
			Cell cell = row.getCell(vColumn); // getting the cell representing the given column
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				value = cell.getStringCellValue();// getting cell value
				System.out.println("Value in excel sheet is: " + value);
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				value = String.valueOf(cell.getNumericCellValue());// getting cell value
				System.out.println("Value in excel sheet is: " + value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return value;
	}

	public void getColRowNum(String ExcelSheetName, int SheetIndex) {
		// getRowAndColumn(ExcelSheetName);
		value = null; // variable for storing the cell value
		HSSFWorkbook wb = null; // initialize Workbook null
		try {
			InputStream ins = getClass().getResourceAsStream("/com/" + ExcelSheetName + ".xls");
			wb = new HSSFWorkbook(ins);

			HSSFSheet sheet = wb.getSheetAt(SheetIndex); // getting the XSSFSheet object at given index
			HSSFRow row1 = sheet.getRow(0);
			colNum = row1.getLastCellNum();
			// System.out.println("Total Number of Columns in the excel is : " + colNum);
			rowNum = sheet.getLastRowNum() + 1;
			// System.out.println("Total Number of Rows in the excel is : " + rowNum);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void getRowAndColumn(String ExcelSheetName) {
		FileInputStream fis = (FileInputStream) Class.class.getClassLoader()
				.getResourceAsStream("/com/" + ExcelSheetName + ".xls");
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheet("Sheet");
			HSSFRow row = sheet.getRow(0);
			int colNum = row.getLastCellNum();
			System.out.println("Total Number of Columns in the excel is : " + colNum);
			int rowNum = sheet.getLastRowNum() + 1;
			System.out.println("Total Number of Rows in the excel is : " + rowNum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	public static boolean isElementPresent(WebDriver driver, By by) {
		boolean check = false;
		try {
			//waitForElementClickable(driver, by, 5);
			for (int k = 0; k <= 3; k++) {
				if (driver.findElements(by).size() == 0) {
					//Thread.sleep(500);
					waitForPageToLoad(driver,2);
					System.out.println("Waiting for element to be present..."+by);
				} else {
					System.out.println("awaited element "+by+" is found");
					check = true;
					highlightElement(driver, by);
					break;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			check = false;
		}
		return check;
	}

	public static boolean isElementDisplayed(WebDriver driver, By by) {
		boolean check = false;
		try {
			//waitForElementClickable(driver, by, 5);
			for (int k = 0; k <= 3; k++) {
				if (driver.findElement(by).isDisplayed()!=true) {
					//Thread.sleep(500);
					waitForPageToLoad(driver,2);
					System.out.println("Waiting for element to be displayed..."+by);
				} else {
					System.out.println("awaited element "+by+" is displayed");
					check = true;
					highlightElement(driver, by);
					break;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			check = false;
		}
		return check;
	}

	public static double findSimilarity(String x, String y) {
		//Using JaroWinklerDistance() method
		if (x == null && y == null) {
			return 1.0;
		}
		if (x == null || y == null) {
			return 0.0;
		}
		return StringUtils.getJaroWinklerDistance(x, y);

		//Using LevenshteinDistance() method
		/*double maxLength = Double.max(x.length(), y.length());
		if (maxLength > 0) {
			// optionally ignore case if needed
			return (maxLength - StringUtils.getLevenshteinDistance(x, y)) / maxLength;
		}
		return 1.0;*/
	}

	public static String differenceInText(String str1, String str2) {
		if (str1 == null) {
			return str2;
		}
		if (str2 == null) {
			return str1;
		}
		int at = indexOfDifference(str1, str2);
		if (at == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str2.substring(at);
	}

	public static boolean isPageScrollable(WebDriver driver){
		String execScript = "return document.documentElement.scrollHeight>document.documentElement.clientHeight;";
		JavascriptExecutor scrollBarPresent = (JavascriptExecutor) driver;
		Boolean isScrollable = (Boolean) (scrollBarPresent.executeScript(execScript));
		return isScrollable;
	}

	public static boolean isElementPresent(WebDriver driver, By by, int MaxTimeOutInSeconds) {
		Boolean bool=false;
		try {
			//waitForElementClickable(driver, by, MaxTimeOutInSeconds);
			for (int k = 0; k <= MaxTimeOutInSeconds; k++) {
				if (driver.findElements(by).size() == 0) {
					//Thread.sleep(500);
					waitForPageToLoad(driver,2);
					System.out.println("Waiting for element to be present..."+by);
				} else {
					System.out.println("awaited element "+by+" is found");
					bool= true;
					highlightElement(driver, by);
					break;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			bool= false;
			System.out.println("awaited element "+by+" is not found");
		}
		return bool;
	}

	public boolean isElementPresent(WebDriver driver, List<WebElement> allEle) {
		boolean bool=false;
		try {
			for (int k = 0; k <= 3; k++) {
				if (allEle.size() == 0) {
					Thread.sleep(500);
					System.out.println("Waiting for elements to be present...");
				} else {
					System.out.println("awaited elements "+allEle+" are found");
					bool= true;
					for(int i=0;i<allEle.size();i++) {
						highlightElement(driver, allEle.get(i));
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	public boolean isElementPresentWithoutWait(WebDriver driver, By by) {
		boolean bool=false;
		try {
			//waitForElementClickable(driver, by, 5);
			if (driver.findElements(by).size() == 0) {
				System.out.println("Element "+by+" is not present...");
				bool= false;
			} else {
				System.out.println("Element "+by+" is present...");
				bool= true;
				highlightElement(driver, by);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	public static boolean isElementPresentShort(WebDriver driver, By by) {
		boolean bool=false;
		try {
			//waitForElementClickable(driver, by, 80);
			List<WebElement> list = driver.findElements(by);
			int size = list.size();
			for (int k = 0; k <= 2; k++) {
				if (size == 0) {
					Thread.sleep(200);
					System.out.println("Waiting for element to be present..."+by);
				} else {
					System.out.println("awaited element "+by+" is found");
					bool= true;
					highlightElement(driver, by);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	public static void waitForPageToLoad(WebDriver driver, int TimeInSeconds) {
		try {
			new WebDriverWait(driver, TimeInSeconds).until(
					webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void waitForElementPresent(WebDriver driver, By by, int time) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
			//Assert.fail("Element not found");
		}
	}

	public static void waitForVisibilityofTextPresent(WebDriver driver, By by, int time, String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(by), text));
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitForInvisibilityofElementsLocated(WebDriver driver, By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void waitForInvisibilityofElementsLocated(WebDriver driver, By by, int MaxTimeInSecionds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, MaxTimeInSecionds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitForElementClickable(WebDriver driver, By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
			//Assert.fail("Element not found");
		}
	}
	
	public static void waitForElementClickable(WebDriver driver, By by, int timeInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			highlightElement(driver, by);
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Element "+by+" could not found to wait further beyond "+timeInSeconds+" seconds");
			//Assert.assertTrue(false,"Element not found");
		}catch (Exception e) {
			e.printStackTrace();
			//Assert.assertTrue(false,"Element not found");
		}
	}

	public void waitUntilElementIsInvisible(WebDriver driver, By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void highlightElement(WebDriver driver, By by){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//use executeScript() method and pass the arguments
		//Here i pass values based on css style. Yellow background color with solid red color border.
		//js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 5px solid red;');", driver.findElement(by));
		js.executeScript("arguments[0].setAttribute('style', 'background:; border: 3px solid red;');", driver.findElement(by));
	}

	public static void highlightElement(WebDriver driver, WebElement element){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//use executeScript() method and pass the arguments
		//Here i pass values based on css style. Yellow background color with solid red color border.
		//js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 5px solid red;');", driver.findElement(by));
		js.executeScript("arguments[0].setAttribute('style', 'background:; border: 3px solid red;');", element);
	}

	public static void	clickElement(WebDriver driver, By by) {
		try {
			//waitForElementClickable(driver, by, 5);
			if (isElementPresent(driver, by)) {
				driver.findElement(by).click();
				Thread.sleep(500);
				System.out.println("Clicked on: "+by);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//Assert.fail("Element not found");
		}
	}

	public void	clickElement(WebDriver driver, List<WebElement> element) {
		try {
			//waitForElementClickable(driver, by, 5);
			if (isElementPresent(driver, element)) {
				//highlightElement(driver, element);
				element.get(0).click();
				System.out.println("Clicked on: "+element);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//Assert.fail("Element not found");
		}
	}
	
	public static void clickElement(WebDriver driver, By by, int TimeinSeconds) {
		try {
			//waitForElementClickable(driver, by, TimeinSeconds);
			if (isElementPresent(driver, by,5)) {
				driver.findElement(by).click();
				System.out.println("Clicked on: "+by);
			}else {
				System.out.print("Expected Element "+by+" Not present");
			}
		} catch (Exception e) {
			System.out.print("Unable to Click on expected Element: "+by);
			e.printStackTrace();
		}
	}

	// Function select an element base on index
	// and return an element
	public static int getRandomElement(List<Integer> list)
	{
		Random rand = new Random();
		return list.get(rand.nextInt(list.size()));
	}

	public static int getListElement(List<Integer> list)
	{

		return list.size();
	}

	public List<WebElement> getListofElements(WebDriver driver,By by)
	{
		List<WebElement> allswitches = driver.findElements(by);
		for(int i=0;i<allswitches.size();i++) {
			highlightElement(driver, allswitches.get(i));
		}
		return allswitches;
	}

	public boolean scrollUpByPixel(int yPixel, By by)
	{
		boolean bool=false;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int y=10;
		for(int i=0;i<=50;i++) {
			System.out.println("Current element to be clicked is: "+by);
			System.out.println("Checking if the Current element Present: "+isElementPresent(driver, by,5));
			// Scroll Up by 10 pixels
			if (isElementPresent(driver, by,5) != true) {
				js.executeScript("window.scrollBy(0," + (-yPixel) + ")", "");
				y=y+y;
			}else{
				bool=true;
				highlightElement(driver, by);
				break;
			}
		}
		return bool;
	}

	public boolean scrollByPixel(int yPixel, By by)
	{
		boolean bool = false;
		if(scrollUpByPixel(yPixel,by)==false) {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			int y = 10;
			for (int i = 0; i <= 50; i++) {
				// Scroll Down by 10 pixels
				if (isElementPresentWithoutWait(driver, by) != true) {
					js.executeScript("window.scrollBy(0," + (yPixel) + ")", "");
					y = y + y;
				} else {
					bool = true;
					highlightElement(driver, by);
					break;
				}
			}
		}
		return bool;
	}

	public static void scrollUp()
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0, -250);");
	}

	public static String getAnyText(WebDriver driver, By by) {
		String text = null;
		try {
			//waitForElementClickable(driver,by,5);
			if (isElementPresent(driver, by,5)) {
				text = driver.findElement(by).getText();
				System.out.println("Run time text is: " + text);
			} else {
				System.out.println("Required element "+by+" is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//Assert.fail("Element not found");
		}
		return text;
	}

	public static String getAnyText(WebDriver driver, By by, int TimeInSecond) throws TimeoutException {
		String text = null;
		try {
			if (isElementPresent(driver, by,TimeInSecond)) {
				highlightElement(driver, by);
				text = driver.findElement(by).getText();
				System.out.println("Run time text is: " + text);
			} else {
				System.out.println("Required element "+by+" is not present");
			}
		}catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Element "+by+" is not available");
			//Assert.fail("Element not found");
		}
		return text;
	}

	public String getInnerHML(WebDriver driver, By by) {
		String text = "";
		try {
			waitForElementPresent(driver, by, 40);
			if (isElementPresent(driver, by)) {
				text = driver.findElement(by).getAttribute("innerHTML");
				System.out.println("Run time text is: " + text);
			} else {
				System.out.println("Required element "+by+" is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public String getInnerText(WebDriver driver, By by) {
		String text = "";
		try {
			waitForElementPresent(driver, by, 40);
			//driver.manage().timeouts().implicitlyWait(320, TimeUnit.SECONDS);
			if (isElementPresent(driver, by)) {
				text = driver.findElement(by).getAttribute("innerText");
				System.out.println("Run time text is: " + text);
			} else {
				System.out.println("Required element "+by+" is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public String getClassValue(WebDriver driver, By by) {
		String classText = "";
		try {
			waitForElementClickable(driver, by, 40);
			if (isElementPresent(driver, by)) {
				classText = driver.findElement(by).getAttribute("class");
				System.out.println("Run time Class is: " + classText);

			} else {
				System.out.println("Required element "+by+" is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classText;
	}

	public String getClassValue(WebDriver driver, By by, int TimeinSeconds) {
		String classText = "";
		try {
			waitForElementClickable(driver, by, TimeinSeconds);
			if (isElementPresent(driver, by)) {
				classText = driver.findElement(by).getAttribute("class");
				System.out.println("Run time Class is: " + classText);

			} else {
				System.out.println("Required element "+by+" is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classText;
	}

	public static int extractNumericFromString(String str) {
		StringBuilder myNumbers = null;
		try {
			myNumbers = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				if (Character.isDigit(str.charAt(i))) {
					myNumbers.append(str.charAt(i));
					// System.out.println(str.charAt(i) + " is a digit.");
				} /*else {
					// System.out.println(str.charAt(i) + " not a digit.");
				}*/
			}
			System.out.println("Your numbers: " + myNumbers);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(myNumbers.toString());
	}

	public static Float extractFloatFromString(String str) {
		StringBuilder myNumbers = null;
		try {
			myNumbers = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				if (Character.isDigit(str.charAt(i)) || String.valueOf(str.charAt(i)).contains(".")) {
					myNumbers.append(str.charAt(i));
					// System.out.println(str.charAt(i) + " is a digit.");
				} /*else {
					// System.out.println(str.charAt(i) + " not a digit.");
				}*/
			}
			System.out.println("Your numbers: " + myNumbers);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Float.parseFloat(myNumbers.toString());
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public void verifyIfTextpresent(WebDriver driver, By by, String expectedString,int TimeInSecond) {
		try {
			waitForElementClickable(driver, by, TimeInSecond);
			SoftAssert sa = new SoftAssert();
			sa.assertEquals(getAnyText(driver, by).contains(expectedString), true);
			sa.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void waitUntilTitleIsVisible(WebDriver driver, String Title, int TimeInSecond) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, TimeInSecond);
			wait. until(ExpectedConditions. titleContains(Title));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void enterData(WebDriver driver, By by, String string) {
		try {
			waitForElementClickable(driver, by, 20);
			if (isElementPresentShort(driver, by)) {
				driver.findElement(by).clear();
				driver.findElement(by).sendKeys(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enterData(WebDriver driver, By by, String string, int TimeInSecond) {
		try {
			waitForElementClickable(driver, by, TimeInSecond);
			if (isElementPresentShort(driver, by)) {
				driver.findElement(by).clear();
				driver.findElement(by).sendKeys(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isElementEnabled(WebDriver driver, By by) {
		try {
			return driver.findElement(by).isEnabled();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		try {
			while (count-- != 0) {
				int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
				builder.append(ALPHA_NUMERIC_STRING.charAt(character));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public void waitForElementTobeVisible(WebDriver driver, By by) {
		try {
			WebDriverWait some_element = new WebDriverWait(driver, 40);
			some_element.until(ExpectedConditions.visibilityOfElementLocated(by));
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void navigateBack(WebDriver driver) {
		try {
			driver.navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int numberOfElements(WebDriver driver, By by) {
		int size = 0;
		try {
			waitForPageToLoad(driver,10);
			waitForElementPresent(driver, by, 25);
			List<WebElement> allElements = driver.findElements(by);
			for(int i=0;i<allElements.size();i++) {
				highlightElement(driver, allElements.get(i));
			}
			size = allElements.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	public void clickElementFromList(WebDriver driver, By by, String str) {
		try {
			waitForElementClickable(driver, by, 40);
			List<WebElement> allElements = driver.findElements(by);
			for (int i = 0; i <= allElements.size(); i++) {
				if (allElements.get(i).getText().contains(str)) {
					highlightElement(driver, by);
					allElements.get(i).click();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get method name using StackTraceElement.getMethodName()
	public static void getMethodNameUsingStackTraceElement() {
		try {
			StackTraceElement[] stackTraceElements = (new Throwable()).getStackTrace();
			System.out.println(
					"Current Method Execution Name Using StackTraceElement - " + stackTraceElements[0].getMethodName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get method name using Thread.currentThread().getStackTrace()
	public static void getMethodNameUsingCurrentThread() {
		try {
			System.out.println("Current Method Execution Name using Current Thread - "
					+ Thread.currentThread().getStackTrace()[1].getMethodName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void waitForUrlContains(String expectedString, WebDriver driver, int specifiedTimeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
			ExpectedCondition<Boolean> urlIsCorrect = arg0 -> driver.getCurrentUrl().contains(expectedString);
			if(urlIsCorrect.equals(true)) {
				wait.until(urlIsCorrect);
			}
		}catch (org.openqa.selenium.TimeoutException e2) {
			e2.printStackTrace();
			//driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void waitForDecodeUrlContains(String expectedString, WebDriver driver, int specifiedTimeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
			String URL = URLDecoder.decode(driver.getCurrentUrl(), "UTF-8");
			System.out.println(URL);
			ExpectedCondition<Boolean> urlIsCorrect = arg0 -> URL.contains(expectedString);
			wait.until(urlIsCorrect);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// driver and screenshotName
	public static String getScreenshot(WebDriver driver, String resultDirectory, String screenshotName) {
		// below line is just to append the date format with the screenshot name to
		// avoid duplicate names
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			// after execution, you could see a folder "FailedTestsScreenshots" under src
			// folder
			String destination = resultDirectory + "/FailedTestsScreenshots/" + screenshotName + dateName + ".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Returns the captured file path
		String ImagePath = "./FailedTestsScreenshots/" + screenshotName + dateName + ".png";
		return ImagePath;
	}

	// Fail with screenshot
	public static void failWithScreenshot(String text, String resultDirectory, WebDriver driver, ExtentReports extent,
			ExtentTest logger) {
		try {
			//logger.log(Status.FAIL, MarkupHelper.createLabel(text, ExtentColor.RED));
			String screenshotPath = UniversalMethods.getScreenshot(driver, resultDirectory, "getCarTitle");
			System.out.println(screenshotPath);
			logger.log(Status.FAIL, text, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ScreenshotNodeSubNOde(String resultDirectory, WebDriver driver, ExtentTest NodeORSubNode
										  ) {
		try {
			//NodeORSubNode.log(Status.FAIL, MarkupHelper.createLabel("Failed test case", ExtentColor.RED));
			String screenshotPath = UniversalMethods.getScreenshot(driver, resultDirectory, "getCarTitle");
			NodeORSubNode.log(Status.FAIL, "FailedScreenShot at the path: "+screenshotPath);
			//System.out.println(screenshotPath);
			//NodeORSubNode.log(Status.FAIL, text, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void failWithScreenshot(String text, String resultDirectory, WebDriver driver, ExtentTest logger) {
		try {
			//logger.log(Status.FAIL, MarkupHelper.createLabel(text, ExtentColor.RED));
			String screenshotPath = UniversalMethods.getScreenshot(driver, resultDirectory, text);
			System.out.println(screenshotPath);
			logger.log(Status.FAIL, text, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void passWithScreenshot(String text, String resultDirectory, WebDriver driver, ExtentTest logger) {
		try {
			//logger.log(Status.FAIL, MarkupHelper.createLabel("Failed test case", ExtentColor.RED));
			String screenshotPath = UniversalMethods.getScreenshot(driver, resultDirectory, "getCarTitle");
			System.out.println(screenshotPath);
			logger.log(Status.PASS, text, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void catchFailDetails(String resultDirectory, ExtentTest nodeORSubNode, WebDriver driver, String description, Exception e) {
		try {
			//NodeORSubNode.log(Status.FAIL, description);
			failWithScreenshot(description, resultDirectory, driver, nodeORSubNode);
			nodeORSubNode.log(Status.FAIL, e.fillInStackTrace());
		}catch (Exception e2){
			e2.printStackTrace();
		}
	}

	// Log with screenshot
	public static void logWithScreenshot(String text, String resultDirectory, WebDriver driver, ExtentReports extent,
			ExtentTest logger) {
		try {
			String screenshotPath = UniversalMethods.getScreenshot(driver, resultDirectory, "getCarTitle");
			logger.log(Status.INFO, text, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void scroling(WebDriver driver, By by) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			try {
				if(isElementPresent(driver,by)){
					driver.findElement(by).click();
					break;
				}else{
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0,50)");
					Thread.sleep(500);
					highlightElement(driver, by);
					break;
				}
			} catch (ElementClickInterceptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void scrollingJS(WebDriver driver, By by) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			try {
				WebElement element = driver.findElement(by);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
				highlightElement(driver, by);
				break;
			} catch (ElementClickInterceptedException e) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,50)");
				highlightElement(driver, by);
				Thread.sleep(500);
			}
		}

	}

	/**
	 * To click web element using JavascriptExecutor
	 * 
	 * @param driver
	 * @param by
	 * @throws InterruptedException
	 */
	public static void clickUsingJS(WebDriver driver, By by) {
		try {
			//waitForElementClickable(driver, by, 10);
			WebElement element = driver.findElement(by);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * To scroll into view web element using JavascriptExecutor
	 * 
	 * @param driver
	 * @param by
	 * @throws InterruptedException
	 */
	public static void scrollIntoView(WebDriver driver, By by) {
		try {
			WebElement element = driver.findElement(by);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", element);
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void scrollIntoViewWithActionClass(WebDriver driver, By by) {
		try {
			WebElement element = driver.findElement(by);
			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.perform();
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void mouseHover(By by) throws InterruptedException {
		//Instantiate Action Class
		Actions actions = new Actions(driver);
		//Retrieve WebElement 'Music' to perform mouse hover
		WebElement element = driver.findElement(by);
		//Mouse hover menuOption 'Music'
		actions.moveToElement(element).perform();
		highlightElement(driver,element);
		System.out.println("Done Mouse hover on : "+by);
	}

	/**
	 * To wait until URL does not contain given string
	 * 
	 * @param expectedString
	 * @param driver
	 * @param specifiedTimeout
	 * @throws UnsupportedEncodingException
	 */
	public static void waitForUrlNotContains(String expectedString, WebDriver driver, int specifiedTimeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
			ExpectedCondition<Boolean> urlIsCorrect = arg0 -> !driver.getCurrentUrl().contains(expectedString);
			wait.until(urlIsCorrect);
			System.out.println(urlIsCorrect);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To get given attribute of web element
	 * 
	 * @param driver
	 * @param by
	 * @param attribute
	 * @return
	 */
	public String getAttributeValue(WebDriver driver, By by, String attribute) {
		String value = "";
		try {
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			if (isElementPresent(driver, by)) {
				value = driver.findElement(by).getAttribute(attribute);
				System.out.println("Run time attribute value is: " + value);

			} else {
				System.out.println("Required element "+by+" is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public String getAttributeValue(WebDriver driver, By by, String attribute, int TimeInSecond) {
		String value = "";
		try {
			driver.manage().timeouts().implicitlyWait(TimeInSecond, TimeUnit.SECONDS);
			if (isElementPresent(driver, by)) {
				value = driver.findElement(by).getAttribute(attribute);
				System.out.println("Run time attribute value is: " + value);

			} else {
				System.out.println("Required element "+by+" is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * To scroll to the top of page
	 * 
	 * @param driver
	 */
	public static void scrollToTop(WebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
			Thread.sleep(2000);
			System.out.println("Scrolled to top");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To scroll to the bottom of page
	 * 
	 * @param driver
	 */
	public void scrollToBottom(WebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To get HTTP response code
	 * 
	 * @param driver
	 * @param value  - url
	 * @return
	 */
	public int getHTTPResponseCode(WebDriver driver, String value) {
		int code = 0;
		try {
			URL url = new URL(value);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			code = connection.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * To open new tab in browser window
	 * 
	 * @param driver
	 */
	public void openNewTab(WebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("window.open()");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To generate random number
	 * 
	 * @return
	 */
	public static int generateRandomNumber() {
		int random = (int) (Math.random() * 999999999 + 10);
		return random;
	}

	/**
	 * To write any temp value in Properties file
	 * 
	 * @param value
	 */
	public void writeToProperties(String value) {
		Properties prop = new Properties();
		String file = "src/test/java/solRetailIHM/Utilities/Temp.properties";
		try {
			InputStream in = new FileInputStream(file);
			prop.load(in);
			prop.setProperty("Temp", value);
			prop.store(new FileOutputStream(file), "Properties");
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To write any temp value for given key in Properties file
	 * 
	 * @param key
	 * @param value
	 */
	public static void writeToProperties(String key, String value) {
		Properties prop = new Properties();
		String file = "src/test/java/solRetailIHM/Utilities/Temp.properties";
		try {
			InputStream in = new FileInputStream(file);
			prop.load(in);
			prop.setProperty(key, value);
			prop.store(new FileOutputStream(file), "Properties");
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To read value from Properties file
	 * 
	 * @param key
	 * @return
	 */
	public static String readFromProperties(String key) {
		Properties prop = new Properties();
		String file = "src/test/java/solRetailIHM/Utilities/Temp.properties";
		String value = "";
		try {
			InputStream in = new FileInputStream(file);
			prop.load(in);
			value = prop.getProperty(key);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * To select dropdown value using index
	 * 
	 * @param driver
	 * @param by
	 * @param index
	 */
	public void selectDropdownValueByIndex(WebDriver driver, By by, int index) {
		try {
			Select select = new Select(driver.findElement(by));
			highlightElement(driver, by);
			select.selectByIndex(index);
		} catch (Exception e) {

		}
	}

	public void selectDropdownValueByValue(WebDriver driver, By by, String value) {
		try {
			Select select = new Select(driver.findElement(by));
			highlightElement(driver, by);
			select.selectByValue(value);
		} catch (Exception e) {

		}
	}

	public void enterText(WebDriver driver, By by, String string) {
		try {
			waitForElementClickable(driver, by, 40);
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(string);
			highlightElement(driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To accept alert
	 * 
	 * @param driver
	 */
	public void acceptAlert(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To dismiss alert
	 * 
	 * @param driver
	 */
	public void dismissAlert(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To click Enter using Robot class
	 */
	public void clickEnterUsingRobot(String resultDirectory, ExtentTest NodeORSubNode) {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			NodeORSubNode.log(Status.INFO, "Pressed ENTER key from Keyboard");
		} catch (Exception e) {
			catchFailDetails(resultDirectory, NodeORSubNode,driver, "Error with Pressing ENTER key from Keyboard",e);
		}
	}

	public void allowClipBoardPopup(String resultDirectory, ExtentTest NodeORSubNode) {
		try {
			Robot robot = new Robot();
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			NodeORSubNode.log(Status.INFO, "Handled ClipBoard Popup");
		} catch (Exception e) {
			catchFailDetails(resultDirectory, NodeORSubNode, driver, "Error with Pressing ENTER key from Keyboard", e);
		}
	}
		
	public void waitForJavascript(WebDriver driver, int maxWaitMillis, int pollDelimiter) throws Exception {
	    double startTime = System.currentTimeMillis();
	    while (System.currentTimeMillis() < startTime + maxWaitMillis) {
	        String prevState = driver.getPageSource();
	        Thread.sleep(pollDelimiter); // <-- would need to wrap in a try catch
	        if (prevState.equals(driver.getPageSource())) {
	            return;
	        }
	    }
	}

	public List<String> stringSplit(String str, String Splitter,String resultDirectory, ExtentTest NodeORSubNode) throws Exception {
		String strMain = str;
		String[] arrSplit = strMain.split(Splitter);
		List<String> newListString=new ArrayList<String>();
		for (int i=0; i < arrSplit.length; i++)
		{
			System.out.println(arrSplit[i]);
			if(!(arrSplit[i] ==null)) {
				NodeORSubNode.log(Status.PASS, "Text is not empty: "+arrSplit[i].trim());
				newListString.add(arrSplit[i].trim());
			}else{
				NodeORSubNode.log(Status.PASS, "Text is empty: "+arrSplit[i].trim());
			}
		}
		System.out.println(newListString);
		return newListString;
	}
	
	public static void xmlInstantiator(String XMLFilePath) throws Exception {
        System.out.println("only user directory is: " + System.getProperty("user.dir" ));
        System.out.println("user directory with xmlFilePath is: " + System.getProperty("user.dir" ) + XMLFilePath);
        File file = new File(System.getProperty("user.dir") + XMLFilePath);
        //File file = new File(XMLFilePath);
        if (file.exists()) {
            System.out.println("File exists" );
            System.out.println("The whole file is: " + file);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            System.out.println("Document Builder factory is: " + dbf);
            DocumentBuilder db = dbf.newDocumentBuilder();
            System.out.println("Document Builder is: " + db);
            document = db.parse(file);
            //System.out.println("Document is: " + document);
        } else {
            System.err.println("File doesn't exist");
        }
    }

    public static String translationKey(String key, String Country, String Brand) throws Exception {
        String text = null;
		try {
			//System.out.println("Country is: "+ ScenarioMain_ES.Country);
			//System.out.println("Brand is: "+ScenarioMain_ES.Brand);
			Language = config.getProperty("language");
			//Country = Country;
			//BrandName = Brand;
			/*Country = config.getProperty("Country");
			BrandName_Config = config.getProperty("Brand");*/
			// System.out.println("Language from Config File is:---> " + Language);


			Filepath_XML = "\\src\\test\\java\\solRetailIHM\\transKeys\\" + Country + "\\"
					+ Brand + "\\strings.xml";

			System.out.println("File path for string.xml is:---> " + Filepath_XML);
			xmlInstantiator(Filepath_XML);
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xp = xpf.newXPath();
			text = xp.evaluate("//string[@name='" + key + "']", document.getDocumentElement());
			if (text.contains("\\n")) {
				text = text.replace("\\n", " ");
			}
			System.out.println("data/value from relevant xml file for " + key + " is:---->" + text);
		} catch (Exception e) {
            System.err.println("Error with Translation Key");
        }
        System.out.println("Returning element for the key is ......>" + text);
        return text;
    }

	// Function to remove the String
	public static String[] removeStringFromStringArray(String[] arr, int index)
	{
		List<String> list = null;
		if(arr!=null && index>0) {
			list = new ArrayList<String>(Arrays.asList(arr));
			list.remove(index);
		}else{
			System.out.println("Invalid String and/or index is wrong");
		}
		return list.toArray(new String[0]);
	}

}
