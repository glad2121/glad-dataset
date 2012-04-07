package org.glad2121.dataset;

/**
 * 表形式のデータの集まりです。
 * 
 * @author GLAD!!
 */
public interface DataSet {

    String[] getTableNames();

    DataTable getTable(String tableName);

}
