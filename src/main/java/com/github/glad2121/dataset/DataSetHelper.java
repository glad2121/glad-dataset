package com.github.glad2121.dataset;

import java.io.File;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glad2121.dataset.resource.FileResource;
import com.github.glad2121.dataset.resource.Resource;
import com.github.glad2121.dataset.resource.ResourceNotFoundException;

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

    /**
     * 指定されたテーブルを読み込んで、{@link DataSet} を生成します。
     * 
     * @param tableNames テーブル名
     * @return {@link DataSet}
     */
    public DataSet readDb(String... tableNames) {
        return getDbAccessor().read(tableNames);
    }

    public void cleanInsert(Resource resource, String... tableNames) {
        DataSet dataSet = readFile(resource, tableNames);
        getDbAccessor().cleanInsert(dataSet);
    }

    public void insert(DataSet dataSet) {
        getDbAccessor().insert(dataSet);
    }

    public void update(DataSet dataSet) {
        getDbAccessor().update(dataSet);
    }

    /**
     * 期待値データと DB の状態を比較・検証します。
     * <P>
     * 期待値データが存在しない場合は、DB の状態に基づいてデータ雛型を生成します。
     * 
     * @param expected 期待値データのリソース
     * @param tableNames 期待値データが保持するテーブル名
     */
    public void assertDb(Resource expected, String... tableNames) {
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
            return readFile(resource);
        } catch (ResourceNotFoundException e) {
            if (tableNames.length > 0) {
                try {
                    writeFile(getInputResource(resource), readDb(tableNames));
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

    Resource getInputResource(Resource resource) {
        if (resource instanceof FileResource) {
            return resource;
        } else {
            return new FileResource(getInputDir(), resource.getPath());
        }
    }

}
