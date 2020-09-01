package com.itheima;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class PoiRead2 {

    //使用POI从Excel文件读取数据
    public static void main(String[] args) throws Exception{
        //通过输入流加载指定的文件，封装成一个POI提供的Excel文件对象
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\sun\\Desktop\\test_poi.xlsx")));
        //读取第一个工作表中的数据
        XSSFSheet sheet = excel.getSheetAt(0);
        //获得最后一个行号，行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<=lastRowNum;i++){
            XSSFRow row = sheet.getRow(i);//根据行索引获取某一行
            short lastCellNum = row.getLastCellNum();
            for(int j=0;j<lastCellNum;j++){
                Cell cell = row.getCell(j);
                //获得单元格中的数据
                switch (cell.getCellType()){
                    // 字符串类型
                    case Cell.CELL_TYPE_STRING :
                        System.out.print(cell.getStringCellValue()); break;
                    // 布尔值： boolean
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue()); break;
                    // 数值类型
                    case Cell.CELL_TYPE_NUMERIC :
                        //数值类型中也有可能是 日期类型，如果日期类型，则按照日期类型获取
                        if(HSSFDateUtil.isCellDateFormatted(cell)){
                            Date date = cell.getDateCellValue();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            String str = sdf.format(date);
                            System.out.print(str);
                        }else{
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            System.out.print(cell.getStringCellValue());
                        }
                        break;
                    //空值
                    case Cell.CELL_TYPE_BLANK :
                        System.out.print("空值"); break;
                    // 公式类型
                    case Cell.CELL_TYPE_FORMULA :
                        System.out.print(cell.getCellFormula()); break;
                    //  未知错误
                    case Cell.CELL_TYPE_ERROR :
                        System.out.print("未知错误"); break;
                    //未知类型
                    default:
                        System.out.print("未知类型"); break;
                }
                System.out.print("\t\t");
            }
            System.out.println();
        }
        //关闭资源
        excel.close();
    }
}
