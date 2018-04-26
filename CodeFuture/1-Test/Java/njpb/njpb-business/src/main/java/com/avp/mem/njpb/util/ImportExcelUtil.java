package com.avp.mem.njpb.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImportExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImportExcelUtil.class);
    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

    private final static Integer excelNum = 2000; // 2007+ 版本的excel

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     * 
     * @param in
     *            ,fileName
     * @return
     * @throws IOException
     */
    public List<List<Object>> getBankListByExcel(InputStream in, String fileName)
            throws IOException {
        int j=0,y=0;
        try {
            List<List<Object>> list = null;
            logger.debug("开始处理excel文件 : {}", fileName);

            // 创建Excel工作薄
            Workbook work = this.getWorkbook(in, fileName);
            if (null == work) {
                throw new IOException("Excel工作薄为空！");
            }
            Sheet sheet = null;
            Row row = null;
            Cell cell = null;

            list = new ArrayList<List<Object>>();
            // 遍历Excel中所有的sheet
            for (int i = 0; i < work.getNumberOfSheets(); i++) {
                sheet = work.getSheetAt(i);
                logger.debug("开始处理sheet({})", sheet.getSheetName());
                int headLength = sheet.getRow(0).getLastCellNum();

                // 遍历当前sheet中的所有行
                for (j = 1; j <= sheet.getLastRowNum(); j++) {
                    logger.debug("开始处理excel的第{}行数据", j + 1);
                    row = sheet.getRow(j);
                    if (row == null) {
                        logger.debug("当前行({})数据为空，跳过改行执行下一行",j+1);
                        continue;
                    }

                    // 遍历所有的列
                    List<Object> li = new ArrayList<Object>();
                    for (y = 0; y < headLength; y++) {
                        logger.debug("开始处理第{}行第{}列数据", j + 1, y + 1);
                        cell = row.getCell(y);
                        logger.debug("第{}行第{}列数据值为:{}", j + 1, y + 1, cell);
                        li.add(this.getCellValue(cell));
                    }
                    list.add(li);
                }
            }
            work.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("excel文件内容有误,错误位置 : {}行 {}列" ,j+1,y+1 );
            throw new IOException("excel文件内容有误,错误位置 : "+(j+1)+"行 "+ (y+1)+"列");
        }

    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * 
     * @param inStr
     *            ,fileName
     * @return
     * @throws IOException
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws IOException {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new IOException("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     * 
     * @param cell
     * @return
     */
    public Object getCellValue(Cell cell) {
        Object value = null;
        if(cell == null){
            return "";
        }
        DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字

        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            value = cell.getRichStringCellValue().getString().trim();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                value = df.format(cell.getNumericCellValue());
            } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                value = sdf.format(cell.getDateCellValue());
            } else {
                value = df2.format(cell.getNumericCellValue());
            }
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            value = cell.getBooleanCellValue();
            break;
        case Cell.CELL_TYPE_BLANK:
            value = "";
            break;
        default:
            break;
        }
        return value;
    }

}