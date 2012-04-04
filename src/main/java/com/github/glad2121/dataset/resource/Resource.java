package com.github.glad2121.dataset.resource;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 
 * @author GLAD!!
 */
public interface Resource {

    String getPath();

    boolean exists();

    InputStream openInputStream();

    OutputStream openOutputStream();

}
