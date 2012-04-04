package com.github.glad2121.dataset.util;

import static com.github.glad2121.dataset.util.JavaUtils.*;
import static com.github.glad2121.dataset.util.IOUtils.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

/**
 * 
 * 
 * @author GLAD!!
 */
public class JavaUtilsTest {

    @Test
    public void testLoadProperties() {
        InputStream in = getContextClassLoader()
                .getResourceAsStream("jdbc.properties");
        try {
            Properties props = loadProperties(in);
            assertThat(props.getProperty("jdbc.driver"), is("org.h2.Driver"));
            //assertThat(props.getProperty("jdbc.url"), is("jdbc:h2:mem:"));
            assertThat(props.getProperty("jdbc.username"), is("sa"));
            assertThat(props.getProperty("jdbc.password"), is(""));
        } finally {
            closeQuietly(in);
        }
    }

}
