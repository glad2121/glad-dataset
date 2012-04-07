package org.glad2121.dataset.dbunit;

import org.glad2121.dataset.dbunit.DbUnitDataSetAsserter;

public class DbUnitDataSetAsserterExt extends DbUnitDataSetAsserter {

    @Override
    protected boolean skipCompare(
            String columnName, Object expectedValue, Object actualValue) {
        if ("UPDATED".equalsIgnoreCase(columnName)) {
            return true;
        }
        return super.skipCompare(columnName, expectedValue, actualValue);
    }

    // 最後に定義すること。
    public static final DbUnitDataSetAsserterExt INSTANCE =
            new DbUnitDataSetAsserterExt();

}
