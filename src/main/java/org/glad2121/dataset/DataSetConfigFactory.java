package org.glad2121.dataset;

import java.util.Properties;

import org.glad2121.dataset.util.JavaUtils;

/**
 * ${@link DataSetConfig} のファクトリです。
 * 
 * @author GLAD!!
 */
public class DataSetConfigFactory {

    static final String DATASET_PROPERTIES = "dataset.properties";

    static final String DATASET_CONFIG_KEY = "dataset.config";

    static final String DEFAULT_DATASET_CONFIG =
            "com.github.glad2121.dataset.dbunit.DbUnitDataSetConfig";

    public static DataSetConfig getConfig() {
        return getConfig(DATASET_PROPERTIES);
    }

    public static DataSetConfig getConfig(String path) {
        return getConfig(JavaUtils.loadProperties(path));
    }

    public static DataSetConfig getConfig(Properties props) {
        String className = props.getProperty(
                DATASET_CONFIG_KEY, DEFAULT_DATASET_CONFIG);
        return JavaUtils.newInstance(DataSetConfig.class, className);
    }

}
