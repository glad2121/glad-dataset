package com.github.glad2121.dataset;

import java.io.File;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glad2121.dataset.resource.FileResource;
import com.github.glad2121.dataset.resource.Resource;
import com.github.glad2121.dataset.resource.ResourceNotFoundException;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DataSetHelper {

    static final Logger logger = LoggerFactory.getLogger(DataSetHelper.class);

    final DbAccessor dbAccessor;

    final FileAccessor fileAccessor;

    final DataSetAsserter asserter;

    final File inputDir;

    final File outputDir;

    public DataSetHelper(Connection connection) {
        this(connection, DataSetConfigFactory.getConfig());
    }

    public DataSetHelper(Connection connection, DataSetConfig config) {
        this.dbAccessor = config.getDbAccessor(connection);
        this.fileAccessor = config.getFileAccessor();
        this.asserter = config.getAsserter();
        this.inputDir = config.getInputDir();
        this.outputDir = config.getOutputDir();
    }

    public DataSetHelper(
            DbAccessor dbAccessor, FileAccessor fileAccessor,
            DataSetAsserter asserter, File inputDir, File outputDir) {
        this.dbAccessor = dbAccessor;
        this.fileAccessor = fileAccessor;
        this.asserter = asserter;
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    public DbAccessor getDbAccessor() {
        return dbAccessor;
    }

    public FileAccessor getFileAccessor() {
        return fileAccessor;
    }

    public DataSetAsserter getAsserter() {
        return asserter;
    }

    public File getInputDir() {
        return inputDir;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public DataSet readDb(String... tableNames) {
        return getDbAccessor().read(tableNames);
    }

    public void assertDb(Resource expected) {
        DataSet dataSet = readFile(expected);
        DataSet actual = readDb(dataSet.getTableNames());
        getAsserter().assertEquals(dataSet, actual);
    }

    public void assertDb(Resource expected, String... tableNames) {
        DataSet dataSet = readFile(expected, tableNames);
        DataSet actual = readDb(dataSet.getTableNames());
        getAsserter().assertEquals(dataSet, actual);
    }

    public DataSet readFile(Resource resource) {
        return getFileAccessor().read(resource);
    }

    public DataSet readFile(Resource resource, String... tableNames) {
        try {
            return readFile(resource);
        } catch (ResourceNotFoundException e) {
            try {
                writeFile(getInputResource(resource), readDb(tableNames));
            } catch (Exception e2) {
                logger.warn(e2.toString());
            }
            throw e;
        }
    }

    public void writeFile(Resource resource, DataSet dataSet) {
        getFileAccessor().write(resource, dataSet);
    }

    Resource getInputResource(Resource resource) {
        if (resource instanceof FileResource) {
            return resource;
        } else {
            return new FileResource(getInputDir(), resource.getPath());
        }
    }

}
