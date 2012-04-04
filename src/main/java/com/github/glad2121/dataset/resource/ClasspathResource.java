package com.github.glad2121.dataset.resource;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.github.glad2121.dataset.util.JavaUtils;

/**
 * 
 * 
 * @author GLAD!!
 */
public class ClasspathResource extends AbstractResource {

    private final ClassLoader classLoader;

    public ClasspathResource(String path) {
        this(path, JavaUtils.getContextClassLoader());
    }

    public ClasspathResource(String path, ClassLoader classLoader) {
        super(path);
        this.classLoader = classLoader;
    }

    public ClasspathResource(Class<?> clazz, String name) {
        this(JavaUtils.getResourcePath(clazz, name), clazz.getClassLoader());
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public boolean exists() {
        return getResource() != null;
    }

    public InputStream openInputStream() {
        InputStream in = getResourceAsStream();
        checkNotNull(in);
        return in;
    }

    URL getResource() {
        return getClassLoader().getResource(getPath());
    }

    InputStream getResourceAsStream() {
        return getClassLoader().getResourceAsStream(getPath());
    }

    void checkNotNull(InputStream in) {
        if (in == null) {
            throw new ResourceNotFoundException(getPath());
        }
    }

    public OutputStream openOutputStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "classpath:" + getPath();
    }

}
