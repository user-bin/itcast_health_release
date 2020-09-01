package com.itheima;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
public class PoiRead {
    public static void main(String[] args) throws IOException {
        //使用POI从Excel文件读取数据
            //通过输入流加载指定的文件，封装成一个POI提供的Excel文件对象
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\sun\\Desktop\\test_poi.xlsx")));
            //读取第一个工作表中的数据
            XSSFSheet sheet = excel.getSheetAt(0);
            //遍历工作表获得每一行数据
            for (Row row : sheet) {
                //遍历行对象获得每一列，获得单元格对象
                for (Cell cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
            //关闭资源
            excel.close();
    }
}
