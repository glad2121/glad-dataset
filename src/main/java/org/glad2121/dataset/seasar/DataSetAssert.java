package org.glad2121.dataset.seasar;

import org.glad2121.dataset.DataSet;

public class DataSetAssert {

    public static void assertEquals(DataSet expected, DataSet actual) {
        S2DataSetAsserter.INSTANCE.assertEquals(expected, actual);
    }

}
