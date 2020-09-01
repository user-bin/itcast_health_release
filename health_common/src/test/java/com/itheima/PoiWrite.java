package com.itheima;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class PoiWrite {
    //通过POI将数据写入到Excel文件中

    public static void main(String[] args) throws Exception{
        //在内存中创建一个Excel文件对象
        XSSFWorkbook excel = new XSSFWorkbook();
        //在Excel文件中创建工作表
        XSSFSheet sheet = excel.createSheet("传智播客");
        //在工作表中创建行对象
        XSSFRow row = sheet.createRow(0);
        //在行中创建单元格对象
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("地址");
        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("小明");
        row1.createCell(1).setCellValue("广州");
        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("小王");
        row2.createCell(1).setCellValue("北京");
        //使用输出流将内存中的Excel写到磁盘
        OutputStream out = new FileOutputStream(new File("C:\\Users\\sun\\Desktop\\test.xlsx"));
        excel.write(out);
        out.flush();
        out.close();
        excel.close();
    }
}
