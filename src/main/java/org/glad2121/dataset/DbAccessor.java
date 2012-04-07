package org.glad2121.dataset;

import java.sql.Connection;

/**
 * データベースに対するアクセッサです。
 * 
 * @author GLAD!!
 */
public interface DbAccessor {

    Connection getConnection();

    DataSet read(String... tableNames);

    void cleanInsert(DataSet dataSet);

    void insert(DataSet dataSet);

    void update(DataSet dataSet);

    void merge(DataSet dataSet);

    void delete(DataSet dataSet);

    void deleteAll(DataSet dataSet);

    void truncate(DataSet dataSet);

    int execute(String... sql);

}
