package org.glad2121.dataset;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.glad2121.dataset.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link DbAccessor} の実装のベースとなる抽象クラスです。
 * 
 * @author GLAD!!
 */
public abstract class AbstractDbAccessor implements DbAccessor {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Connection connection;

    public AbstractDbAccessor(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public int execute(String... sql) {
        logger.info("execute:\n{}", sql);
        try {
            int count = 0;
            Statement stmt = getConnection().createStatement();
            try {
                for (String s : sql) {
                    count += stmt.executeUpdate(s);
                }
            } finally {
                JdbcUtils.closeQuietly(stmt);
            }
            return count;
        } catch (SQLException e) {
            throw new DataSetException(e);
        }
    }

}
