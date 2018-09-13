package com.example.wry_springboot.Util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

@Slf4j
public class exportUtil <T>{
    /*****
     *
     * @param title 表名
     * @param headerName 表格列名
     * @param isExportId  需要导出的字段
     * @param dataList  查询的数据集合
     */
    public void exportUtil(String title, List<String> headerName ,List<String> isExportId ,List<T> dataList){
        try {
            InputStream inputStream = new FileInputStream("/tmp/workbook.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * 表头存放到headerNameMap中
         */
        Map<Integer,String> headerNameMap =new HashMap<>();
        //key变量是 headerNameMap 的key值。
        int headerkey =0;
        for (int i =0;i<headerName.size();i++){
            if(!headerName.get(i).equals(null)){
                headerNameMap.put(headerkey ,headerName.get(i));
                headerkey++;
            }
        }
        /*****
         * 字段名存放到titleFieldMap  中
         */
        Map<Integer,String> titleFieldMap =new HashMap<>();
        int titleKey =0;
        for(int i =0; i<isExportId.size();i++){
            if(!isExportId.get(i).equals(null)){
                titleFieldMap.put(titleKey,isExportId.get(i));
                titleKey++;
            }
        }
        /*****
         * 创建EXCEL
         * 使用 HSSFWorkbook 在数据量大的情况下可能会导致内存溢出
         */
        HSSFWorkbook wb =new HSSFWorkbook();
        HSSFSheet sheet =wb.createSheet(title);
        //设置列宽
        sheet.setDefaultColumnWidth(15);
        //设置表格格式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //创建行列
        HSSFRow row =sheet.createRow(0);
        HSSFCell cell;
        //拿到表格所有标题的value的集合
        Collection collection = headerNameMap.values();
        Iterator<String> iterator = collection.iterator();//表格标题的迭代器
        //根据选择的字段生成表头
        short size = 0;
        while (iterator.hasNext()) {
            cell = row.createCell(size);
            cell.setCellValue(iterator.next().toString());
            cell.setCellStyle(style);
            size++;
        }
        //表格标题一行的字段的集合
        Collection headerC = titleFieldMap.values();
        Iterator<T> labIt = dataList.iterator();//总记录的迭代器
        int headerRow =0;
        while (labIt.hasNext()){
            int headerCell=0;
            headerRow++;
            row = sheet.createRow(headerRow);
            T t =labIt.next();
            Field[] fields =t.getClass().getDeclaredFields();
            log.info(String.valueOf(fields));
            for(short i =0;i<fields.length;i++){
                Field field = fields[i];
                String fieldName =field.getName();
                Iterator<String> headerIt = headerC.iterator();//一条字段的集合的迭代器
                while (headerIt.hasNext()){
                    if (headerIt.next().equals(fieldName)){
                        String afterM =fieldName.substring(0,1);
                        String s =afterM.toUpperCase();
                        String beforM =fieldName.substring(1);
                        String getMethodName = "get"+
                                s+beforM;
                        log.info(getMethodName);
                        Class beanCls = t.getClass();
                        try {
                            Method getMethod = beanCls.getMethod(getMethodName,
                                    new Class[] {});
                            try {
                                Object val =getMethod.invoke(t ,new Object[] {});
                                String textVal = null;
                                if(val != null){
                                    textVal = String.valueOf(val);
                                }else {
                                    textVal = null;
                                }
                                row.createCell(headerCell).setCellValue(textVal);
                                headerCell++;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        try {
            FileOutputStream exportXLs =new FileOutputStream("/tmp/workbook.xls");
            try {
                wb.write(exportXLs);
                exportXLs.close();
                log.info("export success!!!!");
            } catch (IOException e) {
                e.printStackTrace();
                log.info("export error "+e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.info("export error "+e);
        }
    }
}
