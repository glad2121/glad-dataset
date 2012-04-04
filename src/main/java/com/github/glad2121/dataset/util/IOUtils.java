package com.github.glad2121.dataset.util;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author GLAD!!
 */
public class IOUtils {

    static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static void closeQuietly(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

}
