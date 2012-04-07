package org.glad2121.dataset.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.glad2121.dataset.DataSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author GLAD!!
 */
public class JdbcUtils {

    static final String DRIVER_KEY = "jdbc.driver";

    static final String URL_KEY = "jdbc.url";

    static final String USERNAME_KEY = "jdbc.username";

    static final String PASSWORD_KEY = "jdbc.password";

    static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);

    public static Connection getConnection(Properties props) {
        String driver = props.getProperty(DRIVER_KEY);
        String url = props.getProperty(URL_KEY);
        Properties info = new Properties(props);
        if (props.containsKey(USERNAME_KEY)) {
            info.setProperty("user", props.getProperty(USERNAME_KEY));
        }
        if (props.containsKey(PASSWORD_KEY)) {
            info.setProperty("password", props.getProperty(PASSWORD_KEY));
        }
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, info);
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public static void closeQuietly(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            logger.warn(e.toString());
        }
    }

    public static void closeQuietly(Statement stmt) {
        if (stmt == null) {
            return;
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            logger.warn(e.toString());
        }
    }

}
