package apachePOIOperations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DDT_ReadExcel_Example1 {
List<String> data = new ArrayList<String>();
String cellData;
	public void readExcel() throws IOException {
		FileInputStream fis = new FileInputStream("C:\\Sudha\\TestData\\CalData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet Sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		Iterator<Row> rowIterator = Sheet.iterator();
		while(rowIterator.hasNext())
		{
			
			Row rowValue = rowIterator.next();
			int currentRow = rowValue.getRowNum();
			if(currentRow!=0) {
			Iterator<Cell> cellIterator = rowValue.iterator();
			while(cellIterator.hasNext())
			{
				Cell cellValue = cellIterator.next();
				cellData = dataFormatter.formatCellValue(cellValue);
				data.add(cellData);
				System.out.println(data);
				
			}

			}
		}
		workbook.close();
		fis.close();
	}
	public static void main(String[] args) throws IOException {
		DDT_ReadExcel_Example1 eg = new DDT_ReadExcel_Example1();
		eg.readExcel();

	}

}
