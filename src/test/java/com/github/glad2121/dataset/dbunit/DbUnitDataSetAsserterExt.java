package com.github.glad2121.dataset.dbunit;

public class DbUnitDataSetAsserterExt extends DbUnitDataSetAsserter {

    @Override
    protected boolean skipCompare(
            String columnName, Object expectedValue, Object actualValue) {
        if ("UPDATED".equalsIgnoreCase(columnName)) {
            return true;
        }
        return super.skipCompare(columnName, expectedValue, actualValue);
    }

    public static final DbUnitDataSetAsserterExt INSTANCE =
            new DbUnitDataSetAsserterExt();

}
