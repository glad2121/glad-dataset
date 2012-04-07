package org.glad2121.dataset.resource;

/**
 * 
 * 
 * @author GLAD!!
 */
public abstract class AbstractResource implements Resource {

    private final String path;

    public AbstractResource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
