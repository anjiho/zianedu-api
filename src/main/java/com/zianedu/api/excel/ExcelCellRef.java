package com.zianedu.api.excel;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelCellRef {
	
	/**
	 * <pre>
	 * 1. Comment : 사용자리스트 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03. 11
	 * 4. 설명 : Cell에 해당하는 Column Name을 가젼온다(A,B,C..)
     * 만약 Cell이 Null이라면 int cellIndex의 값으로
     * Column Name을 가져온다.
	 * </pre>
	 * @param cell
	 * @param cellIndex
	 * @return
	 */
	public static String getName(Cell cell, int cellIndex) {
		int cellNum = 0;
		if (cell != null) {
			cellNum = cell.getColumnIndex();
		} else {
			cellNum = cellIndex;
		}
		return CellReference.convertNumToColString(cellNum);
	}
	
	public static String getValue(Cell cell) {
		String value = "";
		
		if(cell == null) {
            value = "";
        }
        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_FORMULA :
                value = cell.getCellFormula();
                break;
            
            case Cell.CELL_TYPE_NUMERIC :
                value = (int)cell.getNumericCellValue() + "";
                break;
                
            case Cell.CELL_TYPE_STRING :
                value = cell.getStringCellValue();
                break;
            
            case Cell.CELL_TYPE_BOOLEAN :
                value = cell.getBooleanCellValue() + "";
                break;
           
            case Cell.CELL_TYPE_BLANK :
                value = "";
                break;
            
            case Cell.CELL_TYPE_ERROR :
                value = cell.getErrorCellValue() + "";
                break;
            default:
                value = cell.getStringCellValue();
        }
		return value;
	}

}
