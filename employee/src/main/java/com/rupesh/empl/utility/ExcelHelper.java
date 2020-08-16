package com.rupesh.empl.utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.rupesh.empl.model.Employee;

public class ExcelHelper {
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Name", "Gender", "Department","DateOfBirth" };
	static String SHEET = "EmployeeData";

	  public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }
	  
	  public static List<Employee> excelToTutorials(InputStream is) {
		    try {
		      Workbook workbook = new XSSFWorkbook(is);

		      Sheet sheet = workbook.getSheet(SHEET);
		      Iterator<Row> rows = sheet.iterator();

		      List<Employee> emplist = new ArrayList<Employee>();

		      int rowNumber = 0;
		      while (rows.hasNext()) {
		        Row currentRow = rows.next();

		        // skip header
		        if (rowNumber == 0) {
		          rowNumber++;
		          continue;
		        }

		        Iterator<Cell> cellsInRow = currentRow.iterator();
		        
		        Employee emp=new Employee();

		        int cellIdx = 0;
		        while (cellsInRow.hasNext()) {
		          Cell currentCell = cellsInRow.next();

		          switch (cellIdx) {
		          case 0:
		            emp.setName(currentCell.getStringCellValue());
		            break;

		          case 1:
		            emp.setGender(currentCell.getStringCellValue());
		            break;

		          case 2:
		            emp.setDepartment(currentCell.getStringCellValue());
		            break;

		          case 3:
		            emp.setDob(convertToLocalDateViaInstant(currentCell.getDateCellValue()));
		            break;

		          default:
		            break;
		          }

		          cellIdx++;
		        }
		        emplist.add(emp);
		      }
		      workbook.close();

		      return emplist;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		    }
		  }
	  
	  private static LocalDate convertToLocalDateViaInstant(java.util.Date date) {
		    return date.toInstant()
		      .atZone(ZoneId.systemDefault())
		      .toLocalDate();
		}
}
