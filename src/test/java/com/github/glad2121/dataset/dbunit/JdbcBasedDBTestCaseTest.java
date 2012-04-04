package com.github.glad2121.dataset.dbunit;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;
import org.dbunit.JdbcBasedDBTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glad2121.dataset.DataSet;
import com.github.glad2121.dataset.DataTable;
import com.github.glad2121.dataset.DbAccessor;
import com.github.glad2121.dataset.FileAccessor;
import com.github.glad2121.dataset.resource.FileResource;
import com.github.glad2121.dataset.resource.Resource;
import com.github.glad2121.dataset.util.DataSetUtils;
import com.github.glad2121.dataset.util.JavaUtils;
import com.github.glad2121.dataset.util.JdbcUtils;

/**
 * 
 * 
 * @author GLAD!!
 */
public class JdbcBasedDBTestCaseTest extends JdbcBasedDBTestCase {

    static final String[] TABLE_NAMES = {
        "T_SYSTEM",
        "T_ROLE",
        "T_USER",
        "T_USER_ROLE"
    };

    static final String INPUT_DIR = "src/test/data";

    static final String OUTPUT_DIR = "target/test/data";

    static final Logger logger =
            LoggerFactory.getLogger(JdbcBasedDBTestCaseTest.class);

    static boolean initialized = false;

    DbAccessor dbAccessor;

    FileAccessor fileAccessor;

    IDataSet dataSet;

    protected void setUp() throws Exception {
        if (!initialized) {
            init();
        }
        dbAccessor = new DbUnitDbAccessor(getConnection());
        fileAccessor = new DbUnitXlsAccessor();
        dataSet = DbUnitDataSet.cast(
                fileAccessor.read(getSetupResource())).getDataSet();
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        dataSet = null;
        fileAccessor = null;
        dbAccessor = null;
    }

    void init() throws Exception {
        logger.debug("initilizing...");
        InputStream in = getClass().getResourceAsStream("../setup.sql");
        String sql = IOUtils.toString(in, "UTF-8");
        IOUtils.closeQuietly(in);
        Connection con = getConnection().getConnection();
        Statement stmt = con.createStatement();
        try {
            stmt.executeUpdate(sql);
        } finally {
            JdbcUtils.closeQuietly(stmt);
        }
        initialized = true;
    }

    @Override
    protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(
                DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                new H2DataTypeFactory());
    }

    protected String getDriverClass() {
        return "org.h2.Driver";
    }

    protected String getConnectionUrl() {
        //return "jdbc:h2:mem:";
        return "jdbc:h2:~/.h2/dataset/test";
    }

    @Override
    protected String getUsername() {
        return "sa";
    }

    @Override
    protected String getPassword() {
        return "";
    }

    protected IDataSet getDataSet() throws Exception {
        return dataSet;
    }

    public void testConnection() throws Exception {
        IDatabaseConnection dc = getConnection();
        IDataSet ds = dc.createDataSet(TABLE_NAMES);
        System.out.println(ds);
        ITable users = ds.getTable("T_USER");
        System.out.println(users);
    }

    public void testReadDbWriteXls() {
        DataSet actual = dbAccessor.read(TABLE_NAMES);
        System.out.println(actual);
        DataTable users = actual.getTable("T_USER");
        System.out.println(users);
        fileAccessor.write(getActualResource(), actual);
    }

    public void testAssert() {
        dbAccessor.execute(
            "update T_USER_ROLE set" +
            "  UPDATED_BY = current_user()," +
            "  UPDATED    = current_timestamp()");
        
        DataSet expected = fileAccessor.read(getExpectedResource());
        DataSet actual = dbAccessor.read(TABLE_NAMES);
        DbUnitDataSetAsserterExt.INSTANCE.assertEquals(expected, actual);
    }

    Resource getSetupResource() {
        return new FileResource(INPUT_DIR,
                JavaUtils.getResourcePath(getClass(), "../SETUP.xls"));
    }

    Resource getExpectedResource() {
        return new FileResource(INPUT_DIR,
                DataSetUtils.getTestResourcePath(
                        getClass(), getName(), "_EXPECTED.xls"));
    }

    Resource getActualResource() {
        return new FileResource(OUTPUT_DIR,
                DataSetUtils.getTestResourcePath(
                        getClass(), getName(), "_ACTUAL.xls"));
    }

    Resource getActualResource(Resource expected) {
        return new FileResource(OUTPUT_DIR,
                expected.getPath().replaceFirst("EXPECTED", "ACTUAL"));
    }

}
