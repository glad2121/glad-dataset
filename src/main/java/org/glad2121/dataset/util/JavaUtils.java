package org.glad2121.dataset.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.glad2121.dataset.DataSetException;
import org.glad2121.dataset.resource.ClasspathResource;

/**
 * 
 * 
 * @author GLAD!!
 */
public class JavaUtils {

    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static String getResourcePath(Class<?> clazz, String name) {
        return clazz.getPackage().getName().replace('.', '/') + '/' + name;
    }

    public static Class<?> loadClass(String name) {
        try {
            return Class.forName(name, true, getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new DataSetException(e);
        }
    }

    public static <T> Constructor<T> getConstructor(
            Class<T> clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new DataSetException(e);
        }
    }

    public static <T> T newInstance(Class<? extends T> clazz) {
        try {
            return clazz.cast(clazz.newInstance());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public static <T> T newInstance(Constructor<T> c, Object... args) {
        try {
            return c.newInstance(args);
        } catch (InvocationTargetException e) {
            throw DataSetException.wrap(e.getCause());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public static <T> T newInstance(
            Class<? extends T> type, String className) {
        try {
            return type.cast(loadClass(className).newInstance());
        } catch (Exception e) {
            throw DataSetException.wrap(e);
        }
    }

    public static Properties loadProperties(String path) {
        InputStream in = new ClasspathResource(path).openInputStream();
        try {
            return loadProperties(in);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static Properties loadProperties(InputStream in) {
        try {
            Properties props = new Properties();
            props.load(in);
            return props;
        } catch (IOException e) {
            throw new DataSetException(e);
        }
    }

}
