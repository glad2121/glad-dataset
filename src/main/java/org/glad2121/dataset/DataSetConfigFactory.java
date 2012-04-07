package org.glad2121.dataset;

import java.lang.reflect.Constructor;
import java.util.Properties;

import org.glad2121.dataset.util.JavaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ${@link DataSetConfig} のファクトリです。
 * 
 * @author GLAD!!
 */
public class DataSetConfigFactory {

    static final String DATASET_PROPERTIES = "dataset.properties";

    static final String DATASET_CONFIG_KEY = "dataset.config";

    static final String DEFAULT_DATASET_CONFIG =
            "org.glad2121.dataset.dbunit.DbUnitDataSetConfig";

    static final Logger logger =
            LoggerFactory.getLogger(DataSetConfigFactory.class);

    public static DataSetConfig getConfig() {
        return DEFAULT_CONFIG;
    }

    public static DataSetConfig getConfig(String path) {
        if (DATASET_PROPERTIES.equals(path)) {
            return getConfig();
        }
        return getConfig0(path);
    }

    static DataSetConfig getConfig0(String path) {
        return getConfig(JavaUtils.loadProperties(path));
    }

    public static DataSetConfig getConfig(Properties props) {
        String className = props.getProperty(
                DATASET_CONFIG_KEY, DEFAULT_DATASET_CONFIG);
        Class<?> clazz = JavaUtils.loadClass(className);
        Constructor<?> c = JavaUtils.getConstructor(clazz, Properties.class);
        return DataSetConfig.class.cast(JavaUtils.newInstance(c, props));
    }

    // 最後に定義すること。
    static final DataSetConfig DEFAULT_CONFIG = getConfig0(DATASET_PROPERTIES);

}
