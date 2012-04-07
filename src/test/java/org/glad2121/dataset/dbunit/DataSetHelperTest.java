package org.glad2121.dataset.dbunit;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Properties;

import org.glad2121.dataset.DataSetConfig;
import org.glad2121.dataset.DataSetConfigFactory;
import org.glad2121.dataset.DataSetHelper;
import org.glad2121.dataset.util.JavaUtils;
import org.glad2121.dataset.util.JdbcUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataSetHelperTest {

    Connection connection;

    DataSetConfig config;

    DataSetHelper helper;

    @Before
    public void setUp() throws Exception {
        Properties jdbcProps = JavaUtils.loadProperties("jdbc.properties");
        connection = JdbcUtils.getConnection(jdbcProps);
        config = DataSetConfigFactory.getConfig();
        helper = new DataSetHelper(connection, config);
    }

    @After
    public void tearDown() throws Exception {
        JdbcUtils.closeQuietly(connection);
        helper = null;
        config = null;
        connection = null;
    }

    @Test
    public void testAssert() {
        fail("Not yet implemented");
    }

}
