package org.glad2121.dataset;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.glad2121.dataset.util.JdbcUtils;

/**
 * {@link DbAccessor} の実装のベースとなる抽象クラスです。
 * 
 * @author GLAD!!
 */
public abstract class AbstractDbAccessor implements DbAccessor {

    private final Connection connection;

    public AbstractDbAccessor(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public int execute(String... sql) {
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
