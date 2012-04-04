package com.github.glad2121.dataset.dbunit;

import com.github.glad2121.dataset.DataSet;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DataSetAssert {

    public static void assertEquals(DataSet expected, DataSet actual) {
        DbUnitDataSetAsserter.INSTANCE.assertEquals(expected, actual);
    }

}
