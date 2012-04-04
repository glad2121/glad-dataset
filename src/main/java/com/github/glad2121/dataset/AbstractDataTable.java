package com.github.glad2121.dataset;

import java.util.Arrays;

import com.github.glad2121.dataset.util.DataSetUtils;

/**
 * {@link DataTable} の実装のベースとなる抽象クラスです。
 * 
 * @author GLAD!!
 */
public abstract class AbstractDataTable implements DataTable {

    public abstract Object getValue(int row, String column);

    public <T> T getValue(int row, String column, Class<? extends T> type) {
        return type.cast(getValue(row, column));
    }

    public Integer getInteger(int row, String column) {
        return DataSetUtils.toInteger(getValue(row, column));
    }

    public Long getLong(int row, String column) {
        return DataSetUtils.toLong(getValue(row, column));
    }

    public String getString(int row, String column) {
        return DataSetUtils.toString(getValue(row, column));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " {tableName=" + getTableName()
                + ", columnNames=" + Arrays.toString(getColumnNames())
                + ", primaryKeyNames=" + Arrays.toString(getPrimaryKeyNames())
                + ", rowCount=" + getRowCount()
                + "}";
    }

}
