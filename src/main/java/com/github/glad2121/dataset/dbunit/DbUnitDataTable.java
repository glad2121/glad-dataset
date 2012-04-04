package com.github.glad2121.dataset.dbunit;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.Columns;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;

import com.github.glad2121.dataset.AbstractDataTable;
import com.github.glad2121.dataset.DataSetException;
import com.github.glad2121.dataset.DataTable;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DbUnitDataTable extends AbstractDataTable {

    private final ITable table;

    public static DbUnitDataTable cast(DataTable table) {
        return DbUnitDataTable.class.cast(table);
    }

    public DbUnitDataTable(ITable table) {
        this.table = table;
    }

    public ITable getTable() {
        return table;
    }

    public ITableMetaData getTableMetaData() {
        return getTable().getTableMetaData();
    }

    public String getTableName() {
        return getTableMetaData().getTableName();
    }

    public Column[] getColumns() {
        try {
            return getTableMetaData().getColumns();
        } catch (org.dbunit.dataset.DataSetException e) {
            throw new DataSetException(e);
        }
    }

    public String[] getColumnNames() {
        return Columns.getColumnNames(getColumns());
    }

    public Column[] getPrimaryKeys() {
        try {
            return getTableMetaData().getPrimaryKeys();
        } catch (org.dbunit.dataset.DataSetException e) {
            throw new DataSetException(e);
        }
    }

    public String[] getPrimaryKeyNames() {
        return Columns.getColumnNames(getPrimaryKeys());
    }

    public int getRowCount() {
        return getTable().getRowCount();
    }

    public Object getValue(int row, String column) {
        try {
            return getTable().getValue(row, column);
        } catch (org.dbunit.dataset.DataSetException e) {
            throw new DataSetException(e);
        }
    }

}
