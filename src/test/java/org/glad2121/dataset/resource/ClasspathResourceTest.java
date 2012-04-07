package org.glad2121.dataset.resource;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;

import org.glad2121.dataset.resource.ClasspathResource;
import org.glad2121.dataset.resource.ResourceNotFoundException;
import org.glad2121.dataset.util.IOUtils;
import org.junit.Test;

/**
 * 
 * 
 * @author GLAD!!
 */
public class ClasspathResourceTest {

    @Test
    public void testClasspathResource_withPath() {
        ClasspathResource resource = new ClasspathResource("jdbc.properties");
        assertThat(resource.getPath(), is("jdbc.properties"));
        assertThat(resource.exists(), is(true));
        InputStream in = resource.openInputStream();
        try {
            assertThat(in, notNullValue());
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @Test
    public void testClasspathResource_withClassAndName() {
        ClasspathResource resource = new ClasspathResource(
                ClasspathResourceTest.class, "none");
        assertThat(resource.getPath(),
                is("org/glad2121/dataset/resource/none"));
        assertThat(resource.exists(), is(false));
        try {
            resource.openInputStream();
            fail();
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage(), is(resource.getPath()));
        }
    }

}
