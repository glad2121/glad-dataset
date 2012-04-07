package org.glad2121.dataset.seasar;

import org.glad2121.dataset.DataSet;
import org.glad2121.dataset.DataSetAsserter;
import org.seasar.extension.unit.S2TestCase;

public class S2DataSetAsserter implements DataSetAsserter {

    final S2TestCase testCase = new S2TestCase() {};

    public void assertEquals(DataSet expected, DataSet actual) {
        testCase.assertEquals(
                S2DataSet.cast(expected).getDataSet(),
                S2DataSet.cast(actual).getDataSet());
    }

    public static final S2DataSetAsserter INSTANCE = new S2DataSetAsserter();

}
