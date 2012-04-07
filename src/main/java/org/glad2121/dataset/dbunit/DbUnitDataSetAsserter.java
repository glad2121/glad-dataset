package org.glad2121.dataset.dbunit;

import org.dbunit.DatabaseUnitException;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.assertion.FailureHandler;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.glad2121.dataset.DataSet;
import org.glad2121.dataset.DataSetAsserter;
import org.glad2121.dataset.DataSetException;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DbUnitDataSetAsserter
        extends DbUnitAssert
        implements DataSetAsserter {

    public void assertEquals(DataSet expected, DataSet actual) {
        try {
            assertEquals(
                    DbUnitDataSet.cast(expected).getDataSet(),
                    DbUnitDataSet.cast(actual).getDataSet());
        } catch (DatabaseUnitException e) {
            throw new DataSetException(e);
        }
    }

    @Override
    public void assertEquals(
            IDataSet expectedDataSet, IDataSet actualDataSet)
            throws DatabaseUnitException {
        super.assertEquals(expectedDataSet, actualDataSet);
    }

    @Override
    public void assertEquals(
            IDataSet expectedDataSet, IDataSet actualDataSet,
            FailureHandler failureHandler) throws DatabaseUnitException {
        super.assertEquals(expectedDataSet, actualDataSet, failureHandler);
    }

    @Override
    public void assertEquals(
            ITable expectedTable, ITable actualTable,
            FailureHandler failureHandler) throws DatabaseUnitException {
        actualTable = DefaultColumnFilter.includedColumnsTable(
                actualTable, expectedTable.getTableMetaData().getColumns());
        super.assertEquals(expectedTable, actualTable, failureHandler);
    }

    @Override
    protected boolean skipCompare(
            String columnName, Object expectedValue, Object actualValue) {
        if ("*".equals(expectedValue)) {
            return true;
        }
        return super.skipCompare(columnName, expectedValue, actualValue);
    }

    // 最後に定義すること。
    public static final DbUnitDataSetAsserter INSTANCE =
            new DbUnitDataSetAsserter();

}
