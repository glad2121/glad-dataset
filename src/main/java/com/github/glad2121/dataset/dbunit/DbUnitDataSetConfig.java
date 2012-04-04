package com.github.glad2121.dataset.dbunit;

import java.sql.Connection;
import java.util.Properties;

import com.github.glad2121.dataset.AbstractDataSetConfig;
import com.github.glad2121.dataset.DataSetAsserter;
import com.github.glad2121.dataset.DbAccessor;
import com.github.glad2121.dataset.FileAccessor;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DbUnitDataSetConfig extends AbstractDataSetConfig {

    public DbUnitDataSetConfig(Properties props) {
        super(props);
    }

    @Override
    public DbAccessor getDbAccessor(Connection connection) {
        DbAccessor dbAccessor = super.getDbAccessor(connection);
        if (dbAccessor != null) {
            return dbAccessor;
        }
        return new DbUnitDbAccessor(connection, getProperties());
    }

    @Override
    public FileAccessor getFileAccessor() {
        FileAccessor fileAccessor = super.getFileAccessor();
        if (fileAccessor != null) {
            return fileAccessor;
        }
        return new DbUnitXlsAccessor();
    }

    @Override
    public DataSetAsserter getAsserter() {
        DataSetAsserter asserter = super.getAsserter();
        if (asserter != null) {
            return asserter;
        }
        return DbUnitDataSetAsserter.INSTANCE;
    }

}
