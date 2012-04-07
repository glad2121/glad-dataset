package org.glad2121.dataset.dbunit;

import org.glad2121.dataset.DataSet;

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
