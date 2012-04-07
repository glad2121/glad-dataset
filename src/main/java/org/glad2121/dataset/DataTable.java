package org.glad2121.dataset;

/**
 * 表形式のデータです。
 * 
 * @author GLAD!!
 */
public interface DataTable {

    String getTableName();

    String[] getColumnNames();

    String[] getPrimaryKeyNames();

    int getRowCount();

    Object getValue(int row, String column);

    <T> T getValue(int row, String column, Class<? extends T> type);

    Integer getInteger(int row, String column);

    Long getLong(int row, String column);

    String getString(int row, String column);

}
