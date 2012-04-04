package com.github.glad2121.dataset.seasar;

import org.seasar.extension.unit.S2TestCase;

import com.github.glad2121.dataset.DataSet;
import com.github.glad2121.dataset.DataSetAsserter;

public class S2DataSetAsserter implements DataSetAsserter {

    final S2TestCase testCase = new S2TestCase() {};

    public void assertEquals(DataSet expected, DataSet actual) {
        testCase.assertEquals(
                S2DataSet.cast(expected).getDataSet(),
                S2DataSet.cast(actual).getDataSet());
    }

    public static final S2DataSetAsserter INSTANCE = new S2DataSetAsserter();

}
