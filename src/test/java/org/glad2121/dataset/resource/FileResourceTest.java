package org.glad2121.dataset.resource;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;

import org.glad2121.dataset.resource.FileResource;
import org.glad2121.dataset.resource.ResourceNotFoundException;
import org.glad2121.dataset.util.IOUtils;
import org.junit.Test;

/**
 * 
 * 
 * @author GLAD!!
 */
public class FileResourceTest {

    @Test
    public void testFileResource_withPath() {
        FileResource resource = new FileResource("pom.xml");
        assertThat(resource.getPath(), is("pom.xml"));
        assertThat(resource.exists(), is(true));
        InputStream in = resource.openInputStream();
        try {
            assertThat(in, notNullValue());
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @Test
    public void testFileResource_withBaseDirAndPath() {
        FileResource resource = new FileResource(".", "none");
        assertThat(resource.getPath(), endsWith("none"));
        assertThat(resource.exists(), is(false));
        try {
            resource.openInputStream();
            fail();
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage(), is(resource.getPath()));
        }
    }

}
