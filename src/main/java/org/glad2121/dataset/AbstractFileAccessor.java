package org.glad2121.dataset;

import java.io.InputStream;
import java.io.OutputStream;

import org.glad2121.dataset.resource.Resource;
import org.glad2121.dataset.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link FileAccessor} の実装のベースとなる抽象クラスです。
 * 
 * @author GLAD!!
 */
public abstract class AbstractFileAccessor implements FileAccessor {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public DataSet read(Resource resource) {
        logger.debug("read: {}", resource);
        InputStream in = resource.openInputStream();
        try {
            return read(in);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public void write(Resource resource, DataSet dataSet) {
        logger.debug("write: {}; {}", resource, dataSet);
        OutputStream out = resource.openOutputStream();
        try {
            write(out, dataSet);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

}
