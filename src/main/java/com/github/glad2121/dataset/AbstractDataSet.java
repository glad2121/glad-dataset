package com.github.glad2121.dataset;

import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 
 * 
 * @author GLAD!!
 */
public abstract class AbstractDataSet implements DataSet {

    final Map<String, DataTable> tableCache =
            new WeakHashMap<String, DataTable>();

    public DataTable getTable(String tableName) {
        DataTable table = tableCache.get(tableName);
        if (table == null) {
            table = createTable(tableName);
            tableCache.put(tableName, table);
        }
        return table;
    }

    protected abstract DataTable createTable(String tableName);

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " {tableNames=" + Arrays.toString(getTableNames())
                + "}";
    }

}
