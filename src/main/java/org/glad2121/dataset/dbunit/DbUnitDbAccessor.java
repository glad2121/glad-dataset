package org.glad2121.dataset.dbunit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.operation.DatabaseOperation;
import org.glad2121.dataset.AbstractDbAccessor;
import org.glad2121.dataset.DataSet;
import org.glad2121.dataset.DataSetException;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DbUnitDbAccessor extends AbstractDbAccessor {

    static final String DBUNIT_PREFIX = "dbunit.";

    private final IDatabaseConnection databaseConnection;

    public DbUnitDbAccessor(IDatabaseConnection databaseConnection) {
        super(getConnection(databaseConnection));
        this.databaseConnection = databaseConnection;
    }

    public DbUnitDbAccessor(Connection connection, Properties props) {
        super(connection);
        IDatabaseConnection databaseConnection =
                getDatabaseConnection(connection);
        configure(databaseConnection.getConfig(), props);
        this.databaseConnection = databaseConnection;
    }

    static Connection getConnection(IDatabaseConnection connection) {
        try {
            return connection.getConnection();
        } catch (SQLException e) {
            throw new DataSetException(e);
        }
    }

    static IDatabaseConnection getDatabaseConnection(Connection con) {
        try {
            return new DatabaseConnection(con);
        } catch (DatabaseUnitException e) {
            throw new DataSetException(e);
        }
    }

    void configure(DatabaseConfig config, Properties props) {
        Properties p = new Properties();
        for (Map.Entry<?, ?> entry : props.entrySet()) {
            String key = (String) entry.getKey();
            if (key.startsWith(DBUNIT_PREFIX)) {
                String dbunitKey = key.substring(DBUNIT_PREFIX.length());
                String value = (String) entry.getValue();
                p.setProperty(dbunitKey, value);
                logger.debug("{} = {}", key, value);
            }
        }
        try {
            config.setPropertiesByString(p);
        } catch (DatabaseUnitException e) {
            throw new DataSetException(e);
        }
    }

    public IDatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public DataSet read(String... tableNames) {
        logger.debug("read: {}", tableNames);
        try {
            return new DbUnitDataSet(
                    getDatabaseConnection().createDataSet(tableNames));
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public void cleanInsert(DataSet dataSet) {
        logger.debug("cleanInsert: {}", dataSet);
        try {
            DatabaseOperation.CLEAN_INSERT.execute(
                    getDatabaseConnection(),
                    DbUnitDataSet.cast(dataSet).getDataSet());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public void insert(DataSet dataSet) {
        logger.debug("insert: {}", dataSet);
        try {
            DatabaseOperation.INSERT.execute(
                    getDatabaseConnection(),
                    DbUnitDataSet.cast(dataSet).getDataSet());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public void update(DataSet dataSet) {
        logger.debug("update: {}", dataSet);
        try {
            DatabaseOperation.UPDATE.execute(
                    getDatabaseConnection(),
                    DbUnitDataSet.cast(dataSet).getDataSet());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public void merge(DataSet dataSet) {
        logger.debug("merge: {}", dataSet);
        try {
            DatabaseOperation.REFRESH.execute(
                    getDatabaseConnection(),
                    DbUnitDataSet.cast(dataSet).getDataSet());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public void delete(DataSet dataSet) {
        logger.debug("delete: {}", dataSet);
        try {
            DatabaseOperation.DELETE.execute(
                    getDatabaseConnection(),
                    DbUnitDataSet.cast(dataSet).getDataSet());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public void deleteAll(DataSet dataSet) {
        logger.debug("deleteAll: {}", dataSet);
        try {
            DatabaseOperation.DELETE_ALL.execute(
                    getDatabaseConnection(),
                    DbUnitDataSet.cast(dataSet).getDataSet());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public void truncate(DataSet dataSet) {
        logger.debug("truncate: {}", dataSet);
        try {
            DatabaseOperation.TRUNCATE_TABLE.execute(
                    getDatabaseConnection(),
                    DbUnitDataSet.cast(dataSet).getDataSet());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

}
