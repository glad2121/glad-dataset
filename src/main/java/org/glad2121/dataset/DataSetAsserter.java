package org.glad2121.dataset;

/**
 * ${@link DataSet} に対する表明を検証します。
 * 
 * @author GLAD!!
 */
public interface DataSetAsserter {

    void assertEquals(DataSet expected, DataSet actual);

}
