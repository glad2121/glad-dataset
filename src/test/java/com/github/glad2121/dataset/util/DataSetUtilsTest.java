package com.github.glad2121.dataset.util;

import static com.github.glad2121.dataset.util.DataSetUtils.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DataSetUtilsTest {

    @Test
    public void testToInteger() {
        assertThat(toInteger(null), nullValue());
        assertThat(toInteger(123), is(123));
        assertThat(toInteger(123L), is(123));
        assertThat(toInteger("123"), is(123));
    }

    @Test
    public void testToLong() {
        assertThat(toLong(null), nullValue());
        assertThat(toLong(123), is(123L));
        assertThat(toLong(123L), is(123L));
        assertThat(toLong("123"), is(123L));
    }

    @Test
    public void testToString() {
        assertThat(DataSetUtils.toString(null), nullValue());
        assertThat(DataSetUtils.toString(123), is("123"));
        assertThat(DataSetUtils.toString("abc"), is("abc"));
    }

}
