package org.glad2121.dataset.seasar;

import java.util.ArrayList;
import java.util.List;

import org.glad2121.dataset.AbstractDataTable;
import org.seasar.extension.dataset.DataColumn;
import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataTable;

public class S2DataTable extends AbstractDataTable {

    final DataTable table;

    public static S2DataTable cast(
            org.glad2121.dataset.DataTable table) {
        return S2DataTable.class.cast(table);
    }

    public S2DataTable(DataTable table) {
        this.table = table;
    }

    public DataTable getTable() {
        return table;
    }

    public String getTableName() {
        return getTable().getTableName();
    }

    public String[] getColumnNames() {
        DataTable table = getTable();
        int length = table.getColumnSize();
        String[] columnNames = new String[length];
        for (int i = 0; i < length; ++i) {
            columnNames[i] = table.getColumnName(i);
        }
        return columnNames;
    }

    public String[] getPrimaryKeyNames() {
        DataTable table = getTable();
        int length = table.getColumnSize();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < length; ++i) {
            DataColumn column = table.getColumn(i);
            if (column.isPrimaryKey()) {
                list.add(column.getColumnName());
            }
        }
        return list.toArray(new String[list.size()]);
    }

    public int getRowCount() {
        return getTable().getRowSize();
    }

    public DataRow getRow(int row) {
        return getTable().getRow(row);
    }

    public Object getValue(int row, String column) {
        return getRow(row).getValue(column);
    }

}
