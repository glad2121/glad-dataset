package org.glad2121.dataset;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.glad2121.dataset.util.JavaUtils;
import org.glad2121.dataset.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbInitializer {

    static final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    static boolean initialized = false;

    public void init() throws Exception {
        if (initialized) {
            return;
        }
        logger.debug("initilizing...");
        InputStream in = getClass().getResourceAsStream("setup.sql");
        String sql;
        try {
            sql = IOUtils.toString(in, "UTF-8");
        } finally {
            IOUtils.closeQuietly(in);
        }
        Properties props = JavaUtils.loadProperties("jdbc.properties");
        Connection con = JdbcUtils.getConnection(props);
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } finally {
            JdbcUtils.closeQuietly(con);
        }
        initialized = true;
    }

}
