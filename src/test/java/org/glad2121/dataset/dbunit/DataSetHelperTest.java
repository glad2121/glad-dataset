package org.glad2121.dataset.dbunit;

import java.sql.Connection;
import java.util.Properties;

import org.glad2121.dataset.DataSetConfig;
import org.glad2121.dataset.DataSetConfigFactory;
import org.glad2121.dataset.DataSetHelper;
import org.glad2121.dataset.DbInitializer;
import org.glad2121.dataset.resource.FileResource;
import org.glad2121.dataset.resource.Resource;
import org.glad2121.dataset.util.DataSetUtils;
import org.glad2121.dataset.util.JavaUtils;
import org.glad2121.dataset.util.JdbcUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class DataSetHelperTest {

    static final String[] TABLE_NAMES = {
        "T_SYSTEM",
        "T_ROLE",
        "T_USER",
        "T_USER_ROLE"
    };

    @Rule
    public TestName testName = new TestName();

    Connection connection;

    DataSetConfig config;

    DataSetHelper helper;

    @Before
    public void setUp() throws Exception {
        new DbInitializer().init();
        Properties props = JavaUtils.loadProperties("jdbc.properties");
        connection = JdbcUtils.getConnection(props);
        config = DataSetConfigFactory.getConfig();
        helper = new DataSetHelper(connection, config);
        helper.cleanInsert(getSetupResource(), TABLE_NAMES);
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
        helper.executeSql(
            "update T_USER_ROLE set\n" +
            "  UPDATED_BY = current_user(),\n" +
            "  UPDATED    = current_timestamp()");

        helper.assertDb(getExpectedResource(), TABLE_NAMES);
    }

    Resource getSetupResource() {
        return new FileResource(helper.getInputDir(),
                JavaUtils.getResourcePath(getClass(), "../SETUP.xls"));
    }

    Resource getExpectedResource() {
        return new FileResource(helper.getInputDir(),
                DataSetUtils.getTestResourcePath(
                        getClass(), testName.getMethodName(), "_EXPECTED.xls"));
    }

}
