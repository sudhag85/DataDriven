package apachePOIOperations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReadDataWithExample {
	FileInputStream f;
	XSSFWorkbook workbook;
	XSSFSheet Sheet;
	int Principal;
	WebDriver driver;
	int RateOfInterest;
	int Period;
	String Duration;
	String Frequency;
	double MaturityValue;
	
	
	public void getExcelFile() throws IOException {
		f = new FileInputStream("C:\\Sudha\\TestData\\CalData.xlsx");
		workbook = new XSSFWorkbook(f);
		Sheet = workbook.getSheetAt(0);
	}
@Test
	public void getExcelData() throws IOException {
		System.setProperty("webdriver.chrome.driver", "C:\\Sudha\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.moneycontrol.com/fixed-income/calculator/state-bank-of-india-sbi/fixed-deposit-calculator-SBI-BSB001.html");
		getExcelFile();
		int rowCount = Sheet.getLastRowNum();
		int columnCount = Sheet.getRow(0).getLastCellNum();
		for(int i=1;i<=rowCount;i++)
		{
			XSSFRow currentRow = Sheet.getRow(i);
			Principal = (int)currentRow.getCell(0).getNumericCellValue();
			RateOfInterest = (int)currentRow.getCell(1).getNumericCellValue();
			Period = (int)currentRow.getCell(2).getNumericCellValue();
			Duration = currentRow.getCell(3).getStringCellValue();
			Frequency = currentRow.getCell(4).getStringCellValue();
			MaturityValue = currentRow.getCell(5).getNumericCellValue();
		
			driver.findElement(By.id("principal")).sendKeys(String.valueOf(Principal));
			driver.findElement(By.id("interest")).sendKeys(String.valueOf(RateOfInterest));
			driver.findElement(By.id("tenure")).sendKeys(String.valueOf(Period));
			WebElement TenturePeriod = driver.findElement(By.id("tenurePeriod"));
			Select tp = new Select(TenturePeriod);
			tp.selectByVisibleText(Duration);
			WebElement Freq = driver.findElement(By.id("frequency"));
			Select fq = new Select(Freq);
			fq.selectByVisibleText(Frequency);
			driver.findElement(By.xpath("//*[@id=\"fdMatVal\"]/div[2]/a[1]/img")).click();
			String M_Value = driver.findElement(By.xpath("//*[@id=\"resp_matval\"]/strong")).getText();
			if(Double.parseDouble(M_Value)==MaturityValue)
			{
				Assert.assertTrue(true, "Actual and Expected Maturity value is equal");
			}
			else {
				Assert.assertTrue(false, "Actual and Expected Maturity value is equal");
			}
			
			driver.findElement(By.xpath("//*[@id=\"fdMatVal\"]/div[2]/a[2]/img")).click();
	}
		driver.quit();
	}
		


}
