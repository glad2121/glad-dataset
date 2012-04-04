package com.github.glad2121.dataset.util;

import static com.github.glad2121.dataset.util.JdbcUtils.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Properties;

import org.junit.Test;

/**
 * 
 * 
 * @author GLAD!!
 */
public class JdbcUtilsTest {

    @Test
    public void testGetConnection() {
        Properties props = new Properties();
        props.setProperty("jdbc.driver", "org.h2.Driver");
        props.setProperty("jdbc.url", "jdbc:h2:mem:");
        props.setProperty("jdbc.username", "sa");
        props.setProperty("jdbc.password", "");
        Connection con = getConnection(props);
        try {
            assertThat(con, notNullValue());
        } finally {
            closeQuietly(con);
        }
    }

}
