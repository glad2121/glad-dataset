package org.glad2121.dataset;

import java.io.File;
import java.sql.Connection;

/**
 * DataSet ライブラリの設定を行います。
 * 
 * @author GLAD!!
 */
public interface DataSetConfig {

    DbAccessor getDbAccessor(Connection connection);

    FileAccessor getFileAccessor();

    DataSetAsserter getAsserter();

    File getInputDir();

    File getOutputDir();

}
