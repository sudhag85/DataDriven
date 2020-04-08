package apachePOIOperations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AnotherMethodToReadData {

	public static void main(String[] args) throws IOException {
		FileInputStream f = new FileInputStream("C:\\Sudha\\TestData\\OrangeHRMLoginDetails.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(f);
		XSSFSheet Sheet = workbook.getSheetAt(0);
		int rowCount = Sheet.getLastRowNum();
		int columnCount = Sheet.getRow(0).getLastCellNum();
		for(int i=0;i<=rowCount;i++) 
		{
			XSSFRow currentRow = Sheet.getRow(i);
			for(int j=0;j<columnCount;j++) 
			{
				String cvalue = currentRow.getCell(j).toString();
				System.out.print(" "+cvalue);
			}
			System.out.println();
		}

	}

}
