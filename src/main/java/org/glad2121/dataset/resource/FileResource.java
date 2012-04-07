package org.glad2121.dataset.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.glad2121.dataset.DataSetException;

/**
 * 
 * 
 * @author GLAD!!
 */
public class FileResource extends AbstractResource {

    private final File baseDir;

    private File file;

    public FileResource(String path) {
        this((File) null, path);
    }

    public FileResource(File baseDir, String path) {
        super(path);
        this.baseDir = baseDir;
    }

    public FileResource(String baseDir, String path) {
        this(new File(baseDir), path);
    }

    public File getBaseDir() {
        return baseDir;
    }

    public File getFile() {
        if (file == null) {
            file = getFile(baseDir);
        }
        return file;
    }

    File getFile(File baseDir) {
        if (baseDir == null) {
            return new File(getPath());
        }
        return new File(baseDir, getPath());
    }

    public boolean exists() {
        return getFile().exists();
    }

    public InputStream openInputStream() {
        try {
            return new FileInputStream(getFile());
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(getPath(), e);
        }
    }

    public OutputStream openOutputStream() {
        File file = getFile();
        mkdirs(file.getParentFile());
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new DataSetException(e);
        }
    }

    void mkdirs(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public String toString() {
        return "file:" + getFile().getPath();
    }

}
