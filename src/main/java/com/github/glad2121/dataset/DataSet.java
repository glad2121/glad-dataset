package com.github.glad2121.dataset;

/**
 * 
 * 
 * @author GLAD!!
 */
public interface DataSet {

    String[] getTableNames();

    DataTable getTable(String tableName);

}
