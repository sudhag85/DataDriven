package apachePOIOperations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ReadDataUsingPOI {
	static WebDriver driver;
	static List<String> usernameList = new ArrayList<String>();
	static List<String> passwordList = new ArrayList<String>();
	
	public void getData() throws IOException {
		String Path = "C:\\Sudha\\TestData\\OrangeHRMLoginDetails.xlsx";
		FileInputStream fis = new FileInputStream(Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		Iterator<Row> rowIterator =sheet.iterator();
		while(rowIterator.hasNext()) {
			Row rowvalue =rowIterator.next();
			Iterator<Cell> cellIterator = rowvalue.iterator();
			int i=2;
			while(cellIterator.hasNext()) {
				if (i%2==0) {
				usernameList.add(cellIterator.next().getStringCellValue());
				}else {
				passwordList.add(cellIterator.next().getStringCellValue());
				}
				i++;
			}
		}	
	}

	public void login(String uname, String pwd) {
		System.setProperty("webdriver.chrome.driver", "C:\\Sudha\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/index.php/auth/validateCredentials");
		driver.findElement(By.id("txtUsername")).sendKeys(uname);
		driver.findElement(By.id("txtPassword")).sendKeys(pwd);
		driver.findElement(By.id("btnLogin")).click();
		driver.close();
	}
	
public void executeTest() {
	for(int i=0;i<usernameList.size();i++) {
		login(usernameList.get(i),passwordList.get(i));
	}
	
}
	public static void main(String[] args) throws IOException {
		ReadDataUsingPOI usingPOI = new ReadDataUsingPOI();
		usingPOI.getData();
		System.out.println("usernamelist"+ usernameList);
		System.out.println("passwordList"+ passwordList);
		usingPOI.executeTest();		
		

	}

}
