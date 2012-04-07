package org.glad2121.dataset.seasar;

import org.glad2121.dataset.AbstractDataSet;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;

public class S2DataSet extends AbstractDataSet {

    private final DataSet dataSet;

    public static S2DataSet cast(
            org.glad2121.dataset.DataSet dataSet) {
        return S2DataSet.class.cast(dataSet);
    }

    public S2DataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public String[] getTableNames() {
        DataSet dataSet = getDataSet();
        int length = dataSet.getTableSize();
        String[] tableNames = new String[length];
        for (int i = 0; i < length; ++i) {
            tableNames[i] = dataSet.getTableName(i);
        }
        return tableNames;
    }

    public DataTable getS2Table(String tableName) {
        return getDataSet().getTable(tableName);
    }

    protected org.glad2121.dataset.DataTable
            createTable(String tableName) {
        return new S2DataTable(getS2Table(tableName));
    }

}
