package com.github.glad2121.dataset.dbunit;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.glad2121.dataset.DataSetConfig;
import com.github.glad2121.dataset.DataSetConfigFactory;
import com.github.glad2121.dataset.DataSetHelper;
import com.github.glad2121.dataset.util.JavaUtils;
import com.github.glad2121.dataset.util.JdbcUtils;

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
