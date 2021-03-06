package org.glad2121.dataset;

import java.io.InputStream;
import java.io.OutputStream;

import org.glad2121.dataset.resource.Resource;

/**
 * ファイルに対するアクセッサです。
 * 
 * @author GLAD!!
 */
public interface FileAccessor {

    DataSet read(Resource resource);

    DataSet read(InputStream in);

    void write(Resource resource, DataSet dataSet);

    void write(OutputStream out, DataSet dataSet);

}
