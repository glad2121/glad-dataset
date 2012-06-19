package org.glad2121.dataset;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;

import org.glad2121.dataset.resource.FileResource;
import org.glad2121.dataset.resource.Resource;
import org.glad2121.dataset.resource.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link DataSet} に対する処理を支援するクラスです。
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

    final ExecutionMode executionMode;

    public DataSetHelper(Connection connection) {
        this(connection, DataSetConfigFactory.getConfig());
    }

    public DataSetHelper(Connection connection, DataSetConfig config) {
        this(config.getDbAccessor(connection),
                config.getFileAccessor(),
                config.getAsserter(),
                config.getInputDir(),
                config.getOutputDir(),
                config.getExecutionMode());
    }

    public DataSetHelper(
            DbAccessor dbAccessor, FileAccessor fileAccessor,
            DataSetAsserter asserter, File inputDir, File outputDir,
            ExecutionMode executionMode) {
        this.dbAccessor = dbAccessor;
        this.fileAccessor = fileAccessor;
        this.asserter = asserter;
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        this.executionMode = executionMode;
        if (logger.isInfoEnabled()) {
            logger.info("executionMode = {}", executionMode);
        }
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

    public ExecutionMode getExecutionMode() {
        return executionMode;
    }

    /**
     * 指定されたテーブルを読み込んで、{@link DataSet} を生成します。
     * 
     * @param tableNames テーブル名
     * @return {@link DataSet}
     */
    public DataSet readDb(String... tableNames) {
        return getDbAccessor().read(tableNames);
    }

    /**
     * 指定されたデータで、DB を初期化します。
     * <P>
     * 初期データが存在しない場合は、DB の状態に基づいてデータの雛型を生成します。
     * 
     * @param resource 初期データのリソース
     * @param tableNames 初期データが保持するテーブル名
     */
    public void cleanInsert(Resource resource, String... tableNames) {
        if (executionMode == ExecutionMode.INIT && tableNames.length > 0) {
            writeInputFile(resource, tableNames);
            return;
        }
        DataSet dataSet = readFile(resource, tableNames);
        getDbAccessor().cleanInsert(dataSet);
    }

    public void insert(DataSet dataSet) {
        getDbAccessor().insert(dataSet);
    }

    public void update(DataSet dataSet) {
        getDbAccessor().update(dataSet);
    }

    public void merge(DataSet dataSet) {
        getDbAccessor().merge(dataSet);
    }

    public void delete(DataSet dataSet) {
        getDbAccessor().delete(dataSet);
    }

    public void deleteAll(DataSet dataSet) {
        getDbAccessor().deleteAll(dataSet);
    }

    public void truncate(DataSet dataSet) {
        getDbAccessor().truncate(dataSet);
    }

    public void executeSql(String sql) {
        getDbAccessor().execute(sql);
    }

    /**
     * 期待値データと DB の状態を比較・検証します。
     * <P>
     * 期待値データが存在しない場合は、DB の状態に基づいてデータの雛型を生成します。
     * 
     * @param expected 期待値データのリソース
     * @param tableNames 期待値データが保持するテーブル名
     */
    public void assertDb(Resource expected, String... tableNames) {
        if (executionMode == ExecutionMode.INIT && tableNames.length > 0) {
            writeInputFile(expected, tableNames);
            return;
        }
        DataSet dataSet = readFile(expected, tableNames);
        DataSet actual = readDb(dataSet.getTableNames());
        assertEquals(dataSet, actual);
    }

    public void assertEquals(DataSet expected, DataSet actual) {
        getAsserter().assertEquals(expected, actual);
    }

    /**
     * 指定されたデータファイルを読み込んで、{@link DataSet} を生成します。
     * 
     * @param resource データファイルのリソース
     * @return {@link DataSet}
     */
    public DataSet readFile(Resource resource) {
        return getFileAccessor().read(resource);
    }

    /**
     * 指定されたデータファイルを読み込んで、{@link DataSet} を生成します。
     * <P>
     * データファイルが存在しない場合は、DB の状態に基づいてデータ雛型を生成します。
     * 
     * @param resource データファイルのリソース
     * @param tableNames データファイルが保持するテーブル名
     * @return {@link DataSet}
     */
    public DataSet readFile(Resource resource, String... tableNames) {
        try {
            DataSet dataSet = readFile(resource);
            if (tableNames.length > 0) {
                assertArrayEquals(tableNames, dataSet.getTableNames());
            }
            return dataSet;
        } catch (ResourceNotFoundException e) {
            if (executionMode != ExecutionMode.TEST && tableNames.length > 0) {
                try {
                    writeInputFile(resource, tableNames);
                } catch (Exception e2) {
                    logger.warn(e2.toString());
                }
            }
            throw e;
        }
    }

    /**
     * 指定されたデータファイルに {@link DataSet} を書き込みます
     * 
     * @param resource データファイルのリソース
     * @param dataSet {@link DataSet}
     */
    public void writeFile(Resource resource, DataSet dataSet) {
        getFileAccessor().write(resource, dataSet);
    }

    void writeInputFile(Resource resource, String... tableNames) {
        writeFile(getInputResource(resource), readDb(tableNames));
    }

    Resource getInputResource(Resource resource) {
        if (resource instanceof FileResource) {
            return resource;
        } else {
            return new FileResource(getInputDir(), resource.getPath());
        }
    }

}
