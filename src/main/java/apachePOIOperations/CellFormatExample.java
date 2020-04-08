package apachePOIOperations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CellFormatExample {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fis = new FileInputStream("C:\\Sudha\\TestData\\CalData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet Sheet = workbook.getSheetAt(0);
		for(int i=0;i<=5;i++) {
			CellType cellType = workbook.getSheetAt(0).getRow(1).getCell(i).getCellType();
			//String styleString = cellStyle.getDataFormatString();
			System.out.println(cellType);	
		}

		
	}

}
