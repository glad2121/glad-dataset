package org.glad2121.dataset;

import java.io.InputStream;
import java.io.OutputStream;

import org.glad2121.dataset.resource.Resource;
import org.glad2121.dataset.util.IOUtils;

/**
 * {@link FileAccessor} の実装のベースとなる抽象クラスです。
 * 
 * @author GLAD!!
 */
public abstract class AbstractFileAccessor implements FileAccessor {

    public DataSet read(Resource resource) {
        InputStream in = resource.openInputStream();
        try {
            return read(in);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public void write(Resource resource, DataSet dataSet) {
        OutputStream out = resource.openOutputStream();
        try {
            write(out, dataSet);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

}
