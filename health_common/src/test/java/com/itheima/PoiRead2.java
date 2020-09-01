package com.itheima;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class PoiRead2 {

    public static void main(String[] args) throws IOException {
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
                System.out.print(cell);
                System.out.print("\t");
            }
            System.out.println();
        }
        //关闭资源
        excel.close();
    }
}
