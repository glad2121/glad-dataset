package com.github.glad2121.dataset;

import java.io.File;
import java.sql.Connection;

/**
 * 
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
