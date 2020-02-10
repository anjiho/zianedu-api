package com.zianedu.api.service;

import com.zianedu.api.dto.OffLineExamDTO;
import com.zianedu.api.excel.ExcelRead;
import com.zianedu.api.excel.ExcelReadOption;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExcelReadService {

    public int readOffLineExamResult(File destFile) throws Exception {
        FileInputStream fis = new FileInputStream(destFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int rowIdx = 0;
        int columnIdx = 0;

        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();

        List<OffLineExamDTO> offLineExamDTOList = new ArrayList<>();
        for (rowIdx=1; rowIdx<rows; rowIdx++) {
            List<Integer> number = new ArrayList<>();
            OffLineExamDTO offLineExamDTO = new OffLineExamDTO();
            String serial = "";
            int subjectCount = 0;
            int scoreNumber = 0;
            XSSFRow row = sheet.getRow(rowIdx);
            if (row != null) {
                if (row.getCell(0) != null) {
                    //셀의 수
                    int cells = row.getPhysicalNumberOfCells();
                    subjectCount = cells / 20;
                    for (columnIdx = 0; columnIdx <= cells; columnIdx++) {

                        //셀값을 읽는다
                        XSSFCell cell = row.getCell(columnIdx);
                        String value = "";
                        //셀이 빈값일경우를 위한 널체크
                        if (cell == null) {
                            continue;
                        } else {
                            if (columnIdx == 0) {
                                //타입별로 내용 읽기
                                switch (cell.getCellType()) {
                                    case XSSFCell.CELL_TYPE_FORMULA:
                                        value = cell.getCellFormula();
                                        break;
                                    case XSSFCell.CELL_TYPE_NUMERIC:
                                        value = cell.getNumericCellValue() + "";
                                        break;
                                    case XSSFCell.CELL_TYPE_STRING:
                                        value = cell.getStringCellValue() + "";
                                        break;
                                    case XSSFCell.CELL_TYPE_BLANK:
                                        value = cell.getBooleanCellValue() + "";
                                        break;
                                    case XSSFCell.CELL_TYPE_ERROR:
                                        value = cell.getErrorCellValue() + "";
                                        break;
                                }
                                serial = value;
                            } else {
                                switch (cell.getCellType()) {
                                    case XSSFCell.CELL_TYPE_FORMULA:
                                        value = cell.getCellFormula();
                                        break;
                                    case XSSFCell.CELL_TYPE_NUMERIC:
                                        value = (int) cell.getNumericCellValue() + "";
                                        break;
                                    case XSSFCell.CELL_TYPE_STRING:
                                        value = cell.getStringCellValue() + "";
                                        break;
                                    case XSSFCell.CELL_TYPE_BLANK:
                                        value = cell.getBooleanCellValue() + "";
                                        break;
                                    case XSSFCell.CELL_TYPE_ERROR:
                                        value = cell.getErrorCellValue() + "";
                                        break;
                                }
                                scoreNumber = Integer.parseInt(value);
                                number.add(scoreNumber);
                            }
                        }
                        System.out.println("각 셀 내용 :" + value);
                        offLineExamDTO.setExamNumberList(number);
                    }
                    offLineExamDTO.setSerial(serial);
                    offLineExamDTO.setSubjectCount(subjectCount);
                    offLineExamDTOList.add(offLineExamDTO);
                }
            }
        }
        System.out.println(offLineExamDTOList);
        return 0;
    }
}
