package org.glad2121.dataset.dbunit;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.excel.XlsDataSetWriter;
import org.glad2121.dataset.AbstractFileAccessor;
import org.glad2121.dataset.DataSet;
import org.glad2121.dataset.DataSetException;
import org.glad2121.dataset.util.DataSetUtils;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DbUnitXlsAccessor extends AbstractFileAccessor {

    public DataSet read(InputStream in) {
        try {
            return new DbUnitDataSet(new XlsDataSet(in));
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public void write(OutputStream out, DataSet dataSet) {
        try {
            new Writer().write(DbUnitDataSet.cast(dataSet).getDataSet(), out);
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    static class Writer extends XlsDataSetWriter {

        @Override
        protected void setDateCell(
                HSSFCell cell, Date value, HSSFWorkbook workbook) {
            cell.setCellValue(
                    new HSSFRichTextString(DataSetUtils.toString(value)));
        }

    }

}
