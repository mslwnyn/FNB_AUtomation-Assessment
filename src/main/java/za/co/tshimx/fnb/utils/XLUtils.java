/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.tshimx.fnb.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author tshimologo
 */
public class XLUtils {
    
    public static FileInputStream fi;
    public static Workbook wb;
    public static Sheet ws;
    public static Row row;
    public static Cell cell;
    
    public static int getRowCount (String xlfile,String xlsheet) throws IOException{
        
        fi = new FileInputStream (xlfile);
        wb = new XSSFWorkbook (fi);
        ws = (Sheet) wb.getSheet(xlsheet);
        int rowcount=ws.getLastRowNum();
        wb.close();
        fi.close();
        return rowcount;
        
    }
    
    public static int getCellCount (String xlfile,String xlsheet,int rowNum) throws IOException{
        
        fi = new FileInputStream (xlfile);
        wb = new    XSSFWorkbook (fi);
        ws = wb.getSheet(xlsheet);
        row= ws.getRow(rowNum);
        int cellCount=row.getLastCellNum();
        wb.close();
        fi.close();
        return cellCount;
        
    }
    
    public static String getCellData (String xlfile,String xlsheet,int rowNum,int colNum) throws IOException{
        
        fi = new FileInputStream (xlfile);
        wb = new XSSFWorkbook (fi);
        ws = wb.getSheet(xlsheet);
        row= ws.getRow(rowNum);
        cell = row.getCell(colNum);
        String data;
        try{
            data = cell.getStringCellValue();
        }catch(Exception e){
             data="";
        }
        
        
        wb.close();
        fi.close();
        return data;
        
    }
    
    
}
