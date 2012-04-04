package com.github.glad2121.dataset.dbunit;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;

import com.github.glad2121.dataset.AbstractDataSet;
import com.github.glad2121.dataset.DataSet;
import com.github.glad2121.dataset.DataSetException;
import com.github.glad2121.dataset.DataTable;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DbUnitDataSet extends AbstractDataSet {

    private final IDataSet dataSet;

    public static DbUnitDataSet cast(DataSet dataSet) {
        return DbUnitDataSet.class.cast(dataSet);
    }

    public DbUnitDataSet(IDataSet dataSet) {
        this.dataSet = dataSet;
    }

    public IDataSet getDataSet() {
        return dataSet;
    }

    public String[] getTableNames() {
        try {
            return getDataSet().getTableNames();
        } catch (org.dbunit.dataset.DataSetException e) {
            throw new DataSetException(e);
        }
    }

    public ITableMetaData getTableMetaData(String tableName) {
        try {
            return getDataSet().getTableMetaData(tableName);
        } catch (org.dbunit.dataset.DataSetException e) {
            throw new DataSetException(e);
        }
    }

    public ITable getDbUnitTable(String tableName) {
        try {
            return getDataSet().getTable(tableName);
        } catch (org.dbunit.dataset.DataSetException e) {
            throw new DataSetException(e);
        }
    }

    protected DataTable createTable(String tableName) {
        return new DbUnitDataTable(getDbUnitTable(tableName));
    }

}
