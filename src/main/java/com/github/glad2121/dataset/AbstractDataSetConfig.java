package com.github.glad2121.dataset;

import java.io.File;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.Properties;

import com.github.glad2121.dataset.util.JavaUtils;

/**
 * {@link DataSetConfig} の実装のベースとなる抽象クラスです。
 * 
 * @author GLAD!!
 */
public class AbstractDataSetConfig implements DataSetConfig {

    protected static final String DB_ACCESSOR_KEY = "dataset.dbAccessor";

    protected static final String FILE_ACCESSOR_KEY = "dataset.fileAccessor";

    protected static final String ASSERTER_KEY = "dataset.asserter";

    protected static final String INPUT_DIR_KEY = "dataset.inputDir";

    protected static final String OUTPUT_DIR_KEY = "dataset.outputDir";

    final Properties props;

    public AbstractDataSetConfig(Properties props) {
        this.props = props;
    }

    protected Properties getProperties() {
        return props;
    }

    protected boolean hasProperty(String key) {
        return props.containsKey(key);
    }

    protected String getProperty(String key) {
        return props.getProperty(key);
    }

    protected <T> T get(String key, Class<T> type) {
        String prop = getProperty(key);
        if (prop == null) {
            return null;
        }
        if (type == String.class) {
            return type.cast(prop);
        }
        return JavaUtils.newInstance(type, prop);
    }

    public DbAccessor getDbAccessor(Connection connection) {
        String prop = getProperty(DB_ACCESSOR_KEY);
        if (prop == null) {
            return null;
        }
        Class<?> clazz = JavaUtils.loadClass(prop);
        Constructor<?> c = JavaUtils.getConstructor(
                clazz, Connection.class, Properties.class);
        return DbAccessor.class.cast(
                JavaUtils.newInstance(c, connection, getProperties()));
    }

    public FileAccessor getFileAccessor() {
        return get(FILE_ACCESSOR_KEY, FileAccessor.class);
    }

    public DataSetAsserter getAsserter() {
        return get(ASSERTER_KEY, DataSetAsserter.class);
    }

    public File getInputDir() {
        if (hasProperty(INPUT_DIR_KEY)) {
            return new File(getProperty(INPUT_DIR_KEY));
        } else {
            return new File("src/test/data");
        }
    }

    public File getOutputDir() {
        if (hasProperty(OUTPUT_DIR_KEY)) {
            return new File(getProperty(OUTPUT_DIR_KEY));
        } else {
            return new File("target/test/data");
        }
    }

}
