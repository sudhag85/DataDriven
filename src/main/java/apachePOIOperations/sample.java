package apachePOIOperations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class sample {
	static WebDriver driver;
	static List<InputTest> inputlist = new ArrayList<InputTest>();
	static List<Double> ExpectedOutputlist = new ArrayList<Double>();
	@DataProvider(name="login")
	public static Object[][] getData() throws IOException {
		Object[][] object = null;
		String Path = "C:\\Sudha\\TestData\\CalData.xlsx";
		FileInputStream fis = new FileInputStream(Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		int rCount = sheet.getLastRowNum();
		int cCount = sheet.getRow(0).getLastCellNum();
		System.out.println("Row Count : " + rCount + "Column Count: "+ cCount);
		//Initialize 2 dimensional array
		object = new Object[rCount][cCount];

		Iterator<Row> rowIterator =sheet.iterator();
		rowIterator.next();
		while(rowIterator.hasNext()) {
			Row rowvalue =rowIterator.next();
			Iterator<Cell> cellIterator = rowvalue.iterator();
			
			while(cellIterator.hasNext()) {
				InputTest obj = new InputTest();
				
				obj.Principal = (int)cellIterator.next().getNumericCellValue();
				obj.RateOfInterest = (int)cellIterator.next().getNumericCellValue();
				obj.Period = (int)cellIterator.next().getNumericCellValue();
				obj.Duration = cellIterator.next().getStringCellValue();
				obj.Frequency = cellIterator.next().getStringCellValue();
				inputlist.add(obj);
				System.out.println(inputlist);
				ExpectedOutputlist.add(cellIterator.next().getNumericCellValue());			
				System.out.println(ExpectedOutputlist);
				
			}
			
		}
		
	
	
/*		Object[][] object = {
				{inputlist.get(0),ExpectedOutputlist.get(0)},
				{inputlist.get(1),ExpectedOutputlist.get(1)},
				{inputlist.get(2),ExpectedOutputlist.get(2)},
				{inputlist.get(3),ExpectedOutputlist.get(3)},
		};*/
		return object;
	}

	@Test(dataProvider="login")
//	public void login(List<InputTest> inputlist, List<Double> ExpectedOutputlist) throws IOException {
	public void login(Object obj1, Object obj2) throws IOException {
		InputTest inputlist = (InputTest) obj1;
		Double ExpectedOutputlist = (Double) obj2;
		driver.findElement(By.id("principal")).sendKeys(String.valueOf(inputlist.Principal));
		driver.findElement(By.id("interest")).sendKeys(String.valueOf(inputlist.RateOfInterest));
		driver.findElement(By.id("tenure")).sendKeys(String.valueOf(inputlist.Period));
		WebElement TenturePeriod = driver.findElement(By.id("tenurePeriod"));
		Select tp = new Select(TenturePeriod);
		tp.selectByVisibleText(inputlist.Duration);
		WebElement Freq = driver.findElement(By.id("frequency"));
		Select fq = new Select(Freq);
		fq.selectByVisibleText(inputlist.Frequency);
		driver.findElement(By.xpath("//*[@id=\"fdMatVal\"]/div[2]/a[1]/img")).click();
		String M_Value = driver.findElement(By.xpath("//*[@id=\"resp_matval\"]/strong")).getText();
		if(Double.parseDouble(M_Value)==ExpectedOutputlist)
		{
			Assert.assertTrue(true, "Actual and Expected Maturity value is equal");
		}
		else {
			Assert.assertTrue(false, "Actual and Expected Maturity value is equal");
		}		
		driver.findElement(By.xpath("//*[@id=\"fdMatVal\"]/div[2]/a[2]/img")).click();		
	
	}
	
	@BeforeClass
	public void executeTest(){
		System.setProperty("webdriver.chrome.driver", "C:\\Sudha\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.moneycontrol.com/fixed-income/calculator/state-bank-of-india-sbi/fixed-deposit-calculator-SBI-BSB001.html");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void tearDown() {
		driver.close();
	}
}
